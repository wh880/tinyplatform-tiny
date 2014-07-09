package org.tinygroup.database.trigger.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.tinygroup.database.config.trigger.Trigger;
import org.tinygroup.database.trigger.TriggerSqlProcessor;

public  abstract class AbstractTriggerSqlProcessor implements TriggerSqlProcessor {

	public boolean checkSequenceExist(Trigger trigger, Connection connection)
			throws SQLException {
		Statement statement=null;
		ResultSet resultSet=null;
		try {
			statement=connection.createStatement();
			String sql=getTriggerSql(trigger);
			resultSet=statement.executeQuery(sql);
			if(resultSet.next()){
				return true;
			}
		} finally{
			if(statement!=null){
				statement.close();
			}
			if(resultSet!=null){
				resultSet.close();
			}
		}
		return false;
	}

	protected abstract String getTriggerSql(Trigger trigger);

}
