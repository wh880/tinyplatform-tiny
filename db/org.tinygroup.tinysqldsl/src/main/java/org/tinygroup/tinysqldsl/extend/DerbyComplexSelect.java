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
 * sqlserver复杂查询操作对象
 * 
 * @author renhui
 * 
 */
public class DerbyComplexSelect extends ComplexSelect<DerbyComplexSelect> {

	private DerbyComplexSelect() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public static DerbyComplexSelect union(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOperation();
			}
		}, selects);
	}

	@SuppressWarnings("rawtypes")
	public static DerbyComplexSelect unionAll(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOperation(true);
			}
		}, selects);
	}

	@SuppressWarnings("rawtypes")
	public static DerbyComplexSelect setOperation(
			SetOperationInstanceCallBack instance, Select... selects) {
		DerbyComplexSelect complexSelect = new DerbyComplexSelect();
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
	public static DerbyComplexSelect except(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new ExceptOperation();
			}
		}, selects);
	}

	@SuppressWarnings("rawtypes")
	public static DerbyComplexSelect intersect(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new IntersectOperation();
			}
		}, selects);
	}

	public DerbyComplexSelect offset(Offset offset) {
		operationList.setOffset(offset);
		return this;
	}

	public DerbyComplexSelect fetch(Fetch fetch) {
		operationList.setFetch(fetch);
		return this;
	}

}
