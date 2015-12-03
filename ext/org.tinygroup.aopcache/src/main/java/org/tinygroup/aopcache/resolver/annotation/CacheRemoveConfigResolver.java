package org.tinygroup.aopcache.resolver.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.tinygroup.aopcache.annotation.CacheRemove;
import org.tinygroup.aopcache.config.CacheAction;
import org.tinygroup.aopcache.exception.AopCacheException;

public class CacheRemoveConfigResolver extends AbstractAnnotationConfigResolver {

	public boolean annotationMatch(Annotation annotation) {
		return annotation.annotationType().equals(CacheRemove.class);
	}

	public CacheAction resolve(Method method) {
		CacheRemove cacheRemove = AnnotationUtils.findAnnotation(method,
				CacheRemove.class);
		if (cacheRemove == null) {
			throw new AopCacheException(String.format("方法:%s,不存在类型为:%s的注解",
					method.getName(), CacheRemove.class.getName()));
		}
		org.tinygroup.aopcache.config.CacheRemove cacheRemoveAction=new org.tinygroup.aopcache.config.CacheRemove();
		cacheRemoveAction.setGroup(cacheRemove.group());
		cacheRemoveAction.setRemoveKeys(cacheRemove.removeKeys());
		cacheRemoveAction.setRemoveGroups(cacheRemove.removeGroups());
		return cacheRemoveAction;
	}

}
