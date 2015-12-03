package org.tinygroup.aopcache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CachePut {

    /**
     * 要缓冲的对象参数名称,多个参数名称以逗号分隔
     *
     * @return
     */
    String[] parameterNames() default {};

    /**
     * 缓存的keys
     *
     * @return
     */
    String[] keys() default {};
    
    String group() default "";
    
    /**
     * 要从缓存移除的key,多个key以逗号分隔开
     * @return
     */
    String[] removeKeys() default {};
    
    /**
     * 从缓存移除group,多个组以逗号分隔开
     * @return
     */
    String[] removeGroups() default {};
    /**
     * 设置缓存过期时间，默认不过期
     * @return
     */
    long expire() default Long.MAX_VALUE;

}
