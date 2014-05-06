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
package org.tinygroup.tinydb.service;

import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.config.TableConfiguration;

public interface TinyDBService {
	/**
	 * 从本地获取table的结构信息
	 * @param tableName
	 * @return
	 */
	public TableConfiguration getTableConfig(String tableName,String schema);
	/**
	 * 从本地获取table的结构信息
	 * @param beanType table对应的beanType
	 * @return
	 */
	public TableConfiguration getTableConfigByBean(String beanType,String schema);
	/**
	 * 从本地获取bean的属性列表
	 * @param beanType
	 * @return
	 */
	public List<String> getBeanProperties(String beanType,String schema);
	/**
	 * 从context中获取一个bean对象,此方法调用必须保证容器中能获取Bean的定义
	 * @param context 源context
	 * @param beanType bean的beanType,
	 * @return
	 */
	public Bean context2Bean(Context context,String beanType,String schema);
	/**context
	 * 从context中获取一个bean对象
	 * @param context 源context
	 * @param beanType bean的beanType
	 * @param properties bean的属性列表
	 * @return
	 */
	public Bean context2Bean(Context context,String beanType,List<String> properties,String schema);
}
