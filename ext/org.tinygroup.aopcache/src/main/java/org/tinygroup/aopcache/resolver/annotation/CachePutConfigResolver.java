package org.tinygroup.aopcache.resolver.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.tinygroup.aopcache.annotation.CachePut;
import org.tinygroup.aopcache.config.CacheAction;
import org.tinygroup.aopcache.exception.AopCacheException;

public class CachePutConfigResolver extends AbstractAnnotationConfigResolver{

	public boolean annotationMatch(Annotation annotation) {
		return annotation.annotationType().equals(CachePut.class);
	}

	public CacheAction resolve(Method method) {
		CachePut cachePut = AnnotationUtils.findAnnotation(method,
				CachePut.class);
		if (cachePut == null) {
			throw new AopCacheException(String.format("方法:%s,不存在类型为:%s的注解",
					method.getName(), CachePut.class.getName()));
		}
		org.tinygroup.aopcache.config.CachePut cachePutAction=new org.tinygroup.aopcache.config.CachePut();
		cachePutAction.setKeys(cachePut.keys());
		cachePutAction.setGroup(cachePut.group());
		cachePutAction.setExpire(cachePut.expire());
		cachePutAction.setParameterNames(cachePut.parameterNames());
		cachePutAction.setRemoveKeys(cachePut.removeKeys());
		cachePutAction.setRemoveGroups(cachePut.removeGroups());
		return cachePutAction;
	}

}
