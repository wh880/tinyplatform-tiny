/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.entity;

import java.util.List;

import org.tinygroup.context.Context;

/**
 * 
 * 功能说明: 条件比较信息生成接口

 * 开发人员: renhui <br>
 * 开发时间: 2013-9-13 <br>
 * <br>
 */
public interface CompareMode {
	boolean needValue();
	/**
	 * 
	 * 根据字段名称、条件值生成比较条件信息
	 * @param fieldName
	 * @return
	 */
	public String generateCompareSymbols(String fieldName);
	/**
	 * 
	 * 获取此比较模式的名称
	 * @return
	 */
	public String getCompareKey();
	
	/**
	 * 
	 * 先从上下文中获取参数，再对参数值进行格式化，最后存入参数列表中
     * @param name 参数名称
	 * @param params 参数列表
	 * @param webContext 上下文
	 * @return
	 */
	public void assembleParamterValue(String name,Context context, List<Object> params);
}