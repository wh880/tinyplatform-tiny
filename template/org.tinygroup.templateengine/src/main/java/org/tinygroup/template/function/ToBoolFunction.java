package org.tinygroup.template.function;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;

public class ToBoolFunction extends AbstractTemplateFunction {

	public ToBoolFunction() {
		super("toBool,tobool");
	}

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		if (parameters == null || parameters.length < 1
				|| parameters[0] == null) {
			throw new TemplateException("toBool函数必须输入转换的参数");
		} else {
			if (parameters[0] instanceof Boolean) {
				return parameters[0];
			} else if (parameters[0] instanceof String) {
				return Boolean.parseBoolean((String)parameters[0]);
			}
		}
		return null;
	}

}
