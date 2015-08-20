package org.tinygroup.template.function;

import java.util.Date;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;

/**
 * 返回当前日期
 * @author yancheng11334
 *
 */
public class TodayFunction extends AbstractTemplateFunction {

	public TodayFunction() {
		super("today");
	}

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		return new Date();
	}

}
