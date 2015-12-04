package org.tinygroup.custombeandefine.namegenerator;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.tinygroup.commons.tools.StringUtil;

/**
 * ProxyFactoryBean 名称生成器
 *
 * @author renhui
 */
public class ProxyFactoryBeanNameGenerator implements BeanNameGenerator {

    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        PropertyValues propertyValues = definition.getPropertyValues();
        String interfaceName = StringUtil.substringAfterLast((String) propertyValues.getPropertyValue("proxyInterfaces").getValue(), ".");
        if (registry.containsBeanDefinition(interfaceName)) {
            throw new BeanDefinitionStoreException("beanName:" + interfaceName + "已经在容器中存在,请确保生成的bean名称唯一");
        }
        return StringUtil.uncapitalize(interfaceName);
    }


}
