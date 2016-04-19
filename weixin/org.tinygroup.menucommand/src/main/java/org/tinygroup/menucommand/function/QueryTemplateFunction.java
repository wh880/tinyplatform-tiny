package org.tinygroup.menucommand.function;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.function.AbstractTemplateFunction;

/**
 * 查询菜单的关键字函数
 * @author yancheng11334
 *
 */
public class QueryTemplateFunction extends AbstractTemplateFunction{

	public String getBindingTypes() {
        return "java.lang.String";
    }
	
	public QueryTemplateFunction() {
		super("query");
	}

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		String value = (String)parameters[0];
		String key = (String)parameters[1];
		if(value!=null && key!=null && value.indexOf(key)>-1){
		   return true;
		}
		return false;
	}

}
