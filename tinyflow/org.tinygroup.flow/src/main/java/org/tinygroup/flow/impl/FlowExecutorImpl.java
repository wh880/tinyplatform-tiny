/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.flow.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.tinygroup.commons.match.SimpleTypeMatcher;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.commons.tools.ValueUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.util.Context2ObjectUtil;
import org.tinygroup.event.Parameter;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.flow.FlowExecutor;
import org.tinygroup.flow.config.Component;
import org.tinygroup.flow.config.ComponentDefine;
import org.tinygroup.flow.config.ComponentDefines;
import org.tinygroup.flow.config.Flow;
import org.tinygroup.flow.config.FlowProperty;
import org.tinygroup.flow.config.Node;
import org.tinygroup.flow.containers.ComponentContainers;
import org.tinygroup.flow.exception.FlowRuntimeException;
import org.tinygroup.flow.util.FlowElUtil;
import org.tinygroup.format.Formater;
import org.tinygroup.format.impl.ContextFormater;
import org.tinygroup.format.impl.FormaterImpl;
import org.tinygroup.i18n.I18nMessageFactory;
import org.tinygroup.i18n.I18nMessages;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * 默认流程执行器
 * 
 * @author luoguo
 */
public class FlowExecutorImpl implements FlowExecutor {
	private static Logger logger = LoggerFactory
			.getLogger(FlowExecutorImpl.class);
	private static Map<String, Class<?>> exceptionMap = new HashMap<String, Class<?>>();
	private static transient Formater formater = new FormaterImpl();
	private Map<String, Flow> flowIdMap = new HashMap<String, Flow>();// 包含了name和id两个，所以通过名字和id都可以访问
	private I18nMessages i18nMessages = I18nMessageFactory.getI18nMessages();
	private boolean change;
	// 组件容器
	private ComponentContainers containers = new ComponentContainers();

	static {
		formater.addFormatProvider("", new ContextFormater());
		formater.addFormatProvider(Parameter.INPUT, new ContextFormater());
		formater.addFormatProvider(Parameter.OUTPUT, new ContextFormater());
		formater.addFormatProvider(Parameter.BOTH, new ContextFormater());
	}

	public Map<String, Flow> getFlowIdMap() {
		return flowIdMap;
	}

	public void execute(String flowId, Context context) {
		execute(flowId, null, context);
	}

	public void execute(String flowId, String nodeId, Context context) {
		logger.logMessage(LogLevel.INFO, "开始执行流程[flowId:{0},nodeId:{1}]执行",
				flowId, nodeId);
		if (!getFlowIdMap().containsKey(flowId)) {
			logger.log(LogLevel.ERROR, "flow.flowNotExist", flowId);
			throw new FlowRuntimeException("flow.flowNotExist", flowId);
		}
		Flow flow = getFlowIdMap().get(flowId);
		Node node = getNode(flow, nodeId);
		if (node != null) {
			logContext(context);
			checkInputParameter(flow, context);
			execute(flow, node, context);
			checkOutputParameter(flow, context);
			logContext(context);
		}
		logger.logMessage(LogLevel.INFO, "流程[flowId:{0},nodeId:{1}]执行完毕",
				flowId, nodeId);
	}

	/**
	 * 如果nodeId为空，则为其赋值Begin 如果begin不存在,分两种情况 1、节点数大于0，则执行第一个节点 2、节点数等于0，无需执行
	 * 如果nodeId为end，则无需执行 无需执行的两种情况下，返回的结果集为null 除以上情况外,若节点不存在，则抛出节点不存在的异常
	 * 
	 * @param flow
	 * @param nodeId
	 * @return
	 */
	private Node getNode(Flow flow, String nodeId) {
		if (DEFAULT_END_NODE.equals(nodeId)) { // 如果执行的是end，则无需执行
			logger.logMessage(LogLevel.INFO,
					"流程[flowId:{0},nodeId:{1}]为结束节点,无需执行", flow.getId(),
					DEFAULT_END_NODE);
			return null;
		}
		if (nodeId == null) { // 如果节点为空，则赋值为begin
			nodeId = DEFAULT_BEGIN_NODE;
		}
		if (DEFAULT_BEGIN_NODE.equals(nodeId) && flow.getNodes().size() == 0) {
			logger.logMessage(LogLevel.INFO,
					"流程无节点,流程[flowId:{0},nodeId:{1}]执行完毕。", flow.getId(),
					DEFAULT_BEGIN_NODE);
			return null;
		}
		Node node = flow.getNodeMap().get(nodeId);
		// 如果begin节点不存在，且节点数大于0，则从第一个节点开始执行
		if (node == null && DEFAULT_BEGIN_NODE.equals(nodeId)) {
			node = flow.getNodes().get(0);
		} else if (node == null) {// 节点不存在，且节点名不为begin
			logger.log(LogLevel.ERROR, "flow.flowNodeNotExist", flow.getId(),
					nodeId);
			throw new FlowRuntimeException(i18nMessages.getMessage(
					"flow.flowNodeNotExist", flow.getId(), nodeId));
		}
		return node;
	}

	private void logContext(Context context) {
		if (logger.isEnabled(LogLevel.DEBUG)) {
			logger.logMessage(LogLevel.DEBUG, "环境内容开始：");
			logItemMap(context.getItemMap());
			logSubContext(context.getSubContextMap());
			logger.logMessage(LogLevel.DEBUG, "环境内容结束");
		}
	}

	private void logSubContext(Map<String, Context> subContextMap) {
		logger.logMessage(LogLevel.DEBUG, "子环境[{0}]的内容开始：");
		if (subContextMap != null) {
			for (String key : subContextMap.keySet()) {
				logContext(subContextMap.get(key));
			}
		}
		logger.logMessage(LogLevel.DEBUG, "子环境[{0}]的内容结束：");
	}

	private void logItemMap(Map<String, Object> itemMap) {
		for (String key : itemMap.keySet()) {
			logger.logMessage(LogLevel.DEBUG, "key: {0}, value: {1}", key,
					itemMap.get(key));
		}
	}

	private static Class<?> getExceptionType(String name) {
		Class<?> exceptionType = exceptionMap.get(name);
		if (exceptionType == null) {
			try {
				exceptionType = (Class<?>) Class.forName(name);// TODO:通过Loader进行获取
				exceptionMap.put(name, exceptionType);
			} catch (ClassNotFoundException e) {
				throw new FlowRuntimeException(e);
			}
		}
		return exceptionType;
	}

	/**
	 * 获取一个新环境
	 * 
	 * @param flow
	 * @param context
	 * @return
	 */
	private Context getNewContext(Flow flow, Context context) {
		Context flowContext = null;
		if (context == null) {
			return null;
		}
		flowContext = context.getSubContextMap().get(flow.getId());
		if (flowContext == null) {
			return getNewContext(flow, context.getParent());
		}
		return flowContext;
	}

	/**
	 * 执行具体节点，并继续往下执行
	 * 
	 * @param flow
	 *            当前执行的流程
	 * @param node
	 *            非end节点的任意合法节点
	 * @param context
	 */
	private void execute(Flow flow, Node node, Context context) {

		String nodeId = node.getId(); // 当前节点id
		Context flowContext = context;
		try {
			logger.logMessage(LogLevel.INFO, "开始执行节点:{}", nodeId);
			if (flow.isPrivateContext()) { // 是否context私有
				flowContext = getNewContext(flow, context);
				if (flowContext == null) {
					flowContext = new ContextImpl();
					context.putSubContext(flow.getId(), flowContext);
				}
			}
			Component component = node.getComponent();
			if (component != null) {
				ComponentInterface componentInstance = getComponentInstance(component
						.getName());
				setProperties(node, componentInstance, flowContext);
				if (!nodeId.equals(DEFAULT_END_NODE)) { // 如果当前节点不是最终节点
					componentInstance.execute(flowContext);
				}
				logger.logMessage(LogLevel.INFO, "节点:{}执行完毕", nodeId);
			} else {
				logger.logMessage(LogLevel.INFO, "节点:{}未配置组件，无需执行", nodeId);
			}

		} catch (RuntimeException exception) {
			/**
			 * 遍历所有异常节点
			 */
			logger.errorMessage("流程执行[flow:{},node:{}]发生异常", exception,
					flow.getId(), node.getId());
			if (exceptionNodeProcess(flow, node, context, flowContext,
					exception)) {
				return;
			}
			// 如果节点中没有处理掉，则由流程的异常处理节点进行处理
			Node exceptionNode = flow.getNodeMap().get(EXCEPTION_DEAL_NODE);
			if (exceptionNode != null
					&& exceptionNodeProcess(flow, exceptionNode, context,
							flowContext, exception)) {
				return;
				// executeNextNode(flow, newContext, exceptionNode.getId());
			}
			// 如果还是没有被处理掉，则交由异常处理流程进行管理
			Flow exceptionProcessFlow = this.getFlow(EXCEPTION_DEAL_FLOW);
			if (exceptionProcessFlow != null) {
				exceptionNode = exceptionProcessFlow.getNodeMap().get(
						EXCEPTION_DEAL_NODE);
				if (exceptionNode != null
						&& exceptionNodeProcess(exceptionProcessFlow,
								exceptionNode, context, flowContext, exception)) {
					return;
				}
			}
			throw exception;
		}
		if (nodeId != null && !nodeId.equals(DEFAULT_END_NODE)) {
			// 先直接取，如果取到就执行，如果取不到，则用正则去匹配，效率方面的考虑
			String nextNodeId = node.getNextNodeId(context);
			if (nextNodeId == null) {
				int index = flow.getNodes().indexOf(node);
				if (index != flow.getNodes().size() - 1) {
					nextNodeId = flow.getNodes().get(index + 1).getId();
				} else {
					nextNodeId = DEFAULT_END_NODE;
				}
			}
			logger.logMessage(LogLevel.INFO, "下一节点:{}", nextNodeId);
			executeNextNode(flow, flowContext, nextNodeId);
		}
	}

	private void checkInputParameter(Flow flow, Context context) {
		StringBuffer buf = new StringBuffer();
		if (flow.getInputParameters() != null) {
			for (Parameter parameter : flow.getInputParameters()) {
				if (parameter.isRequired()) {// 如果是必须
					// =============20130619修改begin================
					// Object value = context.get(parameter.getName());
					// if (value == null) {//
					// 如果从上下文直接拿没有拿到，则通过ClassNameObjectGenerator去解析
					// value = getObjectByGenerator(parameter, context);
					// if (value != null) {// 如果解析出来不为空，则放入上下文
					// context.put(parameter.getName(), value);
					// continue;
					// }
					// }
					Object value = Context2ObjectUtil.getObject(parameter,
							context, this.getClass().getClassLoader());
					if (value != null) {// 如果解析出来不为空，则放入上下文
						context.put(parameter.getName(), value);
						continue;
					}
					// =============20130619修改end================
					if (value == null) {
						buf.append("参数<");
						buf.append(parameter.getName());
						buf.append(">在环境中不存在；");
					}
				}
			}
			if (buf.length() > 0) {
				// buf.insert(0, "流程<" + flow.getId() + ">需要的参数不足：");
				// throw new FlowRuntimeException(buf.toString());
				throw new FlowRuntimeException("flow.inParamNotExist",
						flow.getId(), buf.toString());
			}

		}
	}

	// private Object getObjectByGenerator(Parameter parameter, Context context)
	// {
	// String collectionType = parameter.getCollectionType();
	// if (generator == null) {
	// generator = SpringUtil.getBean(
	// GeneratorFileProcessor.CLASSNAME_OBJECT_GENERATOR_BEAN);
	// }
	// if (collectionType != null && !"".equals(collectionType)) {
	// return generator.getObjectCollection(parameter.getName(),
	// collectionType, parameter.getType(), context);
	// } else if (parameter.isArray()) {
	// return generator.getObjectArray(parameter.getName(),
	// parameter.getType(), context);
	// }
	//
	// return generator.getObject(parameter.getName(),parameter.getName(),
	// parameter.getType(),
	// context);
	// }

	private void checkOutputParameter(Flow flow, Context context) {
		StringBuffer buf = new StringBuffer();
		if (flow.getOutputParameters() != null) {
			for (Parameter parameter : flow.getOutputParameters()) {
				if (parameter.isRequired()) {// 如果是必须
					Object value = context.get(parameter.getName());
					if (value == null) {
						buf.append("参数<");
						buf.append(parameter.getName());
						buf.append(">在环境中不存在；");
					}
				}
			}
			if (buf.length() > 0) {
				// buf.insert(0, "流程<" + flow.getId() + ">需要输出的参数不足：");
				// throw new FlowRuntimeException(buf.toString());
				throw new FlowRuntimeException("flow.outParamNotExist",
						flow.getId(), buf.toString());
			}
		}
	}

	private boolean exceptionNodeProcess(Flow flow, Node node, Context context,
			Context newContext, Exception exception) {
		List<String> nextExceptionList = node.getNextExceptionList();
		// 20130524调整为顺序取异常进行匹配
		for (int i = 0; i < nextExceptionList.size(); i++) {
			String exceptionName = nextExceptionList.get(i);
			if (dealException(exception, context, newContext, node, flow,
					exceptionName)) {
				return true;
			}
			Throwable t = exception.getCause();
			while (t != null) {
				if (dealException(t, context, newContext, node, flow,
						exceptionName)) {
					return true;
				}
				t = t.getCause();
			}

		}
		return false;
	}

	private boolean dealException(Throwable exception, Context context,
			Context newContext, Node node, Flow flow, String exceptionName) {
		if (getExceptionType(exceptionName).isInstance(exception)) {// 如果异常匹配
			String nextNode = node.getNextExceptionNodeMap().get(exceptionName);
			context.put(EXCEPTION_DEAL_FLOW, flow);
			context.put(EXCEPTION_DEAL_NODE_KEY, node);
			context.put(EXCEPTION_KEY, exception);
			executeNextNode(flow, newContext, nextNode);
			logger.errorMessage("处理流程异常:flow:{},node:{}", exception,
					flow.getId(), nextNode);
			return true;
		}
		return false;
	}

	private void executeNextNode(Flow flow, Context context, String nextNodeId) {
		String nextExecuteNodeId = nextNodeId;
		int index = nextNodeId.indexOf(':');
		if (index > 0) { // newflowId:newnodeId
			String[] str = nextNodeId.split(":");
			if (str.length > 1) {
				nextExecuteNodeId = str[1];
			} else {
				nextExecuteNodeId = DEFAULT_BEGIN_NODE;
			}
			// 从另一个流程的节点开始执行
			execute(str[0], nextExecuteNodeId, context);
		} else if (!DEFAULT_END_NODE.equals(nextNodeId)) {
			Node nextNode = flow.getNodeMap().get(nextNodeId);
			execute(flow, nextNode, context);
		}
	}

	/**
	 * 把配置的参数注入进去
	 * 
	 * @param node
	 * @param componentInstance
	 */
	private void setProperties(Node node, ComponentInterface componentInstance,
			Context context) {
		Map<String, FlowProperty> properties = node.getComponent()
				.getPropertyMap();
		if (properties != null) {
			for (String name : properties.keySet()) {
				FlowProperty property = properties.get(name);
				String value = property.getValue();
				Object object = null;
				// 如果是el表达式，则通过el表达式处理
				if (FlowProperty.EL_TYPE.equals(property.getType())) {
					object = FlowElUtil.execute(value, context, this.getClass()
							.getClassLoader());
				} else {// 否则采用原有处理方式
					object = getObject(property.getType(), value, context);
				}

				try {
					PropertyUtils.setProperty(componentInstance, name, object);
				} catch (Exception e) {
					throw new FlowRuntimeException(e);
				}
			}
		}
	}

	private Object getObject(String type, String value, Context context) {
		String str = value;
		if (str instanceof String) {
			str = formater.format(context, str);
		}

		// 所有的都不是，说明是对象或表达式,此时返回null
		Object o = null;
		if (str != null) {
			str = str.trim();
			// type为空，按原先逻辑处理
			if (StringUtil.isEmpty(type)) {
				o = SimpleTypeMatcher.matchType(str);
			} else {
				// type不为空，则根据设置的type进行处理。可以避免数值型结果和参数类型不一致的问题。
				o = ValueUtil.getValue(str, type);
			}

		}
		return o;
	}

	// protected Object getObjectByName(String name, Context context) {
	// Object object = getObject(name, context);
	// if (object == null) {
	// int index = name.indexOf('.');
	// if (index == -1) {
	// object = context.get(name);
	// } else {
	// String k = name.substring(0, index);
	// String p = name.substring(index + 1);
	// object = context.get(k);
	// if (object != null) {
	// try {
	// object = PropertyUtils.getProperty(object, p);
	// } catch (Exception e) {
	// throw new FlowRuntimeException(e);
	// }
	// }
	// }
	// }
	// return object;
	// }

	public void assemble() {
		for (Flow flow : flowIdMap.values()) {
			flow.assemble();
		}
	}

	public void addFlow(Flow flow) {
		if (flow.getId() != null && flowIdMap.get(flow.getId()) != null) {
			logger.logMessage(LogLevel.ERROR, "flow:[id:{0}]已经存在！",
					flow.getId());
		}
		if (flow.getName() != null && flowIdMap.get(flow.getName()) != null) {
			logger.logMessage(LogLevel.ERROR, "flow:[name:{0}]已经存在！",
					flow.getName());
		}
		if (flow.getId() != null) {
			logger.logMessage(LogLevel.INFO, "添加flow:[id:{0}]", flow.getId());
			flowIdMap.put(flow.getId(), flow);
		}
		if (flow.getName() != null) {
			logger.logMessage(LogLevel.INFO, "添加flow:[Name:{0}]",
					flow.getName());
			flowIdMap.put(flow.getName(), flow);
		}
		flow.setFlowExecutor(this);
		setChange(true);
	}

	public void removeFlow(Flow flow) {
		logger.logMessage(LogLevel.INFO, "移除flow:[id:{0}]", flow.getId());
		flowIdMap.remove(flow.getId());
		logger.logMessage(LogLevel.INFO, "移除flow:[name:{0}]", flow.getName());
		flowIdMap.remove(flow.getName());
		setChange(true);
	}

	public void removeFlow(String flowId) {
		Flow flow = getFlow(flowId);
		removeFlow(flow);
	}

	public Flow getFlow(String flowId) {

		return flowIdMap.get(flowId);
	}

	public void addComponents(ComponentDefines components) {
		containers.addComponents(components);
	}

	public void removeComponents(ComponentDefines components) {
		containers.removeComponents(components);
	}

	/**
	 * 根据流程组件信息实例化组件
	 * 
	 * @param componentName
	 *            流程组件名
	 * @return
	 */
	public ComponentInterface getComponentInstance(String componentName) {
		ComponentInterface componentInstance = null;
		if (!StringUtil.isBlank(componentName)) {
			componentInstance = containers.getComponentInstance(componentName);
			return componentInstance;
		}
		throw new FlowRuntimeException("flow.componentNotExist", componentName);
		// throw new FlowRuntimeException("组件名称:" + componentName + "找不到");
	}

	public Context getInputContext(Flow flow, Context context) {
		return getContext(flow.getInputParameters(), context);
	}

	private Context getContext(List<Parameter> parameters, Context context) {
		Context result = new ContextImpl();
		if (parameters != null) {
			for (Parameter parameter : parameters) {
				result.put(parameter.getName(),
						context.get(parameter.getName()));
			}
		}
		return result;
	}

	public Context getOutputContext(Flow flow, Context context) {
		return getContext(flow.getOutputParameters(), context);
	}

	public void addComponent(ComponentDefine component) {
		containers.addComponent(component);
	}

	public void removeComponent(ComponentDefine component) {
		containers.removeComponent(component);
	}

	public ComponentDefine getComponentDefine(String componentName) {
		return containers.getComponentDefine(componentName);
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public List<ComponentDefine> getComponentDefines() {
		return containers.getComponentDefines();
	}

}
