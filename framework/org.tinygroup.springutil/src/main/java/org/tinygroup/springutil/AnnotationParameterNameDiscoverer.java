package org.tinygroup.springutil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.springframework.core.ParameterNameDiscoverer;
import org.tinygroup.commons.tools.ArrayUtil;
import org.tinygroup.springutil.annotation.MethodParamName;

/**
 * 从方法参数注解MethodParamName获取方法参数名称
 * @author renhui
 *
 */
public class AnnotationParameterNameDiscoverer implements
		ParameterNameDiscoverer {
	

	public String[] getParameterNames(Method method) {
		Class[] paramTypes =method.getParameterTypes();
		String[] names=new String[paramTypes.length];
		Annotation[][] parameterAnnotations=method.getParameterAnnotations();
		int annotationsFound=0;
		for (int i = 0; i < names.length; i++) {
			Annotation[] annotations=parameterAnnotations[i];
			if(!ArrayUtil.isEmptyArray(annotations)){
				for (Annotation annotation : annotations) {
					if (MethodParamName.class.isInstance(annotation)) {
						MethodParamName methodParamName = (MethodParamName) annotation;
						names[i]=methodParamName.value();
						annotationsFound++;
					}
				}
			}
		}
		if(annotationsFound==0){//发现方法参数没有标记@MethodParamName注解,就从方法字节码中获取参数名称
		  return MethodNameAccessTool.getMethodParameterName(method);
		}
		if(annotationsFound!=names.length){
             throw new RuntimeException(String.format("方法：%s,所有参数对象需要增加MethodParamName注解", method.getName()));		
		}
		return names;
	}

	public String[] getParameterNames(Constructor ctor) {
		return null;
	}
	
}
