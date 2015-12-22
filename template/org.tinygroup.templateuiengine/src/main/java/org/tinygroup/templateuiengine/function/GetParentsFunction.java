package org.tinygroup.templateuiengine.function;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.uiengine.config.UIComponent;

/**
 * 获得某个UI组件的父节点列表(依赖列表)
 * @author yancheng11334
 *
 */
public class GetParentsFunction extends UIComponentManagerFunction{

	public GetParentsFunction() {
		super("getParents");
	}
	
	public String getBindingTypes() {
        return "org.tinygroup.uiengine.config.UIComponent";
    }

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		UIComponent component = (UIComponent)parameters[0];
		
		List<UIComponent> list = new ArrayList<UIComponent>();
		if(!StringUtil.isEmpty(component.getDependencies())){
		   String[] dependencies = component.getDependencies().split(",");
		   for(String dependName:dependencies){
			  UIComponent uIComponent = getManager().getUIComponent(dependName);
			  if(uIComponent!=null){
				 list.add(uIComponent);
			  }
		   }
		}
		return list;
	}

}
