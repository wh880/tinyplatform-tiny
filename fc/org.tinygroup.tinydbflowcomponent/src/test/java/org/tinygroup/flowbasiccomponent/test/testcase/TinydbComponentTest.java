package org.tinygroup.flowbasiccomponent.test.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.tinydb.Bean;


public class TinydbComponentTest extends BaseTest {


    public void setUp() {
        super.setUp();
        getOperator().execute("delete from animal");//删除所有记录
        //		for (int i = 0; i < 3; i++) {
        //			Bean bean = new Bean(ANIMAL);
        //			bean.setProperty("id",i+"");
        //			bean.setProperty("name","name"+i);
        //			bean.setProperty("length","length"+i);
        //			getOperator().insert(bean);
        //		}
    }


    private Bean getBean() {
        Bean bean = new Bean(ANIMAL);
        bean.setProperty("id", "aaaaaa");
        bean.setProperty("name", "name123");
        bean.setProperty("length", "123");
        return bean;
    }


    public void testAdd() {
        Context context = new ContextImpl();
        Bean bean = getBean();
        context.put(ANIMAL, bean);
        context.put("beanType", ANIMAL);
        flowExecutor.execute("addService", context);
        Bean result = context.get("result");
        assertEquals("aaaaaa", result.get("id"));
    }

	public void testUpdate() {
		Bean bean=getBean();
		getOperator().insert(bean);
		bean.setProperty("name", "updateName");
		Context context=new ContextImpl();
		context.put(ANIMAL, bean);
		context.put("beanType", ANIMAL);
		flowExecutor.execute("updateService", context);
        Integer record=context.get("result");
        assertEquals(1, record.intValue());
	}

	public void testDelete() {
		Bean bean=getBean();
		getOperator().insert(bean);
		Context context=new ContextImpl();
		context.put(ANIMAL, bean);
		context.put("beanType", ANIMAL);
		flowExecutor.execute("deleteService", context);
		Integer record=context.get("result");
        assertEquals(1, record.intValue());
	}

    public void testQuery() {
        for (int i = 0; i < 3; i++) {
            Bean bean = new Bean(ANIMAL);
            bean.setProperty("id", i + "");
            bean.setProperty("name", "name");
            bean.setProperty("length", 100 + i);
            getOperator().insert(bean);
        }
        Bean bean = new Bean(ANIMAL);
        bean.setProperty("name", "name");
        Context context = new ContextImpl();
        context.put(ANIMAL, bean);
        context.put("beanType", ANIMAL);
        flowExecutor.execute("queryService", context);
        Bean[] beans = context.get("result");
        assertEquals(3, beans.length);
    }

    public void testQueryWithId() {
        Bean bean = getBean();
        getOperator().insert(bean);
        Context context = new ContextImpl();
        context.put(ANIMAL, bean);
        context.put("beanType", ANIMAL);
        context.put("primaryKey", "aaaaaa");
        flowExecutor.execute("queryWithIdService", context);
        Bean result = context.get("result");
        assertEquals(123, result.get("length"));
    }

}
