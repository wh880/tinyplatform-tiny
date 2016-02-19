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
package org.tinygroup.jdbctemplatedslsession.batch;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.tinygroup.commons.namestrategy.NameStrategy;
import org.tinygroup.commons.namestrategy.impl.CamelCaseStrategy;
import org.tinygroup.jdbctemplatedslsession.SimpleDslSession;
import org.tinygroup.jdbctemplatedslsession.TableMetaData;
import org.tinygroup.jdbctemplatedslsession.editor.AllowNullNumberEditor;
import org.tinygroup.jdbctemplatedslsession.exception.DslRuntimeException;
import org.tinygroup.jdbctemplatedslsession.keygenerator.AppKeyGenerator;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinysqldsl.Insert;
import org.tinygroup.tinysqldsl.KeyGenerator;
import org.tinygroup.tinysqldsl.base.InsertContext;

/**
 * 插入批量操作
 * 
 * @author renhui
 *
 */
public class InsertBatchOperate {

	private InsertContext insertContext;

	private SimpleJdbcInsert simpleJdbcInsert;

	private List<String> generateKeys = new ArrayList<String>();

	private SimpleDslSession simpleDslSession;
	private NameStrategy nameStrategy=new CamelCaseStrategy();
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(InsertBatchOperate.class);

	public InsertBatchOperate(boolean autoGeneratedKeys, Insert insert,
			TableMetaData metaData, SimpleJdbcInsert simpleJdbcInsert,
			SimpleDslSession simpleDslSession) {
		super();
		this.insertContext = insert.getContext();
		this.simpleJdbcInsert = simpleJdbcInsert;
		this.simpleDslSession = simpleDslSession;
		InsertContext insertContext = insert.getContext();
		simpleJdbcInsert.withSchemaName(insertContext.getSchema())
				.withTableName(insertContext.getTableName());
		if (autoGeneratedKeys) {
			simpleJdbcInsert.usingColumns(insertContext.getColumnNameArray())
					.usingGeneratedKeyColumns(metaData.getKeyNames());
		} else {
			String[] keyNames = metaData.getKeyNames();
			Set<String> columns = new HashSet<String>();
			List<String> allColumns = insertContext.getColumnNames();
			for (String columnName : allColumns) {
				columns.add(columnName.toUpperCase());
			}
			for (String key : keyNames) {
				if (!columns.contains(key.toUpperCase())) {
					generateKeys.add(key);
				}
			}
			allColumns.addAll(generateKeys);// 主键值通过应用程序自动生成
			simpleJdbcInsert.usingColumns(allColumns.toArray(new String[0]));
		}

	}

	public int[] batchProcess(List<Map<String, Object>> batchArgs) {
		Map<String, Object>[] batch = new Map[batchArgs.size()];
		for (int i = 0; i < batch.length; i++) {
			Map<String, Object> params = batchArgs.get(i);
			for (String keyName : generateKeys) {
				params.put(keyName, getKeyValue(keyName));
			}
			batch[i] = params;
		}
		return simpleJdbcInsert.executeBatch(batch);

	}

	public <T> int[] batchProcess(List<T> batchArgs, Class<T> requiredType) {
		SqlParameterSource[] batch = new SqlParameterSource[batchArgs.size()];
		for (int i = 0; i < batch.length; i++) {
			T object = batchArgs.get(i);
			BeanWrapper bw = PropertyAccessorFactory
					.forBeanPropertyAccess(object);
			initBeanWrapper(bw);
			for (String keyName : generateKeys) {
				Object value = getKeyValue(keyName);
				String propertyName=getPropertyName(keyName,requiredType);
				bw.setPropertyValue(propertyName, value);
			}
			batch[i]=new BeanPropertySqlParameterSource(bw.getWrappedInstance());
		}
		return simpleJdbcInsert.executeBatch(batch);
	}
	
	private <T> String getPropertyName(String keyName,Class<T> requiredType){
		PropertyDescriptor descriptor=BeanUtils.getPropertyDescriptor(requiredType, keyName);
	    if(descriptor==null){
              keyName=nameStrategy.getPropertyName(keyName); 	
              descriptor=BeanUtils.getPropertyDescriptor(requiredType, keyName);
	    }
	    if(descriptor==null){
	    	LOGGER.logMessage(LogLevel.WARN, "在class:[{0}],不存在属性:[{1}]",requiredType,keyName);
	    	throw new DslRuntimeException("在pojo对象中获取主键字段对应的属性出错");
	    }
	    return descriptor.getName();
	}

	private void initBeanWrapper(BeanWrapper bw) {
		bw.registerCustomEditor(byte.class, new AllowNullNumberEditor(
				Byte.class, true));
		bw.registerCustomEditor(short.class, new AllowNullNumberEditor(
				Short.class, true));
		bw.registerCustomEditor(int.class, new AllowNullNumberEditor(
				Integer.class, true));
		bw.registerCustomEditor(long.class, new AllowNullNumberEditor(
				Long.class, true));
		bw.registerCustomEditor(float.class, new AllowNullNumberEditor(
				Float.class, true));
		bw.registerCustomEditor(double.class, new AllowNullNumberEditor(
				Double.class, true));
	}

	private Object getKeyValue(String keyName) {
		KeyGenerator keyGenerator = insertContext.getTable().getGeneratorMap()
				.get(keyName);
		if (keyGenerator == null) {//没有重写table对象的getGeneratorMap方法，那么从配置中加载
			keyGenerator = simpleDslSession.getConfiguration().getKeyGenerator(
					insertContext.getTableName(), keyName);
		}
		if (keyGenerator == null) {
			keyGenerator = new AppKeyGenerator(simpleDslSession.getIncrementer());
		}
		return keyGenerator.generate(insertContext);
	}

}
