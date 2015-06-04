package org.tinygroup.springmvc.coc;

import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author renhui
 *
 */
public interface CustomHandlerMethodResolver {

	Class<?> getHandlerType();

	Method getHandlerMethod(HttpServletRequest request);

	Set<String> resolve();
}