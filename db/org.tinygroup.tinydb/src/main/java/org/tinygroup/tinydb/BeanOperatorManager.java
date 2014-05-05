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
package org.tinygroup.tinydb;

import org.tinygroup.tinydb.config.SchemaConfig;
import org.tinygroup.tinydb.config.SchemaConfigContainer;
import org.tinygroup.tinydb.config.TableConfiguration;
import org.tinygroup.tinydb.operator.DBOperator;
import org.tinygroup.tinydb.relation.Relation;
import org.tinygroup.tinydb.relation.Relations;

import java.util.List;
import java.util.Map;

/**
 * Bean管理器
 * 
 * @author luoguo
 * 
 */
public interface BeanOperatorManager {
	String OPERATOR_MANAGER_BEAN = "beanOperatorManager";
	String XSTEAM_PACKAGE_NAME = "tinydb";
	String NULLABLE = "NULLABLE";
	String TYPE_NAME = "TYPE_NAME";
	String COLUMN_SIZE = "COLUMN_SIZE";
	String DECIMAL_DIGITS = "DECIMAL_DIGITS";
	String COLUMN_NAME = "COLUMN_NAME";
	String PK_NAME = "COLUMN_NAME";
	String DATA_TYPE = "DATA_TYPE";
	
	String TABLE_NAME="TABLE_NAME";
	
	void setMainSchema(String schema);

	/**
	 * 获取数据操作器
	 * @param beanType
	 * @return
	 */
	DBOperator<?> getDbOperator(String schame,String beanType);
	
	/**
	 * 获取数据操作器
	 * @param beanType
	 * @return
	 */
	DBOperator<?> getDbOperator(String beanType);

	
	
	/**
	 * @param schemaConfig
	 */
	void registerSchemaConfig(SchemaConfig schemaConfig);

	/**
	 * 
	 * @param mainBean
	 * @param subBeanInfo
	 * @return
	 */
	DBOperator<?> getDbOperator(String mainBean, Map<String, String> subBeanInfo,String schame);

	/**
	 * 根据表名获取表配置信息
	 * 
	 * @param tableName
	 * @param schame
	 * @return
	 */
	TableConfiguration getTableConfiguration(String tableName,String schame);
	
	/**
	 * 根据表名获取表配置信息
	 * 
	 * @param tableName
	 * @param schame
	 * @return
	 */
	TableConfiguration getTableConfiguration(String tableName);

	/**
	 * 
	 * @param beanType
	 * @param schame
	 * @return
	 */
	TableConfiguration getTableConfigurationByBean(String beanType,String schame);
	
	/**
	 * 
	 * @param beanType
	 * @param schame
	 * @return
	 */
	TableConfiguration getTableConfigurationByBean(String beanType);


	/**
	 * 初始化表配置
	 */
	void initBeansConfiguration();

	/**
	 * 获取bean的属性字段列表
	 * 
	 * @param beanType
	 * @param schame
	 * @return
	 */
	public List<String> getBeanProperties(String beanType,String schame);
	/**
	 * 
	 * 要处理的所有schema列表
	 * @param schemas
	 */
	public void loadTablesFromSchemas();
	
	
	SchemaConfigContainer getSchemaContainer();
	
	/**
	 * 
	 * 获取所有schema下的所有表信息
	 * @return
	 */
	public Map<String, Map<String, TableConfiguration>> getTableConfigurations();
	
	/**
	 * 
	 * 添加数据库表关联关系
	 * @param relations
	 */
	void addRelationConfigs(Relations relations);
	
	/**
	 * 
	 * 移除数据库表关联关系
	 * @param relations
	 */
	void removeRelationConfigs(Relations relations);
	
	/**
	 * 
	 * 获取关联描述信息
	 * @param id 描述信息的唯一标识符
	 * @return
	 */
	Relation getRelationById(String id);
	
	/**
	 * 
	 * 获取关联描述信息
	 * @param beanType
	 * @return
	 */
	Relation getRelationByBeanType(String beanType);
	/**
	 * 是否存在参数描述的数据库表
	 * @param beanType
	 * @param schema
	 * @return
	 */
	boolean existsTable(String beanType,String schema);
	/**
	 * 添加表信息
	 * @param configuration
	 */
	void addTableConfiguration(TableConfiguration configuration);
}
