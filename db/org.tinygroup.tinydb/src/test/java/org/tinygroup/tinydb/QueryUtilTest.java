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
package org.tinygroup.tinydb;

import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.query.QueryBean;
import org.tinygroup.tinydb.query.impl.*;
import org.tinygroup.tinydb.test.BaseTest;
import org.tinygroup.tinydb.test.operator.BeanStringOperator;

import java.util.ArrayList;
import java.util.List;

public class QueryUtilTest extends BaseTest {
	BeanStringOperator queryUtil ;

	

	
	public void setUp() {
		super.setUp();
		queryUtil=SpringUtil.getBean("beanStringOperator");
	}

	public void testGenerateSqlClause1() {
		QueryBean queryBean = new QueryBeanLike("abc", "aaa");
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc like ?", stringBuffer.toString());
		assertEquals(1, valueList.size());
		assertEquals("%aaa%", valueList.get(0));

	}

	public void testGenerateSqlClause2() {
		QueryBean queryBean = new QueryBeanLikeWildcardOnFirst("abc", "aaa");
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc like ?", stringBuffer.toString());
		assertEquals(1, valueList.size());
		assertEquals("%aaa", valueList.get(0));

	}

	public void testGenerateSqlClause3() {
		QueryBean queryBean = new QueryBeanLikeWildcardOnLast("abc", "aaa");
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc like ?", stringBuffer.toString());
		assertEquals(1, valueList.size());
		assertEquals("aaa%", valueList.get(0));
	}

	public void testGenerateSqlClause4() {
		QueryBean queryBean = new QueryBeanSmaller("abc", 3);
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc < ?", stringBuffer.toString());
		assertEquals(1, valueList.size());
		assertEquals(3, valueList.get(0));
	}

	public void testGenerateSqlClause5() {
		QueryBean queryBean = new QueryBeanSmallerOrEqual("abc", 3);
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc <= ?", stringBuffer.toString());
		assertEquals(1, valueList.size());
		assertEquals(3, valueList.get(0));
	}

	public void testGenerateSqlClause6() {
		QueryBean queryBean = new QueryBeanGreater("abc", 3);
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc > ?", stringBuffer.toString());
		assertEquals(1, valueList.size());
		assertEquals(3, valueList.get(0));
	}

	public void testGenerateSqlClause7() {
		QueryBean queryBean = new QueryBeanGreaterOrEqual("abc", 3);
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc >= ?", stringBuffer.toString());
		assertEquals(1, valueList.size());
		assertEquals(3, valueList.get(0));
	}

	public void testGenerateSqlClause8() {
		QueryBean queryBean = new QueryBeanIsNull("abc");
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc is null", stringBuffer.toString());
		assertEquals(0, valueList.size());
	}

	public void testGenerateSqlClause9() {
		QueryBean queryBean = new QueryBeanIsNotNull("abc");
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc is not null", stringBuffer.toString());
		assertEquals(0, valueList.size());
	}

	public void testGenerateSqlClause10() {
		QueryBean queryBean = new QueryBeanEqual("abc", 3);
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc = ?", stringBuffer.toString());
		assertEquals(1, valueList.size());
		assertEquals(3, valueList.get(0));
	}

	public void testGenerateSqlClause11() {
		QueryBean queryBean = new QueryBeanNotEqual("abc", 3);
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc <> ?", stringBuffer.toString());
		assertEquals(1, valueList.size());
		assertEquals(3, valueList.get(0));
	}

	public void testGenerateSqlClause12() {
		QueryBean queryBean = new QueryBeanEmpty();
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("", stringBuffer.toString());
		assertEquals(0, valueList.size());
	}

	public void testGenerateSqlClause13() {
		QueryBean queryBean = new QueryBeanEmpty();
		for (int i = 0; i < 2; i++) {
			queryBean.addQueryBean(new QueryBeanEqual("aaa" + i, i));
		}
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("(aaa0 = ? and aaa1 = ?)", stringBuffer.toString());
		assertEquals(2, valueList.size());
		assertEquals(0, valueList.get(0));
		assertEquals(1, valueList.get(1));
	}

	public void testGenerateSqlClause14() {
		QueryBean queryBean = new QueryBeanEqual("aaa", 3);
		for (int i = 0; i < 2; i++) {
			queryBean.addQueryBean(new QueryBeanEqual("aaa" + i, i));
		}
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("aaa = ? and (aaa0 = ? and aaa1 = ?)",
				stringBuffer.toString());
		assertEquals(3, valueList.size());
		assertEquals(3, valueList.get(0));
		assertEquals(0, valueList.get(1));
		assertEquals(1, valueList.get(2));
	}

	public void testGenerateSqlClause15() {
		QueryBean queryBean = new QueryBeanEqual("aaa", 3);
		queryBean.setConnectMode(QueryBean.OR);
		for (int i = 0; i < 2; i++) {
			queryBean.addQueryBean(new QueryBeanEqual("aaa" + i, i));
		}
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("aaa = ? or (aaa0 = ? or aaa1 = ?)",
				stringBuffer.toString());
		assertEquals(3, valueList.size());
		assertEquals(3, valueList.get(0));
		assertEquals(0, valueList.get(1));
		assertEquals(1, valueList.get(2));
	}

	public void testGenerateSqlClause16() {
		QueryBean queryBean = new QueryBeanEqual("aaa", 3);
		queryBean.setConnectMode(QueryBean.OR);
		QueryBean emptyBean = new QueryBeanEmpty();
		queryBean.addQueryBean(emptyBean);
		for (int i = 0; i < 2; i++) {
			emptyBean.addQueryBean(new QueryBeanEqual("aaa" + i, i));
		}
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("aaa = ? or (aaa0 = ? and aaa1 = ?)",
				stringBuffer.toString());
		assertEquals(3, valueList.size());
		assertEquals(3, valueList.get(0));
		assertEquals(0, valueList.get(1));
		assertEquals(1, valueList.get(2));
	}

	public void testGenerateSqlClause17() {
		QueryBean queryBean = new QueryBeanEqual("aaa", 3);
		QueryBean emptyBean = new QueryBeanEmpty();
		emptyBean.setConnectMode(QueryBean.OR);
		queryBean.addQueryBean(emptyBean);
		for (int i = 0; i < 2; i++) {
			emptyBean.addQueryBean(new QueryBeanEqual("aaa" + i, i));
		}
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("aaa = ? and (aaa0 = ? or aaa1 = ?)",
				stringBuffer.toString());
		assertEquals(3, valueList.size());
		assertEquals(3, valueList.get(0));
		assertEquals(0, valueList.get(1));
		assertEquals(1, valueList.get(2));
	}

	public void testGenerateSqlClause18() {
		int[] intArray = { 3, 4, 5 };
		QueryBean queryBean = new QueryBeanIn("abc", intArray);
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		queryUtil.generateQuerySqlClause(queryBean, stringBuffer, valueList);
		assertEquals("abc in (?)", stringBuffer.toString());
		assertEquals(1, valueList.size());
	}
}
