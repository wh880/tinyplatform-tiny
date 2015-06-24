package org.tinygroup.weblayer.listener;

import org.tinygroup.commons.order.Ordered;
import org.tinygroup.weblayer.BasicTinyConfigAware;

import javax.servlet.http.HttpSessionActivationListener;

/**
 * 实现顺序接口的HttpSessionActivationListener
 * 
 * @author renhui
 * 
 */
public interface TinySessionActivationListener extends
		HttpSessionActivationListener, Ordered, BasicTinyConfigAware {

}
