package org.tinygroup.aopcache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRemove {
    /**
     * 要移除的组名
     * @return
     */
    String group() default "";
    /**
     * 从缓存移除key,多个key以逗号分隔开
     * @return
     */
    String[] removeKeys() default {};
    /**
     * 从缓存移除group,多个组以逗号分隔开
     * @return
     */
    String[] removeGroups() default {};
}
