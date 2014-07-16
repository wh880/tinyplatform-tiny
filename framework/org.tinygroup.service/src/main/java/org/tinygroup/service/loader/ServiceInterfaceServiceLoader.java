package org.tinygroup.service.loader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.tinygroup.commons.beanutil.BeanUtil;
import org.tinygroup.commons.tools.ClassUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.impl.AbstractConfiguration;
import org.tinygroup.event.Parameter;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.service.ServiceInterface;
import org.tinygroup.service.ServiceProxy;
import org.tinygroup.service.exception.ServiceLoadException;
import org.tinygroup.service.registry.ServiceRegistry;
import org.tinygroup.service.registry.ServiceRegistryItem;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xmlparser.node.XmlNode;

public class ServiceInterfaceServiceLoader extends AbstractConfiguration
		implements ServiceLoader {

	private static final String SERVICE_METHOD_NAME = "execute";
	private static final String CLASS_NAME_REGULAR = "class-name-regular";
	private static final String SERVICE_INTERFACE_NODE_PATH = "/application/service-interface";
	private static final int CLASS_SUFFIX_LENGTH = 6;
	private List<ServiceInterface> services = new ArrayList<ServiceInterface>();
	private Logger logger = LoggerFactory
			.getLogger(ServiceInterfaceServiceLoader.class);
	private Pattern pattern;

	public void loadService(ServiceRegistry serviceRegistry)
			throws ServiceLoadException {

		for (ServiceInterface serviceInterface : services) {
			ServiceRegistryItem item = new ServiceRegistryItem();
			item.setServiceId(serviceInterface.getServiceId());
			item.setCategory(serviceInterface.getCategory());
			Method method = getExecuteMethod(serviceInterface.getClass());
			if (method != null) {
				try {
					ServiceProxy serviceProxy = new ServiceProxy();
					serviceProxy.setObjectInstance(serviceInterface);
					serviceProxy.setMethod(method);
					getInputParameterNames(item, method, serviceProxy);
					getOutputParameterNames(item, serviceInterface, method, serviceProxy);
					item.setService(serviceProxy);
					serviceRegistry.registeService(item);
				} catch (Exception e) {
					logger.error("service.loadServiceException", e,
							serviceInterface.getClass().getName());
					throw new ServiceLoadException(e);
				}
			}
		}

	}

	private void getInputParameterNames(ServiceRegistryItem item,
			Method method, ServiceProxy serviceProxy)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		logger.logMessage(LogLevel.INFO, "开始加载方法对应的服务入参,方法{0},服务:{1}",
				method.getName(), item.getServiceId());
		String[] parameterNames = BeanUtil.getMethodParameterName(
				method.getDeclaringClass(), method);
		Class<?>[] parameterTypes = method.getParameterTypes();
		List<Parameter> inputParameterDescriptors = new ArrayList<Parameter>();
		for (int i = 0; i < parameterTypes.length; i++) {
			Parameter descriptor = new Parameter();
			if (implmentInterface(parameterTypes[i], Collection.class)) {
				ParameterizedType pt = (ParameterizedType) (method
						.getGenericParameterTypes()[i]);
				Type[] actualTypeArguments = pt.getActualTypeArguments();
				Class<?> clazz = (Class<?>) actualTypeArguments[0];
				descriptor.setType(clazz.getName());
				descriptor.setCollectionType(parameterTypes[i].getName());
			} else {
				descriptor.setType(parameterTypes[i].getName());
			}
			descriptor.setArray(parameterTypes[i].isArray());
			descriptor.setName(parameterNames[i]);
			inputParameterDescriptors.add(descriptor);
		}
		item.setParameters(inputParameterDescriptors);
		serviceProxy.setInputParameters(inputParameterDescriptors);
		logger.logMessage(LogLevel.INFO, "加载方法对应的服务入参完毕,方法{0},服务:{1}",
				method.getName(), item.getServiceId());

	}

	private void getOutputParameterNames(ServiceRegistryItem item,ServiceInterface serviceInterface,Method method, ServiceProxy serviceProxy)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		logger.logMessage(LogLevel.INFO, "开始加载方法对应的服务出参,方法{0},服务:{1}",
				method.getName(), item.getServiceId());
		Class<?> parameterType = method.getReturnType();
		if (!Void.TYPE.equals(parameterType)) {
			List<Parameter> outputParameterDescriptors = new ArrayList<Parameter>();
			Parameter descriptor = new Parameter();
			descriptor.setName(serviceInterface.getResultKey());
			descriptor.setType(parameterType.getName());
			logger.logMessage(LogLevel.INFO, "服务出参type:{0}",
					descriptor.getType());
			descriptor.setArray(parameterType.isArray());
			serviceProxy.setOutputParameter(descriptor);
			outputParameterDescriptors.add(descriptor);
			item.setResults(outputParameterDescriptors);
		}
		logger.logMessage(LogLevel.INFO, "加载方法对应的服务出参完毕,方法{0},服务:{1}",
				method.getName(), item.getServiceId());

	}

	private boolean implmentInterface(Class<?> clazz, Class<?> interfaceClazz) {
		return interfaceClazz.isAssignableFrom(clazz);
	}

	private Method getExecuteMethod(Class<? extends ServiceInterface> clazz) {
		Method[] methods = clazz.getMethods();
		List<Method> executeMethods = new ArrayList<Method>();
		for (Method method : methods) {
			if (method.getName().equals(SERVICE_METHOD_NAME)) {
				executeMethods.add(method);
			}
		}
		int methodCount = executeMethods.size();
		if (methodCount == 0) {
			logger.logMessage(LogLevel.ERROR, "类名：[{0}]不存在execute方法",
					clazz.getSimpleName());
		} else if (executeMethods.size() > 1) {
			logger.logMessage(LogLevel.ERROR,
					"类名：[{0}]存在多个execute方法，只保留第一个execute方法,方法描述信息：{1}",
					clazz.getSimpleName(),
					ClassUtil.getSimpleMethodSignature(executeMethods.get(0)));
			return executeMethods.get(0);
		} else {
			return executeMethods.get(0);
		}
		return null;
	}

	public void setConfigPath(String path) {

	}

	public void removeService(ServiceRegistry serviceRegistry) {

	}

	public void addClassFileObject(FileObject fileObject,
			ClassLoader classLoader) {
		String className = getFullClassName(fileObject);
		if (pattern.matcher(className).matches()) {
			try {
				Class clazz = classLoader.loadClass(className);
				boolean isAbstract=(clazz.getModifiers()&Modifier.ABSTRACT)==Modifier.ABSTRACT;
				boolean isInterface=clazz.isInterface();
                boolean isAssignableFrom=ServiceInterface.class.isAssignableFrom(clazz);
				if (isAssignableFrom&&!isAbstract&&!isInterface) {
					ServiceInterface serviceInterface = (ServiceInterface) clazz
							.newInstance();
					addServiceInterface(serviceInterface);
				}
			} catch (ClassNotFoundException e) {
				logger.errorMessage("类名:[{0}]未找到", e, className);
			} catch (InstantiationException e) {
				logger.errorMessage("类名:[{0}]不存在无参构造函数", e, className);
			} catch (IllegalAccessException e) {
				logger.errorMessage("类名:[{0}]不存在无参构造函数", e, className);
			}

		}
	}

	public void addServiceInterface(ServiceInterface serviceInterface) {
		if (!services.contains(serviceInterface)) {
			services.add(serviceInterface);
		}
	}

	public String getApplicationNodePath() {
		return SERVICE_INTERFACE_NODE_PATH;
	}

	public String getComponentConfigPath() {
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		super.config(applicationConfig, componentConfig);
		if (applicationConfig != null) {
			String classNameRegular = applicationConfig
					.getAttribute(CLASS_NAME_REGULAR);
			if (StringUtil.isBlank(classNameRegular)) {
				classNameRegular = ".*";
			}
			pattern = Pattern.compile(classNameRegular);
		}
	}

	private String getFullClassName(FileObject fileObject) {
		String fileName = getFilePath(fileObject);
		if (fileName.startsWith("/")) {
			fileName = fileName.substring(1);
		}
		return fileName.replaceAll("/", ".");
	}

	private String getFilePath(FileObject fileObject) {
		String path = fileObject.getPath();
		return path.substring(0, path.length() - CLASS_SUFFIX_LENGTH);
	}

}
