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
package org.tinygroup.config.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.config.ConfigurationManager;
import org.tinygroup.config.impl.ConfigurationManagerImpl;
import org.tinygroup.ini.IniOperator;
import org.tinygroup.ini.Section;
import org.tinygroup.ini.ValuePair;
import org.tinygroup.ini.impl.IniOperatorDefault;
import org.tinygroup.parser.filter.NameFilter;
import org.tinygroup.parser.filter.PathFilter;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

/**
 * 应用配置工具类，用于把父对象中的配置参数应用到子对象中。
 * 
 * @author luoguo
 */
public final class ConfigurationUtil {
	private static final String APPLICATION_PROPERTIES_PROPERTY = "/application/application-properties/property";
	private static final String APPLICATION_PROPERTIES_FILE = "/application/application-properties/file";
	private static ConfigurationManager configurationManager = new ConfigurationManagerImpl();

	// private static Logger logger =
	// LoggerFactory.getLogger(ConfigurationUtil.class);

	private ConfigurationUtil() {
	}

	public static ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	/**
	 * 获取属性值，应用配置的优先级更高
	 * 
	 * @param applicationNode
	 * @param componentNode
	 * @param attributeName
	 * @return
	 */
	public static String getPropertyName(XmlNode applicationNode,
			XmlNode componentNode, String attributeName) {
		String value = null;
		checkNodeName(applicationNode, componentNode);
		if (applicationNode != null) {
			value = applicationNode.getAttribute(attributeName);
		}
		if (value == null && componentNode != null) {
			value = componentNode.getAttribute(attributeName);
		}
		return value;
	}

	/**
	 * 获取属性值，应用配置的优先级更高。<br>
	 * 如果读取的结果为Null或为""，则返回默认值
	 * 
	 * @param applicationNode
	 * @param componentNode
	 * @param attributeName
	 * @param defaultValue
	 * @return
	 */
	public static String getPropertyName(XmlNode applicationNode,
			XmlNode componentNode, String attributeName, String defaultValue) {
		String value = getPropertyName(applicationNode, componentNode,
				attributeName);
		if (value == null || value.trim().length() == 0) {
			value = defaultValue;
		}
		return value;
	}

	private static void checkNodeName(XmlNode applicationNode,
			XmlNode componentNode) {
		if (applicationNode == null || componentNode == null) {// 如果有一个为空，则返回
			return;
		}
		String applicationNodeName = applicationNode.getNodeName();
		String componentNodeName = componentNode.getNodeName();
		if (applicationNodeName != null && componentNodeName != null
				&& !applicationNodeName.equals(componentNodeName)) {
			throw new RuntimeException(applicationNodeName + "与"
					+ componentNodeName + "两个节点名称不一致！");
		}
	}

	/**
	 * 根据关键属性进行子节点合并
	 * 
	 * @param applicationNode
	 * @param componentNode
	 * @param keyPropertyName
	 * @return
	 */
	public static List<XmlNode> combineSubList(XmlNode applicationNode,
			XmlNode componentNode, String nodeName, String keyPropertyName) {
		checkNodeName(applicationNode, componentNode);
		List<XmlNode> result = new ArrayList<XmlNode>();
		if (applicationNode == null && componentNode == null) {
			return result;
		}
		List<XmlNode> applicationNodeList = getNodeList(applicationNode,
				nodeName);
		List<XmlNode> componentNodeList = getNodeList(componentNode, nodeName);
		if (componentNodeList.isEmpty()) {// 如果组件配置为空
			result.addAll(applicationNodeList);
			return result;
		}
		if (applicationNodeList.isEmpty()) {// 如果应用配置为空
			result.addAll(componentNodeList);
			return result;
		}
		combineSubList(keyPropertyName, result, applicationNodeList,
				componentNodeList);
		return result;
	}

	private static List<XmlNode> getNodeList(XmlNode node, String nodeName) {
		List<XmlNode> nodeList = new ArrayList<XmlNode>();
		if (node != null) {
			nodeList = node.getSubNodes(nodeName);
		}
		return nodeList;
	}

	private static void combineSubList(String keyPropertyName,
			List<XmlNode> result, List<XmlNode> applicationNodeList,
			List<XmlNode> componentNodeList) {
		Map<String, XmlNode> appConfigMap = nodeListToMap(applicationNodeList,
				keyPropertyName);
		Map<String, XmlNode> compConfigMap = nodeListToMap(componentNodeList,
				keyPropertyName);
		for (String key : appConfigMap.keySet()) {
			XmlNode compNode = compConfigMap.get(key);
			XmlNode appNode = appConfigMap.get(key);
			if (compNode == null) {
				result.add(appNode);
			} else {// 如果两个都有，则合并之
				result.add(combine(appNode, compNode));
			}
		}
		for (String key : compConfigMap.keySet()) {
			// 判断是否配置了应用级别的信息
			XmlNode appNode = appConfigMap.get(key);
			// 未配置应用级别的信息，使用默认的组件级别信息
			if (appNode == null) {
				result.add(compConfigMap.get(key));
			}
		}
	}

	/**
	 * 合并单个节点
	 * 
	 * @param applicationNode
	 * @param componentNode
	 * @return
	 */
	public static XmlNode combineXmlNode(XmlNode applicationNode,
			XmlNode componentNode) {
		checkNodeName(applicationNode, componentNode);
		if (applicationNode == null && componentNode == null) {
			return null;
		}
		XmlNode result = null;
		if (applicationNode != null && componentNode == null) {
			result = applicationNode;
		} else if (applicationNode == null && componentNode != null) {
			result = componentNode;
		} else {
			result = combine(applicationNode, componentNode);
		}
		return result;
	}

	private static XmlNode combine(XmlNode appNode, XmlNode compNode) {
		XmlNode result = new XmlNode(appNode.getNodeName());
		result.setAttribute(compNode.getAttributes());
		result.setAttribute(appNode.getAttributes());
		if (!CollectionUtil.isEmpty(compNode.getSubNodes())) {
			result.addAll(compNode.getSubNodes());
		}
		if (!CollectionUtil.isEmpty(appNode.getSubNodes())) {
			result.addAll(appNode.getSubNodes());
		}
		return result;
	}

	private static Map<String, XmlNode> nodeListToMap(List<XmlNode> subNodes,
			String keyPropertyName) {
		Map<String, XmlNode> nodeMap = new HashMap<String, XmlNode>();
		for (XmlNode node : subNodes) {
			String value = node.getAttribute(keyPropertyName);
			nodeMap.put(value, node);
		}
		return nodeMap;
	}

	/**
	 * 简单合并
	 * 
	 * @param applicationNode
	 * @param componentNode
	 * @return
	 */
	public static List<XmlNode> combineSubList(XmlNode applicationNode,
			XmlNode componentNode) {

		checkNodeName(applicationNode, componentNode);
		List<XmlNode> result = new ArrayList<XmlNode>();
		if (applicationNode == null && componentNode == null) {
			return result;
		}
		if (componentNode != null && componentNode.getSubNodes() != null) {
			result.addAll(componentNode.getSubNodes());
		}
		if (applicationNode != null && applicationNode.getSubNodes() != null) {
			result.addAll(applicationNode.getSubNodes());
		}
		return result;
	}

	/**
	 * 简单合并
	 * 
	 * @param nodeName
	 * @param applicationNode
	 * @param componentNode
	 * @return
	 */
	public static List<XmlNode> combineSubList(String nodeName,
			XmlNode applicationNode, XmlNode componentNode) {
		checkNodeName(applicationNode, componentNode);
		List<XmlNode> result = new ArrayList<XmlNode>();
		if (applicationNode == null && componentNode == null) {
			return result;
		}
		if (componentNode != null
				&& componentNode.getSubNodes(nodeName) != null) {
			result.addAll(componentNode.getSubNodes(nodeName));
		}
		if (applicationNode != null
				&& applicationNode.getSubNodes(nodeName) != null) {
			result.addAll(applicationNode.getSubNodes(nodeName));
		}
		return result;
	}

	/**
	 * 简单合并
	 * 
	 * @param nodeName
	 * @param applicationNode
	 * @param componentNode
	 * @return
	 */
	public static List<XmlNode> combineFindNodeList(String nodeName,
			XmlNode applicationNode, XmlNode componentNode) {
		checkNodeName(applicationNode, componentNode);
		List<XmlNode> result = new ArrayList<XmlNode>();
		if (applicationNode == null && componentNode == null) {
			return result;
		}
		if (componentNode != null) {
			NameFilter<XmlNode> nameFilter = new NameFilter<XmlNode>(
					componentNode);
			List<XmlNode> nodes = nameFilter.findNodeList(nodeName);
			if (nodes != null) {
				result.addAll(nodes);
			}
		}
		if (applicationNode != null) {
			NameFilter<XmlNode> nameFilter = new NameFilter<XmlNode>(
					applicationNode);
			List<XmlNode> nodes = nameFilter.findNodeList(nodeName);
			if (nodes != null) {
				result.addAll(nodes);
			}
		}
		return result;
	}

	public static String replace(String content, String name, String value) {

		Pattern pattern = Pattern.compile("[{]" + name + "[}]");
		Matcher matcher = pattern.matcher(content);
		StringBuilder buf = new StringBuilder();
		int curpos = 0;
		while (matcher.find()) {
			buf.append(content.substring(curpos, matcher.start()));
			curpos = matcher.end();
			buf.append(value);
			continue;
		}
		buf.append(content.substring(curpos));
		return buf.toString();
	}

	public static XmlNode parseXmlFromFileObject(FileObject fileObject)
			throws IOException {
		String config = StreamUtil.readText(fileObject.getInputStream(),
				"UTF-8", true);
		return new XmlStringParser().parse(config).getRoot();
	}

	public static XmlNode loadApplicationConfig(String config) {
		XmlNode applicationConfig = new XmlStringParser().parse(config)
				.getRoot();// 第一次解出
		Map<String, String> applicationPropertiesMap = new HashMap<String, String>();
		loadApplicationProperties(applicationConfig, applicationPropertiesMap);
		loadApplicationPropertyFiles(applicationConfig,
				applicationPropertiesMap);
		String newConfig = replaceProperty(config, applicationPropertiesMap);// 替换里面的全局变量
		return new XmlStringParser().parse(newConfig).getRoot();// 再次解析，出来最终结果
	}

	private static void loadApplicationProperties(XmlNode applicationConfig,
			Map<String, String> applicationPropertiesMap) {
		PathFilter<XmlNode> filter = new PathFilter<XmlNode>(applicationConfig);
		List<XmlNode> propertyList = filter
				.findNodeList(APPLICATION_PROPERTIES_PROPERTY);
		for (XmlNode property : propertyList) {
			String name = property.getAttribute("name");
			String value = property.getAttribute("value");
			applicationPropertiesMap.put(name, value);
			getConfigurationManager().setConfiguration(name, value);
		}
	}

	private static void loadApplicationPropertyFiles(XmlNode applicationConfig,
			Map<String, String> applicationPropertiesMap) {
		PathFilter<XmlNode> filter = new PathFilter<XmlNode>(applicationConfig);
		List<XmlNode> propertyList = filter
				.findNodeList(APPLICATION_PROPERTIES_FILE);
		for (XmlNode property : propertyList) {
			String path = property.getAttribute("path");
			if (path.endsWith(".ini")) {
				loadApplicationPropertyIniFile(path, applicationPropertiesMap);
			} else if (path.endsWith(".properties")) {
				loadApplicationPropertyPropertiesFile(path,
						applicationPropertiesMap);
			}

		}
	}

	private static void loadApplicationPropertyPropertiesFile(String path,
			Map<String, String> applicationPropertiesMap) {
		Properties p = new Properties();
		InputStream in = ConfigurationUtil.class.getResourceAsStream(path);
		try {
			p.load(in);
			in.close();
		} catch (IOException e) {
			throw new RuntimeException("读取配置文件:" + path + "时出错", e);
		}
		if (p.size() <= 0) {
			return;
		}
		for (Object key : p.keySet()) {
			String value = p.getProperty(key.toString());
			applicationPropertiesMap.put(key.toString(), value);
			getConfigurationManager().setConfiguration(key.toString(), value);
		}

	}

	private static void loadApplicationPropertyIniFile(String path,
			Map<String, String> applicationPropertiesMap) {
		IniOperator operator = new IniOperatorDefault();
		try {
			File file = new File(ConfigurationUtil.class.getClassLoader()
					.getResource(path).toURI());
			operator.read(new FileInputStream(file), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("读取配置文件:" + path + "时出错", e);
		}
		List<Section> sectionList = operator.getSections().getSectionList();
		for (Section section : sectionList) {
			List<ValuePair> valuePairs = section.getValuePairList();
			for (ValuePair valuePair : valuePairs) {
				String key = valuePair.getKey();
				String value = valuePair.getValue();
				applicationPropertiesMap.put(key, value);
				getConfigurationManager().setConfiguration(key, value);
			}
		}
	}

	private static String replaceProperty(String config,
			Map<String, String> applicationPropertiesMap) {
		String result = config;
		if (!applicationPropertiesMap.isEmpty()) {
			for (String name : applicationPropertiesMap.keySet()) {
				String value = applicationPropertiesMap.get(name);
				result = replace(result, name, value);
			}
		}
		return result;
	}
}
