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
package org.tinygroup.tinydb.applicationprocessor;

import org.tinygroup.application.Application;
import org.tinygroup.application.ApplicationProcessor;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.config.impl.AbstractConfiguration;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.database.config.table.Table;
import org.tinygroup.database.config.table.TableField;
import org.tinygroup.database.table.TableProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.metadata.config.stddatatype.DialectType;
import org.tinygroup.metadata.config.stdfield.StandardField;
import org.tinygroup.metadata.util.MetadataUtil;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.BeanOperatorManager;
import org.tinygroup.tinydb.config.ColumnConfiguration;
import org.tinygroup.tinydb.config.SchemaConfig;
import org.tinygroup.tinydb.config.TableConfiguration;
import org.tinygroup.xmlparser.node.XmlNode;

import java.util.List;

/**
 * Table对象信息转为为TableConfiguration
 * 配置信息
 * <table-convert-config default-schema="netshop" database="">
		<bean-opertate-config schema="netshop"
			table-name-pattern="" bean-name="beanStringOperator" key-type="uuid">
		</bean-opertate-config>
	</table-convert-config>
 * @author renhui
 *
 */
public class TableConvertBeanProcessor extends AbstractConfiguration implements ApplicationProcessor{
	
	private static final String DECIMAL_DIGITS_HOLDER = "scale";

	private static final String COLUMN_SIZE_HOLDER = "length,precision";

	private static final String DATABASE = "database";

	private static final String TABLE_CONVERT_NODE_PATH="/application/table-convert-config";
	
	private static final String DEFAULT_SCHEMA = "default-schema";

	private static final String KEY_TYPE = "key-type";

	private static final String TABLE_NAME_PATTERN = "table-name-pattern";

	private static final String BEAN_NAME = "bean-name";
	
    private static final String SCHEMA = "schema";
	
	private static final String BEAN_OPERATE_CONFIG="bean-opertate-config";
	
	private Logger logger=LoggerFactory.getLogger(TableConvertBeanProcessor.class);

	public String getApplicationNodePath() {
		return TABLE_CONVERT_NODE_PATH;
	}

	public String getComponentConfigPath() {
		return null;
	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}

	public void start() {
		BeanOperatorManager manager=SpringUtil.getBean(BeanOperatorManager.OPERATOR_MANAGER_BEAN);
		String database=ConfigurationUtil.getPropertyName(applicationConfig, componentConfig, DATABASE);
		Assert.assertNotNull(database, "xmlnode:table-convert-config,property:database must not null");
		String defaultSchema=ConfigurationUtil.getPropertyName(applicationConfig, componentConfig, DEFAULT_SCHEMA);
		manager.setMainSchema(defaultSchema);
		List<XmlNode> nodes = ConfigurationUtil.combineSubList(applicationConfig, componentConfig, BEAN_OPERATE_CONFIG, SCHEMA);
		for (XmlNode node : nodes) {
			String schema = node.getAttribute(SCHEMA);
			String beanName = node.getAttribute(BEAN_NAME);
			String tableNamePattern = node.getAttribute(TABLE_NAME_PATTERN);
			String keyType = node.getAttribute(KEY_TYPE);
			manager.registerSchemaConfig(new SchemaConfig(schema, beanName,
					keyType, tableNamePattern));
		}
		TableProcessor processor=SpringUtil.getBean(TableProcessor.BEAN_NAME);
		List<Table> tables=processor.getTables();
		if(!CollectionUtil.isEmpty(tables)){
			for (Table table : tables) {
				logger.logMessage(LogLevel.DEBUG, "开始转化表对象:{}",table.getName());
				TableConfiguration configuration=new TableConfiguration();
				configuration.setName(table.getNameWithOutSchema());
				String schema=table.getSchema();
				if(schema==null){
					schema=defaultSchema;
				}
				configuration.setSchema(schema);
				List<TableField> fields=table.getFieldList();
				if(!CollectionUtil.isEmpty(fields)){
					for (TableField tableField : fields) {
						ColumnConfiguration column=new ColumnConfiguration();
						String standardFieldId=tableField.getStandardFieldId();
						StandardField standardField=MetadataUtil.getStandardField(standardFieldId);
						if(standardField==null){
							logger.logMessage(LogLevel.ERROR, "找不到[{}]对应的标准字段信息",standardFieldId);
							return;
						}
						DialectType dialectType=MetadataUtil.getDialectType(standardFieldId, database);
						column.setColumnName(standardField.getName());
						column.setAllowNull(Boolean.toString(!tableField.getNotNull()));
						column.setColumnSize(MetadataUtil.getPlaceholderValue(standardFieldId, COLUMN_SIZE_HOLDER));
						column.setDataType(dialectType.getDataType());
						column.setPrimaryKey(tableField.getPrimary());
						column.setDecimalDigits(MetadataUtil.getPlaceholderValue(standardFieldId, DECIMAL_DIGITS_HOLDER, "0"));
						column.setTypeName(dialectType.getTypeName());
						configuration.addColumn(column);
					}
					manager.addTableConfiguration(configuration);
				}
				logger.logMessage(LogLevel.DEBUG, "转化表对象:{}结束",table.getName());
			}
		}
		
	}

	public void stop() {
		
	}

	public void setApplication(Application application) {
		
	}

}
