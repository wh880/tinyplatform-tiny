/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
	int[] callback(List<Map<String, Object>> params);
	
	/**
     * 批量处理回调方法	  
     * @param params
     * @return
     */
	int[] callback(Map<String, Object>[] params);
	
	
	  /**
     * 批量处理回调方法	  
     * @param params
     * @return
     */
	  int[] callbackList(List<List<Object>> params);
	
	
}
