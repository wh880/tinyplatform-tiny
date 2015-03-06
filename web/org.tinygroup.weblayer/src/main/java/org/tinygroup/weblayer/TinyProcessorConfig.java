package org.tinygroup.weblayer;

import org.tinygroup.weblayer.config.TinyProcessorConfigInfo;



/**
 * processor的配置对象
 * @author renhui
 *
 */
public interface TinyProcessorConfig  extends BasicTinyConfig{
	
	/**
	 * 设置processor的配置
	 * @param config 
	 */
	public void setProcessorConfig(TinyProcessorConfigInfo config);
	
   	
}
