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
package org.tinygroup.tinydb.operator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.tinygroup.database.dialectfunction.DialectFunctionProcessor;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.Configuration;
import org.tinygroup.tinydb.dialect.Dialect;
import org.tinygroup.tinydb.exception.TinyDbException;
import org.tinygroup.tinydb.operator.DbSqlOperator;
import org.tinygroup.tinydb.util.TinyDBUtil;

public class BeanDBSqlOperator<K> extends BeanDBBatchOperator<K> implements
		DbSqlOperator<K> {
	
	
	public BeanDBSqlOperator() {
		super();
	}

	public BeanDBSqlOperator(JdbcTemplate jdbcTemplate,Configuration configuration) {
		super(jdbcTemplate,configuration);
	}

	public Bean[] getBeans(String sql) throws TinyDbException {
			return TinyDBUtil
					.collectionToArray(queryBean(buildSqlFuction(sql)));
	}

	public Bean[] getBeans(String sql, Map<String, Object> parameters) throws TinyDbException {
		List<Bean> beans = findBeansByMap(buildSqlFuction(sql), DEFAULT_BEAN_TYPE,
				getSchema(), parameters, new ArrayList<Integer>());
		return TinyDBUtil.collectionToArray(beans);
	}


	public Bean[] getPageBeans(String sql, int start, int limit,
			Map<String, Object> parameters)throws TinyDbException {
		List<Bean> beans = findBeansByMapForPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit, parameters,
				new ArrayList<Integer>());
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getBeans(String sql, Object... parameters)throws TinyDbException {
		List<Bean> beans = findBeans(buildSqlFuction(sql), DEFAULT_BEAN_TYPE,
				getSchema(), parameters);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getBeans(String sql, List<Object> parameters) throws TinyDbException{
		List<Bean> beans = findBeansByList(buildSqlFuction(sql), DEFAULT_BEAN_TYPE,
				getSchema(), parameters);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getPageBeans(String sql, int start, int limit,
			Object... parameters)throws TinyDbException {
		List<Object> params = new ArrayList<Object>();
		if (parameters != null) {
			for (Object obj : parameters) {
				params.add(obj);
			}
		}
		List<Bean> beans = findBeansByListForPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit, params);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getCursorPageBeans(String sql, int start, int limit) throws TinyDbException{
		List<Bean> beans = findBeansForCursorPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getCursorPageBeans(String sql, int start, int limit,
			Map<String, Object> parameters) throws TinyDbException{
		List<Bean> beans = findBeansByMapForCursorPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit, parameters,
				new ArrayList<Integer>());
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getCursorPageBeans(String sql, int start, int limit,
			List<Object> parameters)throws TinyDbException {
		List<Bean> beans = findBeansByListForCursorPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit, parameters);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getCursorPageBeans(String sql, int start, int limit,
			Object... parameters)throws TinyDbException {
		List<Object> params = new ArrayList<Object>();
		if (parameters != null) {
			for (Object obj : parameters) {
				params.add(obj);
			}
		}
		List<Bean> beans = findBeansByListForCursorPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit, params);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getPageBeans(String sql, int start, int limit) throws TinyDbException {
		List<Bean> beans = findBeansForPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getDialectPageBeans(String sql, int start, int limit) throws TinyDbException{
		List<Bean> beans = findBeansForDialectPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getDialectPageBeans(String sql, int start, int limit,
			Object... parameters) throws TinyDbException{
		List<Object> params = new ArrayList<Object>();
		if (parameters != null) {
			for (Object obj : parameters) {
				params.add(obj);
			}
		}
		List<Bean> beans = findBeansByListForDialectPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit, params);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getPageBeans(String sql, int start, int limit,
			List<Object> parameters) throws TinyDbException{
		List<Bean> beans = findBeansByListForPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit, parameters);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getDialectPageBeans(String sql, int start, int limit,
			List<Object> parameters) throws TinyDbException{
		List<Bean> beans = findBeansByListForDialectPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit, parameters);
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean[] getDialectPageBeans(String sql, int start, int limit,
			Map<String, Object> parameters)throws TinyDbException {
		List<Bean> beans = findBeansByMapForDialectPage(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), start, limit, parameters,
				new ArrayList<Integer>());
		return TinyDBUtil.collectionToArray(beans);
	}

	public Bean getSingleValue(String sql)throws TinyDbException {
		Bean bean = (Bean) queryObject(buildSqlFuction(sql), DEFAULT_BEAN_TYPE,
				getSchema());
		return bean;
	}

	public Bean getSingleValue(String sql, Map<String, Object> parameters) throws TinyDbException{
		Bean bean = (Bean) queryObjectByMap(buildSqlFuction(sql),
				DEFAULT_BEAN_TYPE, getSchema(), parameters, null);
		return bean;
	}

	public Bean getSingleValue(String sql, Object... parameters) throws TinyDbException{
		Bean bean = (Bean) queryObject(buildSqlFuction(sql), DEFAULT_BEAN_TYPE,
				getSchema(), parameters);
		return bean;
	}

	public Bean getSingleValue(String sql, List<Object> parameters)throws TinyDbException {
		Bean bean = (Bean) queryObject(buildSqlFuction(sql), DEFAULT_BEAN_TYPE,
				getSchema(), parameters.toArray());
		return bean;
	}

	private List<Bean> queryBean(String sql) throws TinyDbException {
		List<Object> params = new ArrayList<Object>();
		List<Bean> beans = findBeansByList(sql, DEFAULT_BEAN_TYPE, getSchema(),
				params);
		return beans;
	}

	

	public int execute(String sql, Map<String, Object> parameters) throws TinyDbException {
		return executeByMap(buildSqlFuction(sql), parameters, null);
	}

	public int execute(String sql, Object... parameters) throws TinyDbException {
		return executeByArray(buildSqlFuction(sql), parameters);
	}

	public int execute(String sql, List<Object> parameters) throws TinyDbException {
		return executeByList(buildSqlFuction(sql), parameters, null);
	}

	private String buildSqlFuction(String sql) {
		Dialect dialect = getDialect();
		if (dialect != null) {
			return dialect.buildSqlFuction(sql);
		}
		return sql;
	}

	public int account(String sql, Object... parameters) throws TinyDbException {
		return queryForInt(buildSqlFuction(sql), parameters);
	}

	public int account(String sql, List<Object> parameters) throws TinyDbException {
		return queryForIntByList(buildSqlFuction(sql), parameters);
	}

	public int account(String sql, Map<String, Object> parameters) throws TinyDbException {
		return queryForIntByMap(buildSqlFuction(sql), parameters);
	}

}
