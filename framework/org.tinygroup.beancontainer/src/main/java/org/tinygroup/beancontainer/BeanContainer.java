package org.tinygroup.beancontainer;

import java.util.Collection;
import java.util.List;

import org.tinygroup.vfs.FileObject;


/**
 * Created by luoguo on 2014/5/15.
 */
public interface BeanContainer<C> {
    /**
     * 返回原生的Bean窗口类型
     *
     * @return
     */
    C getBeanContainerPrototype();

    /**
     * 获取子容器
     *
     * @param subBeanContainer
     */
    C getSubBeanContainer(List<FileObject> files,ClassLoader loader);

    /**
     * 返回子窗口列表
     *
     * @return
     */
    List<C> getSubBeanContainers();

    /**
     * 返回指定类型的bean列表
     *
     * @param type
     * @param <T>
     * @return
     */
    <T> Collection<T> getBeans(Class<T> type);

    /**
     * 获取指定名称的Bean
     *
     * @param name
     * @param <T>
     * @return
     */
    <T> T getBean(String name);

    /**
     * 获取指定类型的Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getBean(Class<T> clazz);

    /**
     * 获取指定类型指定名称的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getBean(String name, Class<T> clazz);

}
