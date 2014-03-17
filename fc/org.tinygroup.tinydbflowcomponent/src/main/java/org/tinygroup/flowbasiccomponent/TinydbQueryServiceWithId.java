package org.tinygroup.flowbasiccomponent;

import org.tinygroup.context.Context;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.operator.DBOperator;

/**
 * 根据主键值查询
 * 
 * @author renhui
 * 
 */
public class TinydbQueryServiceWithId extends AbstractTinydbService {

	private String primaryKey;

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void tinyService(Context context, DBOperator operator) {
		Bean bean = operator.getBean(primaryKey);
		if (bean != null) {
			context.put(resultKey, bean);
		} else {
			logger.logMessage(LogLevel.WARN,
					"根据主键查询不到记录，beantype:[{0}],主键值:[{1}]", beanType, primaryKey);
		}
	}

}
