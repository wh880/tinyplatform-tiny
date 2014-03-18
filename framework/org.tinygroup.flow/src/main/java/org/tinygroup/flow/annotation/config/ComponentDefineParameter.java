package org.tinygroup.flow.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentDefineParameter {

	String name();
	
	String type();
	
	String title() default "";
	
	String description() default "";
	
}
