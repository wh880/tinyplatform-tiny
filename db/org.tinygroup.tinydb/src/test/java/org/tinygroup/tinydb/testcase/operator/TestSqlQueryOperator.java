/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.tinydb.testcase.operator;

import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.order.OrderBean;
import org.tinygroup.tinydb.order.impl.OrderByBeanDefault;
import org.tinygroup.tinydb.query.QueryBean;
import org.tinygroup.tinydb.query.impl.QueryBeanEqual;
import org.tinygroup.tinydb.query.impl.QueryBeanLike;
import org.tinygroup.tinydb.select.SelectBean;
import org.tinygroup.tinydb.select.impl.SelectBeanDefault;
import org.tinygroup.tinydb.test.BaseTest;

public class TestSqlQueryOperator extends BaseTest{
	
	private Bean getBean(String id,String name){
		Bean bean = new Bean(ANIMAL);
		bean.setProperty("id",id);
		bean.setProperty("name",name);
		bean.setProperty("length","1234");
		return bean;
	}
	
	private Bean[] getBeans(int length){
		Bean[] insertBeans = new Bean[length*2];
		for(int i = 0 ; i < length ; i++ ){
			insertBeans[i] = getBean(i+"","name"+i);
		}
		for(int i = length ; i < length*2 ; i++ ){
			insertBeans[i] = getBean(i+"","bean"+i);
		}
		return insertBeans;
	}
	
//	BeanType[] getPagedBeans(String beanType, SelectBean[] selectBeans,
//	QueryBean queryBean, OrderBean[] orderBeans);
	public void testGetBeans(){
		
		Bean[] insertBeans = getBeans(5);
		getOperator().batchDelete(insertBeans);
		getOperator().batchInsert(insertBeans);
		SelectBean[] selectBeans=new SelectBean[1];
		selectBeans[0]=new SelectBeanDefault("id");
		QueryBean queryBean=new QueryBeanLike("name", "bean");
		
		OrderBean[] orderBeans=new OrderBean[1];
		orderBeans[0]=new OrderByBeanDefault("name", "asc");
		Bean[] beans =getOperator().getBeans(selectBeans, queryBean,orderBeans);
		assertEquals(5, beans.length);
		
		getOperator().batchDelete(insertBeans);
	}
	
//	BeanType[] getPagedBeans(String beanType, SelectBean[] selectBeans,
//	QueryBean queryBean, OrderBean[] orderBeans, int start, int limit);
	public void testPagingGetBeans(){
		
		Bean[] insertBeans = getBeans(12);
		getOperator().batchDelete(insertBeans);
		getOperator().batchInsert(insertBeans);

		SelectBean[] selectBeans=new SelectBean[1];
		selectBeans[0]=new SelectBeanDefault("id");
		QueryBean queryBean=new QueryBeanLike("name", "bean");
		
		OrderBean[] orderBeans=new OrderBean[1];
		orderBeans[0]=new OrderByBeanDefault("name", "asc");
		
		Bean[] beans = getOperator().getBeans(selectBeans, queryBean,orderBeans,0,5);
		assertEquals(5, beans.length);
		beans = getOperator().getBeans(selectBeans, queryBean,orderBeans,6,5);
		assertEquals(5, beans.length);
		beans = getOperator().getBeans(selectBeans, queryBean,orderBeans,11,5);
		assertEquals(2, beans.length);
		getOperator().batchDelete(insertBeans);
	}
	
//	<T> T getSingleValue(String beanType, SelectBean[] selectBeans,
//	QueryBean queryBean);
	public void testGetSingleObject(){
		
		Bean[] insertBeans = getBeans(5);
		getOperator().batchDelete(insertBeans);
		getOperator().batchInsert(insertBeans);
		SelectBean[] selectBeans=new SelectBean[2];
		selectBeans[0]=new SelectBeanDefault("id");
		selectBeans[1]=new SelectBeanDefault("name");
		QueryBean queryBean=new QueryBeanEqual("name", "bean5");
		Bean bean =getOperator().getSingleValue(selectBeans, queryBean);
		assertEquals("bean5", bean.get("name"));
		getOperator().batchDelete(insertBeans);
	}
//	BeanType[] getPagedBeans(String beanType, String selectClause,
//			QueryBean queryBean, OrderBean[] orderBeans);
     public void testGetBeansWithSelectClause(){
		
 		Bean[] insertBeans = getBeans(5);
 		getOperator().batchDelete(insertBeans);
 		getOperator().batchInsert(insertBeans);
 		
 		String slectClause="id";
 		QueryBean queryBean=new QueryBeanLike("name", "bean");
 		
 		OrderBean[] orderBeans=new OrderBean[1];
 		orderBeans[0]=new OrderByBeanDefault("name", "asc");
 		Bean[] beans =getOperator().getBeans(slectClause, queryBean,orderBeans);
 		assertEquals(5, beans.length);
 		
 		getOperator().batchDelete(insertBeans);
	}
//     BeanType[] getPagedBeans(String beanType, String selectClause,
// 			QueryBean queryBean, OrderBean[] orderBeans, int start, int limit);
	public void testPagingGetBeansWithSelectClause(){

		Bean[] insertBeans = getBeans(12);
		getOperator().batchDelete(insertBeans);
		getOperator().batchInsert(insertBeans);
	
		String slectClause="id";
		QueryBean queryBean=new QueryBeanLike("name", "bean");
		
		OrderBean[] orderBeans=new OrderBean[1];
		orderBeans[0]=new OrderByBeanDefault("name", "asc");
		
		Bean[] beans = getOperator().getBeans(slectClause, queryBean,orderBeans,0,5);
		assertEquals(5, beans.length);
		beans = getOperator().getBeans(slectClause, queryBean,orderBeans,6,5);
		assertEquals(5, beans.length);
		beans = getOperator().getBeans(slectClause, queryBean,orderBeans,11,5);
		assertEquals(2, beans.length);
		getOperator().batchDelete(insertBeans);
	}
	
	public void testGetSingleObjectWithSelectClause(){
		
		Bean[] insertBeans = getBeans(5);
		getOperator().batchDelete(insertBeans);
		getOperator().batchInsert(insertBeans);
		String selectClause="id,name";
		QueryBean queryBean=new QueryBeanEqual("name", "bean5");
		Bean bean =getOperator().getSingleValue(selectClause, queryBean);
		assertEquals("bean5", bean.get("name"));
		getOperator().batchDelete(insertBeans);
	}
}
