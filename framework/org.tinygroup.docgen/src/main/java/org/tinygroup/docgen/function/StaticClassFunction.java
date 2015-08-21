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
package org.tinygroup.docgen.function;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.function.AbstractTemplateFunction;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 调用静态类的方法
 * @author yancheng11334
 *
 */
public class StaticClassFunction extends AbstractTemplateFunction {
    private Class<?> clazz;
	//静态类的配置名
	public StaticClassFunction(String names,String className) {
		super(names);
		try {
			clazz = this.getClass().getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public StaticClassFunction(String names,Class<?> clazz) {
		super(names);
		this.clazz= clazz;
	}

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {

		if (parameters.length == 0 || !(parameters[0] instanceof String)) {
	        notSupported(parameters);
	    }
		
		String methodName = parameters[0].toString();
		try {
			Object[] objects = Arrays.copyOfRange(parameters, 1, parameters.length);
			Method method = findMethod(methodName,objects);
			return method.invoke(null, objects);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}
	
	private Method findMethod(String methodName,Object[] objects) throws Exception{
	    for(Method method : clazz.getDeclaredMethods()){
	    	Class<?>[] parameterTypes =  method.getParameterTypes();
	    	if(method.getName().equals(methodName) && checkParameter(objects,parameterTypes)){
	    	   return method;
	    	}
	    }
	    throw new TemplateException("没有找到匹配的方法名:"+methodName);
	}

	private boolean checkParameter(Object[] objects,Class<?>[] parameterTypes){
		if(parameterTypes.length!=objects.length){
		   return false;
		}
		for(int i=0;i<objects.length;i++){
		   if(!parameterTypes[i].isInstance(objects[i])){
			  return false;
		   }
		}
		return true;
	}
	
}