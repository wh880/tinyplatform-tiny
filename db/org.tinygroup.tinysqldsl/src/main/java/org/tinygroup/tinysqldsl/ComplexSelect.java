/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
 */
package org.tinygroup.tinysqldsl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinysqldsl.base.Statement;
import org.tinygroup.tinysqldsl.operator.SetOperationInstanceCallBack;
import org.tinygroup.tinysqldsl.select.ExceptOperation;
import org.tinygroup.tinysqldsl.select.Fetch;
import org.tinygroup.tinysqldsl.select.IntersectOperation;
import org.tinygroup.tinysqldsl.select.Limit;
import org.tinygroup.tinysqldsl.select.MinusOperation;
import org.tinygroup.tinysqldsl.select.Offset;
import org.tinygroup.tinysqldsl.select.OrderByElement;
import org.tinygroup.tinysqldsl.select.SetOperation;
import org.tinygroup.tinysqldsl.select.UnionOperation;

/**
 * 复杂查询
 * 
 * @author renhui
 * 
 */
public class ComplexSelect extends StatementSqlBuilder implements Statement {

	private SetOperationList operationList;

	private ComplexSelect() {
		super();
		operationList = new SetOperationList();
	}

	public static ComplexSelect union(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOperation();
			}
		}, selects);
	}

	public static ComplexSelect unionAll(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOperation(true);
			}
		}, selects);
	}

	public static ComplexSelect setOperation(
			SetOperationInstanceCallBack instance, Select... selects) {
		ComplexSelect complexSelect = new ComplexSelect();
		List<PlainSelect> plainSelects = new ArrayList<PlainSelect>();
		List<SetOperation> operations = new ArrayList<SetOperation>();
		for (int i = 0; i < selects.length; i++) {
			Select select = selects[0];
			plainSelects.add(select.getPlainSelect());
			if (i != 0) {
				operations.add(instance.instanceOperation());
			}
		}
		complexSelect.operationList.setOpsAndSelects(plainSelects, operations);
		return complexSelect;
	}

	public static ComplexSelect minus(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new MinusOperation();
			}
		}, selects);
	}

	public static ComplexSelect except(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new ExceptOperation();
			}
		}, selects);
	}

	public static ComplexSelect intersect(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new IntersectOperation();
			}
		}, selects);
	}

	public ComplexSelect orderBy(OrderByElement... orderByElements) {
		operationList.addOrderByElements(orderByElements);
		return this;
	}

	public ComplexSelect limit(int start, int limit) {
		operationList.setLimit(new Limit(start, limit, true, true));
		return this;
	}

	/**
	 * 生成的sql语句 start和limit用？代替
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	public ComplexSelect limit(Limit limit) {
		operationList.setLimit(limit);
		return this;
	}

	public ComplexSelect offset(Offset offset) {
		operationList.setOffset(offset);
		return this;
	}

	public ComplexSelect fetch(Fetch fetch) {
		operationList.setFetch(fetch);
		return this;
	}

	public SetOperationList getOperationList() {
		return operationList;
	}

	@Override
	public String toString() {
		return sql();
	}

	@Override
	protected void parserStatementBody() {
		build(operationList);
	}

}
