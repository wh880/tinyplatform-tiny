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
package org.tinygroup.tinysqldsl.extend;

import org.tinygroup.tinysqldsl.ComplexSelect;
import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.operator.SetOperationInstanceCallBack;
import org.tinygroup.tinysqldsl.select.*;

import java.util.ArrayList;
import java.util.List;

/**
 * oracle复杂查询操作对象
 * 
 * @author renhui
 * 
 */
public class OracleComplexSelect extends ComplexSelect<OracleComplexSelect> {

	private OracleComplexSelect() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public static OracleComplexSelect union(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOperation();
			}
		}, selects);
	}

	@SuppressWarnings("rawtypes")
	public static OracleComplexSelect unionAll(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOperation(true);
			}
		}, selects);
	}

	@SuppressWarnings("rawtypes")
	public static OracleComplexSelect setOperation(
			SetOperationInstanceCallBack instance, Select... selects) {
		OracleComplexSelect complexSelect = new OracleComplexSelect();
		List<PlainSelect> plainSelects = new ArrayList<PlainSelect>();
		List<SetOperation> operations = new ArrayList<SetOperation>();
		for (int i = 0; i < selects.length; i++) {
			Select select = selects[i];
			plainSelects.add(select.getPlainSelect());
			if (i != 0) {
				operations.add(instance.instanceOperation());
			}
		}
		complexSelect.operationList.setOpsAndSelects(plainSelects, operations);
		return complexSelect;
	}

	@SuppressWarnings("rawtypes")
	public static OracleComplexSelect minus(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new MinusOperation();
			}
		}, selects);
	}

	@SuppressWarnings("rawtypes")
	public static OracleComplexSelect intersect(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new IntersectOperation();
			}
		}, selects);
	}

	public OracleComplexSelect page(int start, int limit) {
		StringBuilder pagingSelect = new StringBuilder();
		if (start == 0) {
			start = 1;
		}
		pagingSelect
				.append("select * from ( select row_.*, rownum db_rownum from ( ");
		pagingSelect.append(sql());
		pagingSelect.append(" ) row_ where rownum <=" + (start + limit - 1)
				+ ") where db_rownum >=" + start);
		this.stringBuilder = pagingSelect;
		return this;
	}
}
