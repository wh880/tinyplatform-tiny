package org.tinygroup.database.trigger;

import java.sql.Connection;
import java.sql.SQLException;

import org.tinygroup.database.config.trigger.Trigger;

public interface TriggerSqlProcessor {
	
	public boolean  checkSequenceExist(Trigger trigger,Connection connection)throws SQLException;

}
