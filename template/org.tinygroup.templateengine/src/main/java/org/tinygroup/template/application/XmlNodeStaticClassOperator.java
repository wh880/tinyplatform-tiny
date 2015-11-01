package org.tinygroup.template.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.MethodUtils;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.xmlparser.node.XmlNode;

public class XmlNodeStaticClassOperator extends AbstractStaticClassOperator{

	private static final String STATIC_METHOD ="static-method";
	/**
	 * 用户自定义的方法别名缓存
	 */
	private Map<String,MethodConfig> nameMaps = new ConcurrentHashMap<String,MethodConfig>();
	
	/**
	 *  真实的方法名缓存(存在重载方法名)
	 */
	private Map<String,List<MethodConfig>> methodNameMaps = new ConcurrentHashMap<String,List<MethodConfig>>();
	
	/**
	 * 注册类和方法(推荐方式)
	 * @param xmlNode
	 * @throws Exception 
	 * @throws Exception 
	 */
	public XmlNodeStaticClassOperator(XmlNode clazzNode) throws Exception{
		super();
		//加载静态类配置
		setName(clazzNode.getAttribute("name"));
		setStaticClass(loadClass(clazzNode.getAttribute("class")));
		
		//加载静态方法配置
		List<XmlNode> staticMethods = clazzNode.getSubNodes(STATIC_METHOD);
		if(staticMethods!=null){
		   for(XmlNode methodNode:staticMethods){
			   MethodConfig config = new MethodConfig(methodNode);
			   if(!StringUtil.isEmpty(config.getName())){
				   addMethodConfigByName(config);
			   }
			   if(!StringUtil.isEmpty(config.getMethodName())){
				   addMethodConfigByMethodName(config);
			   }
		   }
		}
	}
	
    private void addMethodConfigByName(MethodConfig config){
    	nameMaps.put(config.getName(), config);
	}
	
	private void addMethodConfigByMethodName(MethodConfig config){
		List<MethodConfig> methodList = methodNameMaps.get(config.getMethodName());
		if(methodList==null){
		   methodList = new ArrayList<MethodConfig>();
		   methodNameMaps.put(config.getMethodName(), methodList);
		}
		methodList.add(config);
	}
	
	private Class<?> loadClass(String className) throws Exception{
		return getClass().getClassLoader().loadClass(className);
	}

	private MethodConfig findMethodConfig(String name,Object[] args){
		if(nameMaps.containsKey(name)){
		   return nameMaps.get(name);
		}
		List<MethodConfig> methods = methodNameMaps.get(name);
		if(methods!=null){
		   for(MethodConfig config:methods){
			  if(isMatch(args,config.getParameterTypes())){
				 return config;
			  }
		   }
		}
		return null;
	}
	
	/**
	 * 检查参数和参数列表是否一致
	 * @param args
	 * @param parameterTypes
	 * @return
	 */
	private boolean isMatch(Object[] args,Class[] parameterTypes){
		//存在参数为空的场景
		if(args==null || parameterTypes==null){
		   if(args==null && parameterTypes==null){
			  return true;
		   }
		   return false;
		}
		//两者长度不一致
		if(args.length!=parameterTypes.length){
		   return false;
		}
		//逐一检查每个参数
		int count=0;
		for(int i=0;i<args.length;i++){
		   if(args[i]==null || args[i].getClass().equals(parameterTypes[i])){
			  count++;
		   }
		}
		return parameterTypes.length==count;
	}
	
	public Object invokeStaticMethod(String methodName,
			Object[] args) throws Exception {
		MethodConfig config = findMethodConfig(methodName,args);
		if(config!=null){
			return MethodUtils.invokeStaticMethod(getStaticClass(), config.getMethodName(), args , config.getParameterTypes());
		}else{
			return MethodUtils.invokeStaticMethod(getStaticClass(), methodName, args);
		}
	}
	
	class MethodConfig {
		String name;
		String methodName;
		Class[] parameterTypes;
		
		public MethodConfig(XmlNode methodNode) throws Exception{
			name = methodNode.getAttribute("name");
			methodName = methodNode.getAttribute("method-name");
			
			String parameterType = methodNode.getAttribute("parameter-type");
			if(!StringUtil.isEmpty(parameterType)){
			   String[] classes = parameterType.split(",");
			   parameterTypes = new Class[classes.length];
			   for(int i=0;i<classes.length;i++){
				   parameterTypes[i] = loadClass(classes[i]);
			   }
			}
		}
		
		public String getName(){
			return name;
		}
		/**
		 * 获得方法名
		 * @return
		 */
		public String getMethodName(){
		   return methodName;
		}

		public Class[] getParameterTypes() {
			return parameterTypes;
		}
		
	}


}
