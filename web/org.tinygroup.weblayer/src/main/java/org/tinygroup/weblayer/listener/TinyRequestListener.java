package org.tinygroup.weblayer.listener;

import org.tinygroup.commons.order.Ordered;
import org.tinygroup.weblayer.BasicTinyConfigAware;

import javax.servlet.ServletRequestListener;

/**
 * 实现顺序接口的ServletRequestListener
 * 
 * @author renhui
 * 
 */
public interface TinyRequestListener extends ServletRequestListener, Ordered,
		BasicTinyConfigAware {

}
