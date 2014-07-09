package org.tinygroup.database.trigger.impl;

import org.tinygroup.database.config.trigger.Trigger;

public class OracleTriggerSqlProcessor extends AbstractTriggerSqlProcessor {

	protected String getTriggerSql(Trigger trigger) {
		String sql="select * from user_triggers where trigger_name='"+trigger.getName()+"'";
		return sql;
	}

}
