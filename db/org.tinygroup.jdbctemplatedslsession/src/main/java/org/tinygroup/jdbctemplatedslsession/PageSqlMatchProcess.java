package org.tinygroup.jdbctemplatedslsession;

import org.tinygroup.tinysqldsl.Select;

/**
 * 分页sql接口处理
 * @author renhui
 *
 */
public interface PageSqlMatchProcess {
	
	public boolean isMatch(String dbType);
	
	public String sqlProcess(Select select,int start,int limit);
	
}
