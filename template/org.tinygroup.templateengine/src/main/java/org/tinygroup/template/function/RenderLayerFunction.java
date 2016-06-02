package org.tinygroup.template.function;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;

/**
 * 指定渲染层数
 * @author yancheng11334
 *
 */
public class RenderLayerFunction extends AbstractTemplateFunction{

	public RenderLayerFunction() {
		super("renderLayer");
	}

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		if (parameters == null || parameters.length < 1
				|| parameters[0] == null) {
			throw new TemplateException("渲染布局层数必须输入限定层数");
		} else {
			if(parameters[0] instanceof Integer){
				context.put("$renderLayer", (Integer)parameters[0]);
			}else if(parameters[0] instanceof String){
				context.put("$renderLayer", Integer.valueOf((String)parameters[0]));
			}
		}
		return null;
	}

}
