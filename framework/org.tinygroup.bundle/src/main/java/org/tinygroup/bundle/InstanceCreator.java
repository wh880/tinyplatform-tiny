package org.tinygroup.bundle;

/**
 * 实例创建器
 * Created by luoguo on 14-3-18.
 */
public interface InstanceCreator<T> {
    /**
     * 创建实例
     *
     * @param className   要创建的类的名称
     * @param classLoader 类加载器
     * @return
     */
    T getInstance(String className, ClassLoader classLoader);
}
