package org.tinygroup.tinydbdsl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinydbdsl.base.Statement;
import org.tinygroup.tinydbdsl.operator.SetOperationInstaceCallBack;
import org.tinygroup.tinydbdsl.select.ExceptOp;
import org.tinygroup.tinydbdsl.select.Fetch;
import org.tinygroup.tinydbdsl.select.IntersectOp;
import org.tinygroup.tinydbdsl.select.Limit;
import org.tinygroup.tinydbdsl.select.MinusOp;
import org.tinygroup.tinydbdsl.select.Offset;
import org.tinygroup.tinydbdsl.select.OrderByElement;
import org.tinygroup.tinydbdsl.select.SetOperation;
import org.tinygroup.tinydbdsl.select.UnionOp;

/**
 * 复杂查询
 * 
 * @author renhui
 * 
 */
public class ComplexSelect extends StatementParser implements Statement {

	private SetOperationList operationList;

	private ComplexSelect() {
		super();
		operationList = new SetOperationList();
	}

	public static ComplexSelect union(Select... selects) {
		return setOperation(new SetOperationInstaceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOp();
			}
		}, selects);
	}

	public static ComplexSelect unionAll(Select... selects) {
		return setOperation(new SetOperationInstaceCallBack() {
			public SetOperation instanceOperation() {
				return new UnionOp(true);
			}
		}, selects);
	}

	public static ComplexSelect setOperation(
			SetOperationInstaceCallBack instance, Select... selects) {
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
		return setOperation(new SetOperationInstaceCallBack() {
			public SetOperation instanceOperation() {
				return new MinusOp();
			}
		}, selects);
	}

	public static ComplexSelect except(Select... selects) {
		return setOperation(new SetOperationInstaceCallBack() {
			public SetOperation instanceOperation() {
				return new ExceptOp();
			}
		}, selects);
	}

	public static ComplexSelect intersect(Select... selects) {
		return setOperation(new SetOperationInstaceCallBack() {
			public SetOperation instanceOperation() {
				return new IntersectOp();
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
		parser(operationList);
	}

}
