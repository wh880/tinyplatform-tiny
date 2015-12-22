package org.tinygroup.templateuiengine.function;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.uiengine.config.UIComponent;

/**
 * 获得某个UI组件的子节点列表(引用列表)
 * 
 * @author yancheng11334
 * 
 */
public class GetChildrenFunction extends UIComponentManagerFunction {

	public GetChildrenFunction() {
		super("getChildren");
	}

	public String getBindingTypes() {
		return "org.tinygroup.uiengine.config.UIComponent";
	}

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		UIComponent component = (UIComponent) parameters[0];
		List<UIComponent> list = new ArrayList<UIComponent>();
        if(getManager().getUiComponents()!=null){
           for(UIComponent uIComponent:getManager().getUiComponents()){
        	  if(isChild(component.getName(),uIComponent)){
        		 list.add(uIComponent);
        	  }
           }
        }
		return list;
	}
	
}
