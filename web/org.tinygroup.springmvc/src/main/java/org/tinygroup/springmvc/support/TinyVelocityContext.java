package org.tinygroup.springmvc.support;

import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.weblayer.WebContext;

import java.util.Map;

/**
 * web上下文
 * @author renhui
 *
 */
public class TinyVelocityContext extends ContextImpl {

	private WebContext webContext;

	public TinyVelocityContext(Map map, WebContext webContext) {
		super(map);
		this.webContext = webContext;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		T value = (T) super.get(name);
		if (value != null) {
			return value;
		}
		return (T) webContext.get(name);
	}

	public WebContext getWebContext() {
		return webContext;
	}

	@Override
	public boolean exist(String name) {
		boolean exist = super.exist(name);
		if (exist) {
			return true;
		}
		return webContext.exist(name);
	}

}
