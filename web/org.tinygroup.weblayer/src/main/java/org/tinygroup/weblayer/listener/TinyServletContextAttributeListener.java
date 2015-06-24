package org.tinygroup.weblayer.listener;

import org.tinygroup.commons.order.Ordered;
import org.tinygroup.weblayer.BasicTinyConfigAware;

import javax.servlet.ServletContextAttributeListener;

/**
 * 实现顺序接口的ServletContextAttributeListener
 * 
 * @author renhui
 * 
 */
public interface TinyServletContextAttributeListener extends
		ServletContextAttributeListener, Ordered, BasicTinyConfigAware {

}
