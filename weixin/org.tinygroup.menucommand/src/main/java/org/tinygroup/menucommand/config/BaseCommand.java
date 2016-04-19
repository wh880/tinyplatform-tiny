package org.tinygroup.menucommand.config;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.menucommand.exception.MenuCommandException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 基础命令对象
 * @author yancheng11334
 *
 */
public class BaseCommand extends BaseObject{

	/**
	 * 对应的处理类bean名称
	 */
	@XStreamAlias("bean-name")
	@XStreamAsAttribute
	private String beanName;
	
	/**
	 * 对应的处理类名称
	 */
	@XStreamAlias("class-name")
	@XStreamAsAttribute
	private String className;
	
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * 创建实例
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T createCommandObject() {
		// 先根据bean查找
		if (!StringUtil.isEmpty(getBeanName())) {
			try {
				return BeanContainerFactory.getBeanContainer(getClass()
						.getClassLoader()).getBean(getBeanName());
			} catch (Exception e) {
				throw new MenuCommandException(String.format("bean名称为[%s],实例化bean失败",
						getBeanName()), e);
			}
		}

		// 再根据class查找
		if (!StringUtil.isEmpty(getClassName())) {
			try {
				return (T) getClass().getClassLoader()
						.loadClass(getClassName()).newInstance();
			} catch (Exception e) {
				throw new MenuCommandException(String.format("类名称为[%s],实例化类失败",
						getClassName()), e);
			}
		}
		
		throw new MenuCommandException(String.format("名称为[%s]的BaseCommand没有匹配处理对象,实例化失败!", getName()));
	}
}
