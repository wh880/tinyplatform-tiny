package org.tinygroup.weblayer.listener;

import org.tinygroup.commons.order.Ordered;
import org.tinygroup.weblayer.BasicTinyConfigAware;

import javax.servlet.http.HttpSessionBindingListener;

/**
 * 实现顺序接口的HttpSessionBindingListener
 * 
 * @author renhui
 * 
 */
public interface TinySessionBindingListener extends HttpSessionBindingListener,
		Ordered, BasicTinyConfigAware {

}
