package org.tinygroup.tinydb.convert;

import org.tinygroup.tinydb.BeanOperatorManager;

/**
 * 表配置信息转化接口
 * @author renhui
 *
 */
public interface TableConfigConvert {
    /**
     * 表配置信息转换接口  
     * @return
     */
	public void convert();
	
	public void setOperatorManager(BeanOperatorManager manager);
	
	public BeanOperatorManager getOperatorManager();
}
