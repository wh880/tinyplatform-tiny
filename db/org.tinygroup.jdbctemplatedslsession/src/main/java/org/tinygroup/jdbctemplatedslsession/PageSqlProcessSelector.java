package org.tinygroup.jdbctemplatedslsession;


/**
 * 分页处理器的选择器
 * @author renhui
 *
 */
public interface PageSqlProcessSelector {

	/**
	 * 根据数据库类型选择相应的分页处理器
	 * @param dbType
	 * @return
	 */
	PageSqlMatchProcess pageSqlProcessSelect(String dbType);
	
}
