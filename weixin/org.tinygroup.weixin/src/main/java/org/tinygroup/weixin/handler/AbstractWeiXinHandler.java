package org.tinygroup.weixin.handler;

import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weixin.WeiXinHandler;

/**
 * 抽象的微信处理器
 * @author yancheng11334
 *
 */
public abstract class AbstractWeiXinHandler implements WeiXinHandler{

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractWeiXinHandler.class);
	
	private int priority;
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int compareTo(WeiXinHandler o) {
		if (o.getPriority() == getPriority()) {
            return 0;
        } else if (o.getPriority() < getPriority()) {
            return 1;
        } else {
            return -1;
        }
	}

}
