package org.tinygroup.custombeandefine.impl;

import java.util.List;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.tinygroup.custombeandefine.BeanDefineCreate;

public class ProxyFactoryBeanDefineCreate implements BeanDefineCreate {
	
	private static final String PROXY_FACTORY_BEAN = "org.springframework.aop.framework.ProxyFactoryBean";
	private List<String> interceptorNames;

	public List<String> getInterceptorNames() {
		return interceptorNames;
	}

	public void setInterceptorNames(List<String> interceptorNames) {
		this.interceptorNames = interceptorNames;
	}

	public static String getProxyFactoryBean() {
		return PROXY_FACTORY_BEAN;
	}

	public BeanDefinition createBeanDefinition(MetadataReader metadataReader){
		ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(
				metadataReader);
		sbd.setBeanClassName(PROXY_FACTORY_BEAN);
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		ClassMetadata classMetadata=metadataReader.getClassMetadata();
		if(classMetadata.isInterface()){
			propertyValues.addPropertyValue("proxyInterfaces",
					classMetadata.getClassName());
			propertyValues.addPropertyValue("interceptorNames",
					interceptorNames);
			sbd.setPropertyValues(propertyValues);
			return sbd;
		}
		return null;
	}

}
