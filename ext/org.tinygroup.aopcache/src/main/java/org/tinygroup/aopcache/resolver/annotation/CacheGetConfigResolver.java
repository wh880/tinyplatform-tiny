package org.tinygroup.aopcache.resolver.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.tinygroup.aopcache.annotation.CacheGet;
import org.tinygroup.aopcache.config.CacheAction;
import org.tinygroup.aopcache.exception.AopCacheException;

public class CacheGetConfigResolver extends AbstractAnnotationConfigResolver {

	public boolean annotationMatch(Annotation annotation) {
		return annotation.annotationType().equals(CacheGet.class);
	}

	public CacheAction resolve(Method method) {
		CacheGet cacheGet = AnnotationUtils.findAnnotation(method,
				CacheGet.class);
		if (cacheGet == null) {
			throw new AopCacheException(String.format("方法:%s,不存在类型为:%s的注解",
					method.getName(), CacheGet.class.getName()));
		}
		org.tinygroup.aopcache.config.CacheGet cacheGetAction=new org.tinygroup.aopcache.config.CacheGet();
		cacheGetAction.setKey(cacheGet.key());
		cacheGetAction.setGroup(cacheGet.group());
		return cacheGetAction;
	}

}
