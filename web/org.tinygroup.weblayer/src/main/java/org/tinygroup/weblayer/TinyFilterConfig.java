package org.tinygroup.weblayer;

import org.tinygroup.weblayer.config.TinyFilterConfigInfo;


/**
 * tinyfilter的配置对象
 * @author renhui
 *
 */
public interface TinyFilterConfig  extends BasicTinyConfig{
	
	/**
	 * 设置filter的配置
	 * @param config 
	 */
	public void setFilterConfig(TinyFilterConfigInfo config);
	
   	
}
