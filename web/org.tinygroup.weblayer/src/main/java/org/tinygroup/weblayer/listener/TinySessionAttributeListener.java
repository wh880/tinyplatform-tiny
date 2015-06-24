package org.tinygroup.weblayer.listener;

import org.tinygroup.commons.order.Ordered;
import org.tinygroup.weblayer.BasicTinyConfigAware;

import javax.servlet.http.HttpSessionAttributeListener;

/**
 * 实现顺序接口的HttpSessionAttributeListener
 * 
 * @author renhui
 * 
 */
public interface TinySessionAttributeListener extends
		HttpSessionAttributeListener, Ordered, BasicTinyConfigAware {

}
