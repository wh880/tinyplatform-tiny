package org.tinygroup.flow.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentDefine {

	String name();
	
	String bean();
	
	String category() default "";
	
	String title() default "";
	
	String icon() default "";
	
	String shortDescription() default "";
	
	String longDescription() default "";
	
}
