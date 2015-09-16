package org.tinygroup.webservice.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.tinygroup.webservice.instanceresolver.TinyInstanceResolver;

import com.sun.xml.ws.api.server.InstanceResolverAnnotation;



/**
 * 用于标记执行tiny服务的InstanceResolver
 * @author renhui
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
@Documented
@InstanceResolverAnnotation(TinyInstanceResolver.class)
public @interface TinyWebService {

}
