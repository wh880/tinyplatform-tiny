package org.tinygroup.menucommand.function;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.function.AbstractTemplateFunction;

/**
 * 支持字符串的填充到指定长度
 * @author yancheng11334
 *
 */
public class FillTemplateFunction  extends AbstractTemplateFunction{

	public String getBindingTypes() {
        return "java.lang.String";
    }
	
	public FillTemplateFunction() {
		super("fill");
	}

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		String value = (String)parameters[0];
		Integer length = (Integer)parameters[1];
		String tag=" ";
		
		//长度超过指定长度
		if(value.length()>=length){
		   return value;
		}
		
		//填充逻辑
		int n = length-value.length();
		for(int i=0;i<n;i++){
			value = value+tag;
		}
		return value;
	}

}
