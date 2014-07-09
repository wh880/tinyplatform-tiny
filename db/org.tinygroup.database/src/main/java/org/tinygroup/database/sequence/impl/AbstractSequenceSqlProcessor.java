package org.tinygroup.database.sequence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.tinygroup.database.config.sequence.Sequence;
import org.tinygroup.database.sequence.SequenceSqlProcessor;

public abstract class AbstractSequenceSqlProcessor implements
		SequenceSqlProcessor {


	public String getDropSql(Sequence sequence) {
		return "DROP SEQUENCE "+sequence.getName();
	}
	
	public boolean checkSequenceExist(Sequence sequence, Connection connection)throws SQLException{
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			String sql=getQuerySql(sequence);
			resultSet = statement.executeQuery(sql);
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

	protected abstract String getQuerySql(Sequence sequence);

}
