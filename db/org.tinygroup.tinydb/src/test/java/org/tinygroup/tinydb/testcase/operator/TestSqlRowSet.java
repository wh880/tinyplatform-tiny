package org.tinygroup.tinydb.testcase.operator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.exception.TinyDbException;
import org.tinygroup.tinydb.test.BaseTest;

public class TestSqlRowSet extends BaseTest{
	
	
	public void testGetSqlRowSet() throws TinyDbException{
		
		Bean[] beans = getBeans(20);
		beans=getOperator().batchInsert(beans);
		String sql="select * from animal";
		SqlRowSet rowSet= getOperator().getSqlRowSet(sql);
		if(rowSet.next()){
			assertEquals("testSql", rowSet.getString("name"));
		}
		sql="select * from animal where name=?";
		rowSet= getOperator().getSqlRowSet(sql,"testSql");
		if(rowSet.next()){
			assertEquals("testSql", rowSet.getString("name"));
		}
		List<Object> paramList=new ArrayList<Object>();
		paramList.add("testSql");
		rowSet=getOperator().getSqlRowSet(sql, paramList);
		if(rowSet.next()){
			assertEquals("testSql", rowSet.getString("name"));
		}
		sql="select * from animal where name=@name";
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("name", "testSql");
		rowSet=getOperator().getSqlRowSet(sql, paramMap);
		if(rowSet.next()){
			assertEquals("testSql", rowSet.getString("name"));
		}
		getOperator().batchDelete(beans);
	}
	

}
