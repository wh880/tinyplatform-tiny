package org.tinygroup.servicewrapper;

import java.lang.reflect.Method;

/**
 * 解析方法中的服务号
 * @author renhui
 *
 */
public interface ServiceIdAnaly {
	
	public String analyMethod(Method method);

}
