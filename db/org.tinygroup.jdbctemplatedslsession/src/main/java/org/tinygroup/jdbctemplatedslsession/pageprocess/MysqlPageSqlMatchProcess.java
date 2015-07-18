package org.tinygroup.jdbctemplatedslsession.pageprocess;

import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.extend.MysqlSelect;

/**
 * 
 * @author renhui
 *
 */
public class MysqlPageSqlMatchProcess extends AbstractPageSqlMatchProcess{
	
	@Override
	protected Class selectType() {
		return MysqlSelect.class;
	}

	@Override
	protected String internalSqlProcess(Select select,int start, int limit) {
		MysqlSelect mysqlSelect=(MysqlSelect)select;
		mysqlSelect.limit(start, limit);
		return mysqlSelect.parsedSql();
	}

	@Override
	protected String dbType() {
		return "MySQL";
	}

}
