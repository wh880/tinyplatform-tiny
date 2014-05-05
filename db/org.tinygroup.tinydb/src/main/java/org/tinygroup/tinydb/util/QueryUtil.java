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
package org.tinygroup.tinydb.util;

import org.tinygroup.tinydb.BeanDbNameConverter;
import org.tinygroup.tinydb.order.OrderBean;
import org.tinygroup.tinydb.query.QueryBean;
import org.tinygroup.tinydb.select.SelectBean;

import java.util.List;

/**
 * 用于生成SQL语句
 * 
 * @author luoguo
 * 
 */
public class QueryUtil {
	BeanDbNameConverter beanDbNameConverter;

	public BeanDbNameConverter getBeanDbNameConverter() {
		return beanDbNameConverter;
	}

	public void setBeanDbNameConverter(BeanDbNameConverter beanDbNameConverter) {
		this.beanDbNameConverter = beanDbNameConverter;
	}

	/**
	 * 产生SQL语句
	 * 
	 * @param queryBean
	 * @param stringBuffer
	 *            用于存放SQL
	 * @param valueList
	 *            用于存放值列表
	 */
	public void generateQuerySqlClause(QueryBean queryBean,
			StringBuffer stringBuffer, List<Object> valueList) {
		if (queryBean.getPropertyName() != null) {
			appendQueryBeanSql(queryBean, stringBuffer);
			if (queryBean.hasValue()) {
				valueList.add(queryBean.getValue());
			}
		}
		if (queryBean.getQueryBeanList() != null
				&& queryBean.getQueryBeanList().size() > 0) {
			if (queryBean.getPropertyName() != null) {
				stringBuffer.append(" ").append(queryBean.getConnectMode())
						.append(" ");
			}
			if (queryBean.getQueryBeanList().size() > 1) {
				stringBuffer.append("(");
			}
			for (int i = 0; i < queryBean.getQueryBeanList().size(); i++) {
				QueryBean subBean = queryBean.getQueryBeanList().get(i);
				if (i > 0) {
					stringBuffer.append(" ").append(queryBean.getConnectMode())
							.append(" ");
				}
				generateQuerySqlClause(subBean, stringBuffer, valueList);
			}
			if (queryBean.getQueryBeanList().size() > 1) {
				stringBuffer.append(")");
			}
		}
	}

	private StringBuffer appendQueryBeanSql(QueryBean queryBean, StringBuffer sb) {
		return sb.append(
				beanDbNameConverter.propertyNameToDbFieldName(queryBean
						.getPropertyName())).append(queryBean.getQueryClause());
	}

	/**
	 * 
	 * 生成查询部分的sql片段
	 * 
	 * @param selectBeans
	 * @param stringBuffer
	 */
	public void generateSelectSqlClause(SelectBean[] selectBeans,
			StringBuffer stringBuffer) {
		stringBuffer.append(" select ");
		if (selectBeans != null && selectBeans.length > 0) {
			for (int i = 0; i < selectBeans.length; i++) {
				SelectBean selectBean = selectBeans[i];
				stringBuffer.append(selectBean.getSelectClause());
				if (i < selectBeans.length - 1) {
					stringBuffer.append(" , ");
				}
			}
		} else {
			stringBuffer.append(" * ");
		}
	}
    /**
     * 
     * 生成order by 子句
     * @param orderBeans
     * @param stringBuffer
     */
	public void generateOrderSqlClause(OrderBean[] orderBeans,
			StringBuffer stringBuffer) {
		if (orderBeans != null && orderBeans.length > 0) {
			stringBuffer.append(" order by ");
			for (int i = 0; i < orderBeans.length; i++) {
				OrderBean orderBean = orderBeans[i];
				stringBuffer
						.append(beanDbNameConverter
								.propertyNameToDbFieldName(orderBean
										.getPropertyName())).append(" ")
						.append(orderBean.getOrderMode()).append(" ");
				if(i<orderBeans.length-1){
					stringBuffer.append(" , ");
				}
			}
		}
	}
	/**
	 * 
	 * 生成sql语句
	 * @param beanType
	 * @param selectBeans
	 * @param queryBean
	 * @param orderBeans
	 * @param stringBuffer
	 * @param valueList
	 */
	public String generateSqlClause(String beanType,SelectBean[] selectBeans,QueryBean queryBean,OrderBean[] orderBeans,List<Object> valueList){
		StringBuffer stringBuffer=new StringBuffer();
		generateSelectSqlClause(selectBeans, stringBuffer);
		stringBuffer.append(" from ").append(beanDbNameConverter.typeNameToDbTableName(beanType)).append(" ");
		generateQuerySqlClause(queryBean, stringBuffer, valueList);
		generateOrderSqlClause(orderBeans, stringBuffer);
		return stringBuffer.toString();
	}
	
	public String generateSqlClause(String beanType,String selectClause,QueryBean queryBean,OrderBean[] orderBeans,List<Object> valueList){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(" ").append(selectClause).append(" ");
		stringBuffer.append(" from ").append(beanDbNameConverter.typeNameToDbTableName(beanType)).append(" ");
		generateQuerySqlClause(queryBean, stringBuffer, valueList);
		generateOrderSqlClause(orderBeans, stringBuffer);
		return stringBuffer.toString();
	}
}
