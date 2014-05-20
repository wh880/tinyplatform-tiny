package org.tinygroup.beancontainer;

import org.tinygroup.exception.TinyBizRuntimeException;

public class BeanContainerFactory {
	public static BeanContainer<?> container;
	
	public static void setBeanContainer(String beanClassName){
		try {
			container = (BeanContainer)Class.forName(beanClassName).newInstance();
		} catch (Exception e) {
			throw new TinyBizRuntimeException(e, "beancontainer_init_failed",beanClassName);
		}
	}
	public static BeanContainer<?> getBeanContainer(){
		return container;
	}
}
