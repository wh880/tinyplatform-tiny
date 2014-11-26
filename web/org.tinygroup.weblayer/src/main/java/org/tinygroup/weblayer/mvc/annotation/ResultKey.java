package org.tinygroup.weblayer.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 结果保存名称，Tiny框架会把方法返回值以该名称保存到上下文。
 * @author yancheng11334
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResultKey {

	String value() default "resultKey";
}
