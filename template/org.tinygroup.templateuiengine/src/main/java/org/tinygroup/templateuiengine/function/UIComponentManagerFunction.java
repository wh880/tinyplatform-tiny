package org.tinygroup.templateuiengine.function;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.template.function.AbstractTemplateFunction;
import org.tinygroup.uiengine.config.UIComponent;
import org.tinygroup.uiengine.manager.UIComponentManager;

/**
 * 基本的UIComponentManager函数
 * 
 * @author yancheng11334
 * 
 */
public abstract class UIComponentManagerFunction extends
		AbstractTemplateFunction {

	public UIComponentManagerFunction(String names) {
		super(names);
	}

	private UIComponentManager manager;

	public UIComponentManager getManager() {
		if (manager == null) {
			manager = BeanContainerFactory.getBeanContainer(
					getClass().getClassLoader()).getBean(
					UIComponentManager.UIComponentManager_BEAN);
		}
		return manager;
	}

	public void setManager(UIComponentManager manager) {
		this.manager = manager;
	}
	

	protected boolean isChild(String name,UIComponent uIComponent){
		if(!StringUtil.isEmpty(uIComponent.getDependencies())){
   		    String[] dependNames= uIComponent.getDependencies().split(",");
   		    for(String dependName:dependNames){
   		        if(name.equals(dependName)){
   		           return true;
   		        }
   		    }
  	    }
		return false;
	}

}
