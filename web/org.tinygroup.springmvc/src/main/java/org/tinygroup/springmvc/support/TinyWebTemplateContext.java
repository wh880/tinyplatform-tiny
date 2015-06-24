package org.tinygroup.springmvc.support;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.weblayer.WebContext;

import java.util.Map;

/**
 * tinywebcontext包装的模板上下文
 * 
 * @author renhui
 * 
 */
public class TinyWebTemplateContext extends TemplateContextDefault implements
		TemplateContext {

	private WebContext webContext;

	public TinyWebTemplateContext(Map dataMap,WebContext webContext) {
		super(dataMap);
		this.webContext = webContext;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		T value=(T)super.get(name);
		if(value!=null){
			return value;
		}
		return (T)webContext.get(name);
	}

	public WebContext getWebContext() {
		return webContext;
	}

	@Override
	public boolean exist(String name) {
		boolean exist=super.exist(name);
		if(exist){
			return true;
		}
		return webContext.exist(name);
	}
	
	
}
