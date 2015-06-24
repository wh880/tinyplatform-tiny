package org.tinygroup.springmvc.handleradapter;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;

/**
 * 基于注解的方法处理适配器
 * 
 */
public class DefaultAnnotationMethodHandlerAdapter extends
		AbstractMethodHandlerAdapter {


	@Override
	public boolean supports(Object handler) {
		Annotation controller = AnnotationUtils.findAnnotation(
				handler.getClass(), Controller.class);
		if (controller == null) {
			controller = AnnotationUtils.findAnnotation(handler.getClass(),
					RequestMapping.class);
		}
		if (controller != null
				&& getMethodResolver(handler).hasHandlerMethods()) {
			return true;
		}
		//
		if (isConventionHandler(handler)) {
			return true;
		}
		return false;
	}

}
