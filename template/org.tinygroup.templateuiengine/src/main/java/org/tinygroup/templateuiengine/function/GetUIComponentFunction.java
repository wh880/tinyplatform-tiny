package org.tinygroup.templateuiengine.function;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;

/**
 * 根据name属性获得UI组件
 * @author yancheng11334
 *
 */
public class GetUIComponentFunction extends UIComponentManagerFunction{
    
	public GetUIComponentFunction() {
		super("getUI,getUIComponent");
	}

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		String name = (String)parameters[0];
		return  getManager().getUIComponent(name);
	}

}
