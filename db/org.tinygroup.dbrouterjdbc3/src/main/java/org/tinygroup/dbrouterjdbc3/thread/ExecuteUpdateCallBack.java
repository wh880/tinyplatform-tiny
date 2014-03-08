package org.tinygroup.dbrouterjdbc3.thread;

import java.sql.SQLException;

import org.tinygroup.dbrouterjdbc3.jdbc.RealStatementExecutor;

public class ExecuteUpdateCallBack implements StatementProcessorCallBack<Integer> {

	public Integer callBack(RealStatementExecutor statement)
			throws SQLException {
		return statement.executeUpdate();
	}

}
