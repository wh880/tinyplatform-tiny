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
import org.tinygroup.tinysqldsl.select.Limit;
import org.tinygroup.tinysqldsl.select.PlainSelect;
import org.tinygroup.tinysqldsl.select.SetOperation;
import org.tinygroup.tinysqldsl.select.UnionOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * mysql复杂查询操作对象
 * 
 * @author renhui
 * 
 */
public class MysqlComplexSelect extends ComplexSelect<MysqlComplexSelect> {

	private MysqlComplexSelect() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public static MysqlComplexSelect union(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOperation();
			}
		}, selects);
	}
	@SuppressWarnings("rawtypes")
	public static MysqlComplexSelect unionAll(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOperation(true);
			}
		}, selects);
	}

	@SuppressWarnings("rawtypes")
	public static MysqlComplexSelect setOperation(
			SetOperationInstanceCallBack instance, Select... selects) {
		MysqlComplexSelect complexSelect = new MysqlComplexSelect();
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

	public MysqlComplexSelect limit(int start, int limit) {
		operationList.setLimit(new Limit(start, limit, true, true));
		return this;
	}

	/**
	 * 生成的sql语句 start和limit用？代替
	 * 
	 * @param limit
	 * @return
	 */
	public MysqlComplexSelect limit(Limit limit) {
		operationList.setLimit(limit);
		return this;
	}

}
