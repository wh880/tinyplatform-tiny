package org.tinygroup.weblayer.listener;

import org.tinygroup.commons.order.Ordered;
import org.tinygroup.weblayer.BasicTinyConfigAware;

import javax.servlet.ServletContextListener;

/**
 * 实现顺序接口的ServletContextListener
 * @author renhui
 *
 */
public interface TinyServletContextListener extends ServletContextListener,
		Ordered,BasicTinyConfigAware {

}
