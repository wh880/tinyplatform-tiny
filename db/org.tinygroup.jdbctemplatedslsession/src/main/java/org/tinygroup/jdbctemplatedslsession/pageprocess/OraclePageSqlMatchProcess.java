package org.tinygroup.jdbctemplatedslsession.pageprocess;

import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.extend.OracleSelect;

public class OraclePageSqlMatchProcess extends AbstractPageSqlMatchProcess {
	
	@Override
	protected Class selectType() {
		return OracleSelect.class;
	}

	@Override
	protected String internalSqlProcess(Select select, int start, int limit) {
		OracleSelect oracleSelect=(OracleSelect)select;
		oracleSelect.page(start, limit);
		return oracleSelect.sql();
	}

	@Override
	protected String dbType() {
		return "Oracle";
	}

}
