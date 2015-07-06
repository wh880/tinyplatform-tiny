package org.tinygroup.jdbctemplatedslsession.pageprocess;

import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.extend.SqlServerSelect;
import org.tinygroup.tinysqldsl.select.Fetch;
import org.tinygroup.tinysqldsl.select.Offset;

public class SqlServerPageSqlMatchProcess extends AbstractPageSqlMatchProcess {

	@Override
	protected String dbType() {
		return "SqlServer";
	}

	@Override
	protected Class selectType() {
		return SqlServerSelect.class;
	}

	@Override
	protected String internalSqlProcess(Select select, int start, int limit) {
		SqlServerSelect sqlServerSelect=(SqlServerSelect)select;
		sqlServerSelect.offset(Offset.offsetRow(start)).fetch(Fetch.fetchWithNextRow(limit));
		return sqlServerSelect.sql();
	}

}
