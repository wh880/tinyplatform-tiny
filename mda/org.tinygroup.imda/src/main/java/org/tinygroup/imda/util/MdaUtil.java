package org.tinygroup.imda.util;

import org.tinygroup.imda.GetDefaultValue;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.springutil.TypeConverterUtil;

/**
 * 
 * 功能说明: 
 * <p> 系统版本: v1.0<br>
 * 开发人员: renhui <br>
 * 开发时间: 2014-1-21 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public class MdaUtil {

	
	/**
	 * 
	 * defaultValueBean 存在就用该接口获取值,否则TypeConverterUtil进行类型转换
	 * @param value
	 * @param defaultValue
	 * @param className
	 * @param defaultValueBean
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getObject(Object value,String defaultValue,String className,String defaultValueBean){
		if(defaultValueBean!=null){
			GetDefaultValue getDefaultValue=SpringUtil.getBean(defaultValueBean);
			if(getDefaultValue!=null){
				return getDefaultValue.getDefaultValue(value, defaultValue);
			}
		}
		if(value==null){
			value=defaultValue;
		}
		return TypeConverterUtil.typeConverter(value, className);
	}
	
}
