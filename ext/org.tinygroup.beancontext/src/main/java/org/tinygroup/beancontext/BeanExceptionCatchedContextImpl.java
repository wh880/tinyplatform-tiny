package org.tinygroup.beancontext;

import java.util.Map;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.context.BaseContext;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context.util.ContextFactory;

public class BeanExceptionCatchedContextImpl extends BeanContextImpl {
	public BeanExceptionCatchedContextImpl(Context context) {
		super(context);
	}

	public <T> T get(String name) {
		if (context.exist(name)) {
			return (T) context.get(name);
		} else {
			try {
				// 如果没有，则返回null
				T t = (T) beanContainer.getBean(name);
				context.put(name, t);
				return t;
			} catch (Exception e) {
				return null;
			}

		}
	}
}
