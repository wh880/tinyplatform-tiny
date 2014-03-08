package org.tinygroup.dbrouterjdbc4.thread;

import java.sql.SQLException;

import org.tinygroup.dbrouterjdbc4.jdbc.RealStatementExecutor;


/**
 * 回调处理接口
 * @author renhui
 *
 */
public interface StatementProcessorCallBack<T> {

	T callBack(RealStatementExecutor statement)throws SQLException ;
	
}
