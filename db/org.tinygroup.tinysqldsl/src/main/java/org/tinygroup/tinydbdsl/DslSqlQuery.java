package org.tinygroup.tinydbdsl;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;


/**
 * 
 * @author renhui
 *
 * @param <T>
 */
public interface DslSqlQuery{
	
	public <T> T fetchOneResult(Class<T> requiredType);

	public <T> T[] fetchArray(Class<T> requiredType) ;

	public <T> List<T> fetchList(Class<T> requiredType) ;

	public SqlRowSet fetchResult();
	

}
