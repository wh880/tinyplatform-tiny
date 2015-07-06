package org.tinygroup.jdbctemplatedslsession.pageprocess;

import org.tinygroup.jdbctemplatedslsession.PageSqlMatchProcess;
import org.tinygroup.tinysqldsl.Select;

/**
 * 
 * @author renhui
 *
 */
public abstract class AbstractPageSqlMatchProcess implements
		PageSqlMatchProcess {
	

	public boolean isMatch(String dbType) {
		return dbType.indexOf(dbType())!=-1;
	}

	public String sqlProcess(Select select, int start, int limit) {
		Class selectType = selectType();
		if (selectType.isInstance(select)) {
			return internalSqlProcess(select, start, limit);
		}
		throw new RuntimeException(String.format(
				"select对象类型不匹配，要求是[%s]类型,实际是[%s]类型",
				selectType.getSimpleName(), select.getClass().getSimpleName()));
	}

	protected abstract String dbType();
	
	protected abstract Class selectType();
	
	protected abstract String internalSqlProcess(Select select,int start, int limit);

}
