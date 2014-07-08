package org.tinygroup.database.trigger.impl;

import org.tinygroup.database.config.trigger.Trigger;

public class SqlServerTriggerSqlProcessor extends AbstractTriggerSqlProcessor {

	protected String getTriggerSql(Trigger trigger) {
		String sql="select name from sysobjects where name='"+trigger.getName()+"' and xtype='tr'";
		return sql;
	}

}
