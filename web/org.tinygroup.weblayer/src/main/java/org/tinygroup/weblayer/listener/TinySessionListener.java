package org.tinygroup.weblayer.listener;

import org.tinygroup.commons.order.Ordered;
import org.tinygroup.weblayer.BasicTinyConfigAware;

import javax.servlet.http.HttpSessionListener;

/**
 * 实现顺序接口的HttpSessionListener
 * 
 * @author renhui
 * 
 */
public interface TinySessionListener extends HttpSessionListener, Ordered,
		BasicTinyConfigAware {

}
