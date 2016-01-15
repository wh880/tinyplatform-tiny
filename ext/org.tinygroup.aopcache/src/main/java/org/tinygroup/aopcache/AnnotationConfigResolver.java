package org.tinygroup.aopcache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.tinygroup.aopcache.config.CacheAction;

public interface AnnotationConfigResolver {
	
	/**
	 * 解析器匹配的注解
	 * @param annotation
	 * @return
	 */
	boolean annotationMatch(Annotation annotation);
	
	/**
	 * 解析匹配注解的内容，组装成CacheAction实例返回
	 * @param method
	 * @return
	 */
	CacheAction resolve(Method method);

}
