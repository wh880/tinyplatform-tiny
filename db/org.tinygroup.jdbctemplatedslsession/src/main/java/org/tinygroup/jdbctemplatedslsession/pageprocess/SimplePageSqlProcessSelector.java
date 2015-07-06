package org.tinygroup.jdbctemplatedslsession.pageprocess;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.jdbctemplatedslsession.PageSqlMatchProcess;
import org.tinygroup.jdbctemplatedslsession.PageSqlProcessSelector;

/**
 * 简单的分页处理选择器实现
 * 
 * @author renhui
 * 
 */
public class SimplePageSqlProcessSelector implements PageSqlProcessSelector {

	private List<PageSqlMatchProcess> processes = new ArrayList<PageSqlMatchProcess>();

	public SimplePageSqlProcessSelector() {
		super();
		processes.add(new MysqlPageSqlMatchProcess());
		processes.add(new OraclePageSqlMatchProcess());
		processes.add(new SqlServerPageSqlMatchProcess());
	}

	public PageSqlMatchProcess pageSqlProcessSelect(String dbType) {
		for (PageSqlMatchProcess process : processes) {
			if (process.isMatch(dbType)) {
				return process;
			}
		}
		throw new RuntimeException(String.format(
				"根据数据库类型:%s,获取不到相应的PageSqlMatchProcess分页处理器", dbType));
	}

}
