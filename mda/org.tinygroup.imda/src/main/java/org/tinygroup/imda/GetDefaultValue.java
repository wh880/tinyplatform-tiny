package org.tinygroup.imda;


/**
 * 
 * 功能说明:获取默认值接口 
 * <p> 系统版本: v1.0<br>
 * 开发人员: renhui <br>
 * 开发时间: 2014-1-21 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public interface GetDefaultValue<T> {
  
	/**
	 * 
	 * 获取默认值
	 * @return
	 */
	 T getDefaultValue(Object value,String defaultValue);
	
}
