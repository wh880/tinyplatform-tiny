package org.tinygroup.weblayer;

import java.util.Iterator;
import java.util.Map;


/**
 * tinyfilter的配置对象
 * @author renhui
 *
 */
public interface BasicTinyConfig {
	
    /**
     * 获取名称
     * @return
     */
	public String getConfigName();
	
	/**
	 * 根据参数名称获取参数值
	 * @param name
	 * @return
	 */
	public String getInitParameter(String name);
	
	/**
	 * 返回所有参数信息
	 * @return
	 */
	public Iterator<String> getInitParameterNames();
	
	
	public Map<String,String> getParameterMap();
	
	/**
	 * 请求的url是否匹配定义的映射正则表达式
	 * @param url
	 * @return
	 */
	public boolean isMatch(String url);
	
   	
}
