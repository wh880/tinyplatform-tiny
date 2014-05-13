package org.tinygroup.tinydb.convert.impl;

import org.tinygroup.commons.tools.Assert;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinydb.BeanOperatorManager;
import org.tinygroup.tinydb.config.TableConfiguration;
import org.tinygroup.tinydb.config.TableConfigurationContainer;
import org.tinygroup.tinydb.convert.TableConfigConvert;

/**
 * 表信息转换的抽象实现
 * @author renhui
 *
 */
public abstract class AbstractTableConfigConvert implements TableConfigConvert {
	
	private BeanOperatorManager manager;
	
	protected static Logger logger = LoggerFactory.getLogger(AbstractTableConfigConvert.class);


	public void setOperatorManager(BeanOperatorManager manager) {
		this.manager=manager;
	}

	public BeanOperatorManager getOperatorManager() {
		return manager;
	}

	public void convert() {
		Assert.assertNotNull(manager, "bean操作管理对象不能为空");
		realConvert(manager);
	}

	protected abstract void realConvert(BeanOperatorManager manager);
	
	
	protected void addTableConfiguration(TableConfiguration table) {
		TableConfigurationContainer container=getOperatorManager().getTableConfigurationContainer();
		container.addTableConfiguration(table);
	}
	
	protected boolean existsTable(String tableName, String schema){
		BeanOperatorManager manager=getOperatorManager();
		TableConfigurationContainer container=manager.getTableConfigurationContainer();
		return container.isExistTable(schema, tableName);
	}
	
	protected String getSchema(String schema){
		if(StringUtil.isBlank(schema)){
			return getOperatorManager().getMainSchema();
		}
		return schema;
	}
	
}
