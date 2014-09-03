package org.tinygroup.tinydb.testcase.operator;

import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.exception.TinyDbException;
import org.tinygroup.tinydb.operator.DBOperator;
import org.tinygroup.tinydb.test.BaseTest;

public class TestDbMonitor extends BaseTest{

	public void testMonitor() throws TinyDbException{
		DBOperator operator=factory.getDBOperator();
		operator.execute("delete from animal");//刪除
		Thread thread1=new Thread(new Runnable() {
			public void run() {
				beanOperator();
			}
		});
		Thread thread2=new Thread(new Runnable() {
			public void run() {
				beanOperator();
			}
		});
		Thread thread3=new Thread(new Runnable() {
			public void run() {
				beanOperator();
			}
		});
		thread1.start();
		thread2.start();
		thread3.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		assertEquals(3, operator.queryAllActiveConnection().size());
		operator.execute("delete from animal");//刪除
	}
	
	/**
	 * @param operator
	 * @param bean
	 */
	private void beanOperator() {
		final Bean bean=getAnimalBean();
		try {
			DBOperator operator=factory.getDBOperator();
			operator.beginTransaction();
			Bean newBean=operator.insert(bean);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			operator.getBean(newBean.get("id"), ANIMAL);
			operator.delete(newBean);
			operator.commitTransaction();//此时才关闭连接
		} catch (TinyDbException e) {
			fail(e.getMessage());
		}
	}
	
	private Bean getAnimalBean() {
		Bean bean = new Bean(ANIMAL);
		bean.setProperty("id", "beanId");
		bean.setProperty("name", "1234");
		bean.setProperty("length", "1234");
		return bean;
	}
	
}
