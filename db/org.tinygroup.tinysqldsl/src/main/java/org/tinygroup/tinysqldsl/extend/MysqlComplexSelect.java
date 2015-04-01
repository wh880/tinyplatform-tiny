package org.tinygroup.tinysqldsl.extend;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinysqldsl.ComplexSelect;
import org.tinygroup.tinysqldsl.PlainSelect;
import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.operator.SetOperationInstanceCallBack;
import org.tinygroup.tinysqldsl.select.ExceptOperation;
import org.tinygroup.tinysqldsl.select.IntersectOperation;
import org.tinygroup.tinysqldsl.select.Limit;
import org.tinygroup.tinysqldsl.select.MinusOperation;
import org.tinygroup.tinysqldsl.select.SetOperation;
import org.tinygroup.tinysqldsl.select.UnionOperation;

/**
 * mysql复杂查询操作对象
 * 
 * @author renhui
 * 
 */
public class MysqlComplexSelect extends ComplexSelect {

	private MysqlComplexSelect() {
		super();
	}

	public static MysqlComplexSelect union(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOperation();
			}
		}, selects);
	}

	public static MysqlComplexSelect unionAll(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOperation(true);
			}
		}, selects);
	}

	public static MysqlComplexSelect setOperation(
			SetOperationInstanceCallBack instance, Select... selects) {
		MysqlComplexSelect complexSelect = new MysqlComplexSelect();
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

	public static MysqlComplexSelect minus(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new MinusOperation();
			}
		}, selects);
	}

	public static MysqlComplexSelect except(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new ExceptOperation();
			}
		}, selects);
	}

	public static MysqlComplexSelect intersect(Select... selects) {
		return setOperation(new SetOperationInstanceCallBack() {
			public SetOperation instanceOperation() {
				return new IntersectOperation();
			}
		}, selects);
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
