package org.tinygroup.tinydb.applicationprocessor;

import java.util.List;

import org.tinygroup.application.Application;
import org.tinygroup.application.ApplicationProcessor;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.BeanOperatorManager;
import org.tinygroup.tinydb.config.SchemaConfig;
import org.tinygroup.xmlparser.node.XmlNode;


/**
 * 需要在FileResolverProcessor之后运行
 * @author renhui
 *
 */
public class BeanOperatorApplicationProcessor implements ApplicationProcessor{
	
	private XmlNode applicationConfig;

	private XmlNode componentConfig;
	
	private static final String DEFAULT_SCHEMA = "default-schema";

	private static final String KEY_TYPE = "key-type";

	private static final String TABLE_NAME_PATTERN = "table-name-pattern";

	private static final String BEAN_NAME = "bean-name";

	private static final String BEAN_MANAGER_NODE_PATH = "/application/bean-manager-config";

	private static final String SCHEMA = "schema";

	private static final String BEAN_OPERATE_CONFIG = "bean-opertate-config";

	public String getApplicationNodePath() {
		return BEAN_MANAGER_NODE_PATH;
	}

	public String getComponentConfigPath() {
		return "/beanmanager.config.xml";
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
			this.applicationConfig = applicationConfig;
			this.componentConfig = componentConfig;
			BeanOperatorManager manager=SpringUtil.getBean(BeanOperatorManager.OPERATOR_MANAGER_BEAN);
			String defaultSchema = ConfigurationUtil.getPropertyName(
					applicationConfig, componentConfig, DEFAULT_SCHEMA);
			manager.setMainSchema(defaultSchema);
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
				manager.registerSchemaConfig(new SchemaConfig(schema, beanName,
						keyType, tableNamePattern));
			}
	}

	public XmlNode getComponentConfig() {
		return componentConfig;
	}

	public XmlNode getApplicationConfig() {
		return applicationConfig;
	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}

	public void start() {
		BeanOperatorManager manager=SpringUtil.getBean(BeanOperatorManager.OPERATOR_MANAGER_BEAN);
		manager.loadTablesFromSchemas();
	}

	public void stop() {
		
	}

	public void setApplication(Application application) {
		
	}

}
