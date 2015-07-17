/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.tinysqldsl;

import java.util.List;
import java.util.Map;

/**
 * dslsql执行操作接口
 * 
 * @author renhui
 */
public interface DslSession {
	/**
	 * 执行Insert语句,返回值为受影响记录数
	 * 
	 * @param insert
	 * @return
	 */
	int execute(Insert insert);
	
	/**
	 * 执行Insert语句，返回值为自增长的主键值,由数据库来生成主键值.
	 * 
	 * @param insert 
	 * @return
	 */
	<T> T executeAndReturnObject(Insert insert);
	
	/**
	 * 执行Insert语句，返回值为自增长的主键值,由数据库来生成主键值.
	 * 
	 * @param insert 
	 * @param clazz 返回对象的类型
	 * @return
	 */
	<T> T executeAndReturnObject(Insert insert,Class<T> clazz);

	/**
	 * 执行Insert语句，返回值为自增长的主键值。
	 * 
	 * @param insert
	 * @param autoGeneratedKeys
	 *            true:由数据库来生成主键值,false:由应用层来生成主键
	 * @return
	 */
	<T> T executeAndReturnObject(Insert insert,Class<T> clazz, boolean autoGeneratedKeys);

	/**
	 * 执行更新语句
	 * 
	 * @param update
	 * @return
	 */
	int execute(Update update);

	/**
	 * 执行删除语句
	 * 
	 * @param delete
	 * @return
	 */
	int execute(Delete delete);

	/**
	 * 返回一个结果，既然是有多个结果也只返回第一个结果
	 * 
	 * @param select
	 * @param requiredType
	 * @param <T>
	 * @return
	 */
	<T> T fetchOneResult(Select select, Class<T> requiredType);

	/**
	 * 把所有的结果变成一个对象数组返回
	 * 
	 * @param select
	 * @param requiredType
	 * @param <T>
	 * @return
	 */
	<T> T[] fetchArray(Select select, Class<T> requiredType);

	/**
	 * 把所有的结果变成一个对象列表返回
	 * 
	 * @param select
	 * @param requiredType
	 * @param <T>
	 * @return
	 */
	<T> List<T> fetchList(Select select, Class<T> requiredType);

	/**
	 * 返回一个结果，既然是有多个结果也只返回第一个结果
	 * 
	 * @param select
	 * @param requiredType
	 * @param <T>
	 * @return
	 */
	<T> T fetchOneResult(ComplexSelect complexSelect, Class<T> requiredType);

	/**
	 * 把所有的结果变成一个对象数组返回
	 * 
	 * @param select
	 * @param requiredType
	 * @param <T>
	 * @return
	 */
	<T> T[] fetchArray(ComplexSelect complexSelect, Class<T> requiredType);

	/**
	 * 把所有的结果变成一个对象列表返回
	 * 
	 * @param select
	 * @param requiredType
	 * @param <T>
	 * @return
	 */
	<T> List<T> fetchList(ComplexSelect complexSelect, Class<T> requiredType);
	
	/**
	 * 分页处理
	 * @param <T>
	 * @param pageSelect
	 * @param start
	 * @param limit
	 * @param isCursor
	 * @param requiredType
	 * @return
	 */
	<T> Pager<T> fetchPage(Select pageSelect,int start,int limit,boolean isCursor,Class<T> requiredType);
	/**
	 * 	基于游标的分页方式，select对象生成的sql语句是不包含分页信息的
	 * @param <T>
	 * @param pageSelect
	 * @param start
	 * @param limit
	 * @param requiredType
	 * @return
	 */
	<T> Pager<T> fetchCursorPage(Select pageSelect,int start,int limit,Class<T> requiredType);
	/**
	 * 基于方言的分页方式，select对象生成的sql语句是包含分页信息的
	 * @param <T>
	 * @param pageSelect
	 * @param start
	 * @param limit
	 * @param requiredType
	 * @return
	 */
	<T> Pager<T> fetchDialectPage(Select pageSelect,int start,int limit,Class<T> requiredType);
	/**
	 * 查询总记录数
	 * @param select
	 * @return
	 */
	int count(Select select);
	
	public int[] batchInsert(Insert insert,List<Map<String, Object>> params);
	
	public int[] batchInsert(Insert insert,List<Map<String, Object>> params,int batchSize);
	/**
	 * 批量新增
	 * @param insert 生成批量新增的sql语句
	 * @param params 批量操作的参数
	 * @param batchSize 一次批量操作的最大数量
	 * @param autoGeneratedKeys 主键值是否由数据库自动生成
	 * @return
	 */
	public int[] batchInsert(Insert insert,List<Map<String, Object>> params,int batchSize,boolean autoGeneratedKeys);
	
	public <T> int[] batchInsert(Insert insert,Class<T> requiredType,List<T> params);
	
	public <T> int[] batchInsert(Insert insert,Class<T> requiredType,List<T> params,int batchSize);
	
	/**
	 * 批量新增
	 * @param insert 生成批量新增的sql语句
	 * @param params 批量操作的参数
	 * @param batchSize 一次批量操作的最大数量
	 * @param autoGeneratedKeys 主键值是否由数据库自动生成
	 * @return
	 */
	public <T> int[] batchInsert(Insert insert,Class<T> requiredType,List<T> params,int batchSize,boolean autoGeneratedKeys);
	
	public int[] batchUpdate(Update update,List<List<Object>> params);
	
	/**
	 * 批量更新
	 * @param update 生成update 语句
	 * @param params 参数
	 * @param batchSize 一次批量更新的最大数量
	 * @return
	 */
	public int[] batchUpdate(Update update,List<List<Object>> params,int batchSize);
	
    
	public int[] batchDelete(Delete delete,List<List<Object>> params);
	/**
	 * 批量删除
	 * @param delete 生成delete语句
	 * @param params 参数
	 * @param batchSize 一次批量删除的最大数量
	 * @return
	 */
	public int[] batchDelete(Delete delete,List<List<Object>> params,int batchSize);
	
}
