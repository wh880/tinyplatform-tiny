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
package org.tinygroup.tinydb.query.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinydb.query.QueryBean;

/**
 * 查询Bean，用此Bean可以来查询数据，只针对单表
 * 
 * @author luoguo
 * 
 */
public abstract class AbstractQueryBean implements QueryBean {
	String propertyName = null;
	String compareMode = null;
	Object value = null;
	String connectMode = AND;
	private List<QueryBean> queryBeanList = new ArrayList<QueryBean>();

	public String getConnectMode() {
		return connectMode;
	}

	public String getQueryClause() {
		if (hasValue()) {
			return getHasValueClause();
		} else {
			return getNoneValueClause();
		}
	}

	public void setConnectMode(String connectMode) {
		this.connectMode = connectMode;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getCompareMode() {
		return compareMode;
	}

	public void setCompareMode(String compareMode) {
		this.compareMode = compareMode;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		return (T) value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public AbstractQueryBean() {

	}

	public AbstractQueryBean(String propertyName, String compareMode) {
		this(propertyName, compareMode, null);
	}

	public AbstractQueryBean(String propertyName, String compareMode,
			Object value) {
		this.propertyName = propertyName;
		this.compareMode = compareMode;
		this.value = value;
	}

	protected String getNoneValueClause() {
		checkCompareMode();
		return " " + compareMode;
	}

	private void checkCompareMode() {
		if (propertyName == null || compareMode == null) {
			throw new RuntimeException("无效的QueryBean，类型"
					+ this.getClass().getName());
		}
	}

	protected String getHasValueClause() {
		checkCompareMode();
		return " " + compareMode + " ?";
	}

	public List<QueryBean> getQueryBeanList() {
		return queryBeanList;
	}

	public void addQueryBean(QueryBean queryBean) {
		queryBeanList.add(queryBean);
	}
}
