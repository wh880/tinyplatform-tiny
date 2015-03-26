package org.tinygroup.tinysqldsl;

import java.util.List;

/**
 * dslsql执行操作接口
 * @author renhui
 *
 */
public interface DslSqlSession {

	public int execute(Insert insert);
	
	public int execute(Update update);
	
	public int execute(Delete delete);
	
	public <T> T fetchOneResult(Select select,Class<T> requiredType);

	public <T> T[] fetchArray(Select select,Class<T> requiredType) ;

	public <T> List<T> fetchList(Select select,Class<T> requiredType) ;

}
