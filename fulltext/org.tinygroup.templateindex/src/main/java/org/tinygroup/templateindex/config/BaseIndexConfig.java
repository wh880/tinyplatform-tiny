package org.tinygroup.templateindex.config;

import java.util.Set;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.templateindex.TemplateIndexOperator;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 默认的索引配置项基类
 * @author yancheng11334
 *
 */
@XStreamAlias("index-config-loader")
public abstract class BaseIndexConfig {

	/**
	 * 返回需要查询匹配的字段组
	 * @return
	 */
	public abstract Set<String> getQueryFields();
	
	/**
	 * 返回可以处理该索引配置的bean名称
	 * @return
	 */
	public abstract String getBeanName();
	
	@SuppressWarnings("rawtypes")
	public TemplateIndexOperator getTemplateIndexOperator(){
		return BeanContainerFactory.getBeanContainer(this.getClass().getClassLoader()).getBean(getBeanName());
	}
}
