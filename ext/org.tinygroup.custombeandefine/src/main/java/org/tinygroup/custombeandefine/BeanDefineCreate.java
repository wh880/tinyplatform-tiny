package org.tinygroup.custombeandefine;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.classreading.MetadataReader;

/**
 * BeanDefinition创建接口
 * @author renhui
 *
 */
public interface BeanDefineCreate {

	/**
	 * 根据MetadataReader相关的类信息创建相应的BeanDefinition对象
	 * @param metadataReader 
	 * @return
	 */
	BeanDefinition createBeanDefinition(MetadataReader metadataReader);
	
}
