package org.tinygroup.template.function;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;

public class ToFloatFunction extends AbstractTemplateFunction {

	public ToFloatFunction() {
		super("toFloat,tofloat");
	}

	
	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		if (parameters == null || parameters.length < 1
				|| parameters[0] == null) {
			throw new TemplateException("toFloat函数必须输入转换的参数");
		} else {
			if (parameters[0] instanceof Float) {
				return parameters[0];
			} else if (parameters[0] instanceof String) {
				return Float.parseFloat((String) parameters[0]);
			}
		}
		return null;
	}

}
