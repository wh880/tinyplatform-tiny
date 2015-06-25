/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
import org.tinygroup.template.rumtime.U;

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

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {

		if (parameters.length == 0 || !(parameters[0] instanceof String)) {
	        notSupported(parameters);
	    }
		
		String methodName = parameters[0].toString();
		try {
			Method method = clazz.getMethod(methodName, U.getParameterTypes(clazz, methodName));
			Object[] objects = Arrays.copyOfRange(parameters, 1, parameters.length);
			return method.invoke(null, objects);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}
	

}
