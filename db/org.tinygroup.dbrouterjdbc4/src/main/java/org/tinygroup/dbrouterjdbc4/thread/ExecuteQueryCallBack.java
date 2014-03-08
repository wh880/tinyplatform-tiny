package org.tinygroup.dbrouterjdbc4.thread;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.tinygroup.dbrouterjdbc4.jdbc.RealStatementExecutor;
import org.tinygroup.dbrouterjdbc4.jdbc.ResultSetExecutor;

public class ExecuteQueryCallBack implements StatementProcessorCallBack<ResultSetExecutor> {

	public ResultSetExecutor callBack(RealStatementExecutor statement)
			throws SQLException {
		ResultSet resultSet = statement.executeQuery();
		return new ResultSetExecutor(resultSet, statement.getExecuteSql(),statement.getOriginalSql(), statement.getShard());
	}

}
