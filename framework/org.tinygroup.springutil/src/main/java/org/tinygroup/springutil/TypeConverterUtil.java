package org.tinygroup.springutil;

import java.util.Collection;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.SimpleTypeConverter;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * 
 * 功能说明:类型转换的工具类 

 * 开发人员: renhui <br>
 * 开发时间: 2014-1-17 <br>
 * <br>
 */
public class TypeConverterUtil {

	private static SimpleTypeConverter converter=new SimpleTypeConverter();
	private static Logger logger=LoggerFactory.getLogger(TypeConverterUtil.class);
	
	static{
		Collection<PropertyEditorRegistrar> editorRegistrars=SpringUtil.getBeansOfType(PropertyEditorRegistrar.class);
		for (PropertyEditorRegistrar registrar : editorRegistrars) {
              registrar.registerCustomEditors(converter);
		}
	}
	
	public static Object typeConverter(Object value,String className){
		
		try {
			Class clazz = Class.forName(className);
			return converter.convertIfNecessary(value, clazz);	
		} catch (ClassNotFoundException e) {
		     logger.errorMessage("get Class error with className:"+className, e);
		     throw new RuntimeException(e);
		}
	}
	
	
	public static Object typeConverter(Object value,Class type){
	     return converter.convertIfNecessary(value, type);	
	}
	
	public static void registerCustomEditors(PropertyEditorRegistrar registrar){
		if(registrar!=null){
			registrar.registerCustomEditors(converter);
		}
	}

	
}
