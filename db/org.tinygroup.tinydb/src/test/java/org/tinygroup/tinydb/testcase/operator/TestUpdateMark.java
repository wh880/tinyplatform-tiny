package org.tinygroup.tinydb.testcase.operator;

import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.test.BaseTest;

public class TestUpdateMark extends BaseTest{
	
	
	private Bean getBean(){
		Bean bean = new Bean(ANIMAL);
		bean.setProperty("id","aaaaaa");
		bean.setProperty("name","123");
		bean.setProperty("length","123");
		return bean;
	}
	
	public void testUpdateMark(){
		Bean bean = getBean();
		getOperator().delete(bean);
		getOperator().insert(bean);
		Bean[] beans=getOperator().getBeans("select * from animal where name=?","123");
		bean=beans[0];
		bean.setProperty("name","testMark");
		assertEquals(1, getOperator().update(bean));
		bean=getOperator().getBean(bean.get("id").toString());
		assertEquals("testMark", bean.get("name"));
		assertEquals(123, bean.get("length"));
		getOperator().delete(bean);
	}
	

}
