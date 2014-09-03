package org.tinygroup.beanwrapper;

import java.beans.PropertyEditor;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.springutil.SpringBeanContainer;

public class BeanWrapperHolder {

	private BeanWrapperImpl beanWrapper;

	public BeanWrapperHolder() {
		beanWrapper = new BeanWrapperImpl();
		SpringBeanContainer container = (SpringBeanContainer) BeanContainerFactory
				.getBeanContainer(getClass().getClassLoader());
		if(container!=null){
			DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ((FileSystemXmlApplicationContext) container
					.getBeanContainerPrototype()).getBeanFactory();
			Map customEditors = beanFactory.getCustomEditors(); 
			Set keySet = customEditors.keySet();
			for (Object key : keySet) {
				Class requiredType = (Class) key;
				if (customEditors.get(requiredType) instanceof Class) {
					try {
						beanWrapper.registerCustomEditor(requiredType,
								(PropertyEditor) ((Class) customEditors
										.get(requiredType)).newInstance());
					} catch (Exception e) {
						throw new RuntimeException("注册客户自定义类型转换出错", e);
					}
				}
				if (customEditors.get(requiredType) instanceof PropertyEditor) {
					beanWrapper.registerCustomEditor(requiredType,
							(PropertyEditor) customEditors.get(requiredType));
				}
			}
		}
	}
	
	public BeanWrapperImpl getBeanWrapper(){
		return beanWrapper;
	}

}
