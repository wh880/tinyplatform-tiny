package org.tinygroup.tinydb.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.Configuration;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.BeanDbNameConverter;
import org.tinygroup.tinydb.BeanOperatorManager;
import org.tinygroup.tinydb.config.SchemaConfig;
import org.tinygroup.tinydb.config.TableConfiguration;
import org.tinygroup.tinydb.config.TableConfigurationContainer;
import org.tinygroup.tinydb.convert.TableConfigConvert;
import org.tinygroup.tinydb.exception.DBRuntimeException;
import org.tinygroup.tinydb.exception.TinyDbRuntimeException;
import org.tinygroup.tinydb.operator.DBOperator;
import org.tinygroup.tinydb.relation.Relation;
import org.tinygroup.tinydb.relation.Relations;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * bean操作管理器接口的实现
 * 
 * @author renhui
 * 
 */
public class BeanOperatorManagerImpl implements BeanOperatorManager,
		Configuration {

	private String mainSchema;

	private BeanDbNameConverter beanDbNameConverter = new DefaultNameConverter();

	private TableConfigurationContainer container = new TableConfigurationContainer();

	private Map<String, Relation> relationIdMap = new HashMap<String, Relation>();

	private Map<String, Relation> relationTypeMap = new HashMap<String, Relation>();

	private XmlNode applicationConfig;

	private XmlNode componentConfig;
	
	private boolean isInit;

	private static final String DEFAULT_SCHEMA = "default-schema";

	private static final String KEY_TYPE = "key-type";

	private static final String TABLE_NAME_PATTERN = "table-name-pattern";

	private static final String BEAN_NAME = "bean-name";

	private static final String BEAN_MANAGER_NODE_PATH = "/application/bean-manager-config";

	private static final String SCHEMA = "schema";

	private static final String BEAN_OPERATE_CONFIG = "bean-opertate-config";

	public DBOperator<?> getDbOperator(String schema, String beanType) {
		String realSchema=getRealSchema(schema);
		SchemaConfig schemaConfig = container.getSchemaConfig(realSchema);
		if (schemaConfig != null) {
			DBOperator<?> operator = SpringUtil.getBean(schemaConfig
					.getOperatorBeanName());
			operator.setSchema(realSchema);
			operator.setBeanType(beanType);
			operator.setBeanDbNameConverter(beanDbNameConverter);
			operator.setManager(this);
			operator.setRelation(getRelationByBeanType(beanType));
			return operator;
		}
		throw new TinyDbRuntimeException("不存在schema:" + realSchema + "对应的bean操作对象。");
	}

	public DBOperator<?> getDbOperator(String beanType) {
		return getDbOperator(mainSchema, beanType);
	}

	public void registerSchemaConfig(SchemaConfig schemaConfig) {
		container.addSchemaConfigs(schemaConfig);
	}

	public TableConfiguration getTableConfiguration(String beanType,
			String schema) {
		String tableName = beanDbNameConverter.typeNameToDbTableName(beanType);
		return container.getTableConfiguration(schema, tableName);
	}

	public TableConfiguration getTableConfiguration(String beanType) {
		return getTableConfiguration(beanType, mainSchema);
	}
	
	public void loadTablesFromSchemas() {
		Collection<TableConfigConvert> converts= SpringUtil.getBeansOfType(TableConfigConvert.class);
		if(!CollectionUtil.isEmpty(converts)){
			for (TableConfigConvert convert : converts) {
				convert.setOperatorManager(this);
				convert.convert();
			}
		}
	}

	public TableConfigurationContainer getTableConfigurationContainer() {
		return container;
	}

	public void addRelationConfigs(Relations relations) {
		for (Relation relation : relations.getRelations()) {
			relationIdMap.put(relation.getId(), relation);
			relationTypeMap.put(relation.getType(), relation);
		}
	}

	public void removeRelationConfigs(Relations relations) {
		for (Relation relation : relations.getRelations()) {
			relationIdMap.remove(relation.getId());
			relationTypeMap.remove(relation.getType());
		}
	}

	public Relation getRelationById(String id) {
		return relationIdMap.get(id);
	}

	public Relation getRelationByBeanType(String beanType) {
		return relationTypeMap.get(beanType);
	}

	public boolean existsTableByType(String beanType, String schema) {
		String tableName = beanDbNameConverter.typeNameToDbTableName(beanType);
		String realSchema = getRealSchema(schema);
		TableConfiguration configuration = container.getTableConfiguration(
				realSchema, tableName);
		if (configuration == null) {
			throw new DBRuntimeException("tinydb.tableNotExist", beanType,
					realSchema + "." + tableName);
		}
		return true;
	}

	public void setBeanDbNameConverter(BeanDbNameConverter beanDbNameConverter) {
		this.beanDbNameConverter = beanDbNameConverter;
	}

	public BeanDbNameConverter getBeanDbNameConverter() {
		return beanDbNameConverter;
	}

	public void setMainSchema(String schema) {
		this.mainSchema = schema;
	}

	public String getRealSchema(String schema) {
		if (StringUtil.isBlank(schema)) {
			return mainSchema;
		}
		return schema;
	}

	public String getApplicationNodePath() {
		return BEAN_MANAGER_NODE_PATH;
	}

	public String getComponentConfigPath() {
		return "/beanmanager.config.xml";
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		if(!isInit){
			this.applicationConfig = applicationConfig;
			this.componentConfig = componentConfig;
			String defaultSchema = ConfigurationUtil.getPropertyName(
					applicationConfig, componentConfig, DEFAULT_SCHEMA);
			setMainSchema(defaultSchema);
			List<XmlNode> nodes = ConfigurationUtil
					.combineSubList(applicationConfig, componentConfig,
							BEAN_OPERATE_CONFIG, SCHEMA);
			for (XmlNode node : nodes) {
				String schema = node.getAttribute(SCHEMA);
				String beanName = node.getAttribute(BEAN_NAME);
				String tableNamePattern = node.getAttribute(TABLE_NAME_PATTERN);
				if(StringUtil.isBlank(tableNamePattern)){
					tableNamePattern="%";
				}
				String keyType = node.getAttribute(KEY_TYPE);
				registerSchemaConfig(new SchemaConfig(schema, beanName,
						keyType, tableNamePattern));
			}
			loadTablesFromSchemas();
			isInit=true;
		}
	}

	public XmlNode getComponentConfig() {
		return componentConfig;
	}

	public XmlNode getApplicationConfig() {
		return applicationConfig;
	}

	public String getMainSchema() {
		return mainSchema;
	}

	

}
