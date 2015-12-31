package org.tinygroup.dbrouter.parser.impl;

import java.util.List;
import java.util.Set;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.dbrouter.parser.GroupByColumn;
import org.tinygroup.dbrouter.parser.OrderByColumn;
import org.tinygroup.dbrouter.parser.SqlParserResult;
import org.tinygroup.dbrouter.parser.base.Condition;
import org.tinygroup.dbrouter.parser.visitor.SqlParserContext;

/**
 * 默认的sql解析结果接口实现
 * @author renhui
 *
 */
public  class DefaultSqlParserResult implements SqlParserResult {
	
	private SqlParserContext sqlParserContext;
	
	public DefaultSqlParserResult(SqlParserContext sqlParserContext) {
		this.sqlParserContext=sqlParserContext;
	}
	

	public String getTableName() {
		Set<String> tableNames= sqlParserContext.getTableNames();
		if(!CollectionUtil.isEmpty(tableNames)){
			return tableNames.iterator().next();
		}
		return null;
	}

	public List<OrderByColumn> getOrderByElements() {
		return sqlParserContext.getOrderByColumns();
	}

	public List<GroupByColumn> getGroupByElements() {
		return sqlParserContext.getGroupByColumns();
	}

	public boolean isDML() {
		return sqlParserContext.isDML();
	}

	public boolean isDDL() {
		return sqlParserContext.isDDL();
	}

	public long getSkip() {
		return sqlParserContext.getSkip();
	}


	public long getMax() {
		return sqlParserContext.getMax();
	}

	public boolean isForUpdate() {
		return sqlParserContext.isForUpdate();
	}


	public String getReplaceSql() {
		return sqlParserContext.getReplaceSql();
	}


	public List<Condition> getConditions() {
		return sqlParserContext.getConditions();
	}

}
