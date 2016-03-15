package org.tinygroup.mockservice;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.service.exception.ServiceExecuteException;

public class MockService implements ServiceInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4345831876683679140L;
	private String serviceId;
	private String category;
	private List<Parameter> parameters = new ArrayList<Parameter>();
	private List<Parameter> results = new ArrayList<Parameter>();
	transient Type resultType;
	transient Class<?> type;
	transient String methodName;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MockService.class);

	public int compareTo(ServiceInfo o) {
		return o.getServiceId().compareTo(serviceId);
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public List<Parameter> getResults() {
		return results;
	}

	public void setResults(List<Parameter> results) {
		this.results = results;
	}

	private Method findMethod() {
		Class<?>[] argsType = null;// 参数类型列表
		if (parameters != null) {
			argsType = new Class<?>[parameters.size()];
			for (int i = 0; i < argsType.length; i++) {
				argsType[i] = getClassByName(parameters.get(i));
			}
		}
		try {
			return type.getMethod(methodName, argsType);
		} catch (Exception e) {
			LOGGER.errorMessage("获取方法时出现异常,方法名:{methodName}", e, methodName);
			throw new RuntimeException("获取方法时出现异常,方法名:{" + methodName + "}", e);
		}

	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public Type getResultType(){
		if(resultType==null){
			Method m = findMethod();
			resultType = m.getGenericReturnType();
		}
		return resultType;
	}
	
	public String getResultName(){
		if(results.size()==0){
			return null;
		}
		return results.get(0).getName();
	}
	private Class<?> getClassByName(Parameter param) {
		String name = param.getType();// 参数类型名默认取参数type
		if (param.getCollectionType() != null) { // 如果配置了参数集合类型，则参数类型取集合类型的值
			name = param.getCollectionType();
		}
		Class<?> clazz = getClassByName(name);
		if (param.isArray()) {
			return Array.newInstance(clazz, 1).getClass();
		} else {
			return clazz;
		}

	}
	
	private Class<?> getClassByName(String name) {
		try {
			if ("int".equals(name)) {
				return int.class;
			} else if ("byte".equals(name)) {
				return byte.class;
			} else if ("long".equals(name)) {
				return long.class;
			} else if ("short".equals(name)) {
				return short.class;
			} else if ("char".equals(name)) {
				return char.class;
			} else if ("double".equals(name)) {
				return double.class;
			} else if ("float".equals(name)) {
				return float.class;
			} else if ("boolean".equals(name)) {
				return boolean.class;
			}

			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new ServiceExecuteException(e);
		}
	}
}
