package org.tinygroup.jdbctemplatedslsession;

import java.util.List;
import java.util.Map;


/**
 * 批量操作回调执行接口
 * @author renhui
 *
 */
public interface BatchOperateCallback {

    /**
     * 批量处理回调方法	  
     * @param params
     * @return
     */
	public int[] callback(List<Map<String, Object>> params);
	
	/**
     * 批量处理回调方法	  
     * @param params
     * @return
     */
	public int[] callback(Map<String, Object>[] params);
	
	
	  /**
     * 批量处理回调方法	  
     * @param params
     * @return
     */
	public int[] callbackList(List<List<Object>> params);
	
	
}
