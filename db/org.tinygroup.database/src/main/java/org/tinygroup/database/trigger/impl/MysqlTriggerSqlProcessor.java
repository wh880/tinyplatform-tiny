package org.tinygroup.database.trigger.impl;

import org.tinygroup.database.config.trigger.Trigger;

public class MysqlTriggerSqlProcessor extends AbstractTriggerSqlProcessor {

	protected String getTriggerSql(Trigger trigger) {
		String sql="SELECT * FROM information_schema.TRIGGERS where trigger_name='"+trigger.getName()+"'";
		return sql;
	}

}
