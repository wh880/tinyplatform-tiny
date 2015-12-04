package org.tinygroup.aopcache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheGet {
    /**
     * 缓存的key
     *
     * @return
     */
    String[] key() default {};
    /**
     * 缓存项所在的组
     * @return
     */
    String group() default "";
    
}
