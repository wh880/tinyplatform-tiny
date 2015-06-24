package org.tinygroup.springmvc.coc;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Set;

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