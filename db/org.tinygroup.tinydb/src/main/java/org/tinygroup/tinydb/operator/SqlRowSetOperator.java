package org.tinygroup.tinydb.operator;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.tinygroup.tinydb.exception.TinyDbException;

/**
 * 返回结果集的操作
 * @author renhui
 *
 */
public interface SqlRowSetOperator {

	SqlRowSet getSqlRowSet(String sql)throws TinyDbException;
	
	SqlRowSet getSqlRowSet(String sql,Object...parameters)throws TinyDbException;
	
	SqlRowSet getSqlRowSet(String sql,List<Object> parameters)throws TinyDbException;
	
	SqlRowSet getSqlRowSet(String sql,Map<String, Object> parameters)throws TinyDbException;
	
	
}
