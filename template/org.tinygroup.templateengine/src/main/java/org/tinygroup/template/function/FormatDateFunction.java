package org.tinygroup.template.function;

import java.text.SimpleDateFormat;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;

/**
 * 格式化日期的函数
 * @author yancheng11334
 *
 */
public class FormatDateFunction extends AbstractTemplateFunction {

	public static final String DEFAULT_PATTEN ="yyyy-MM-dd HH:mm:ss";
	
	public FormatDateFunction() {
		super("formatDate,formatdate");
	}

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		if (parameters == null || parameters.length < 1
				|| parameters[0] == null) {
			throw new TemplateException("formatDate函数必须输入转换的日期");
		} else {
			String patten = parameters.length>1?(String)parameters[1]:DEFAULT_PATTEN;
			SimpleDateFormat format = new SimpleDateFormat(patten);
			return format.format(parameters[0]);
		}
	}

}
