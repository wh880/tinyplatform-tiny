package org.tinygroup.weblayer.listener;

import org.tinygroup.commons.order.Ordered;
import org.tinygroup.weblayer.BasicTinyConfigAware;

import javax.servlet.ServletRequestAttributeListener;

/**
 * 实现顺序接口的ServletRequestAttributeListener
 * 
 * @author renhui
 * 
 */
public interface TinyRequestAttributeListener extends
		ServletRequestAttributeListener, Ordered, BasicTinyConfigAware {

}
