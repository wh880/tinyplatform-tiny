package org.tinygroup.tinydbdsl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinydbdsl.base.Statement;
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

	public ComplexSelect getInstance() {
		ComplexSelect complexSelect = new ComplexSelect();
		return complexSelect;
	}

	public ComplexSelect union(Select... selects) {
		List<PlainSelect> plainSelects = new ArrayList<PlainSelect>();
		List<SetOperation> operations = new ArrayList<SetOperation>();
		for (Select select : selects) {
			plainSelects.add(select.getPlainSelect());
			operations.add(new UnionOp());
		}
		operationList.setOpsAndSelects(plainSelects, operations);
		return this;
	}

	public ComplexSelect unionAll(Select... selects) {
		List<PlainSelect> plainSelects = new ArrayList<PlainSelect>();
		List<SetOperation> operations = new ArrayList<SetOperation>();
		for (Select select : selects) {
			plainSelects.add(select.getPlainSelect());
			operations.add(new UnionOp(true));
		}
		operationList.setOpsAndSelects(plainSelects, operations);
		return this;
	}

	public ComplexSelect minus(Select... selects) {
		List<PlainSelect> plainSelects = new ArrayList<PlainSelect>();
		List<SetOperation> operations = new ArrayList<SetOperation>();
		for (Select select : selects) {
			plainSelects.add(select.getPlainSelect());
			operations.add(new MinusOp());
		}
		operationList.setOpsAndSelects(plainSelects, operations);
		return this;
	}

	public ComplexSelect except(Select... selects) {
		List<PlainSelect> plainSelects = new ArrayList<PlainSelect>();
		List<SetOperation> operations = new ArrayList<SetOperation>();
		for (Select select : selects) {
			plainSelects.add(select.getPlainSelect());
			operations.add(new ExceptOp());
		}
		operationList.setOpsAndSelects(plainSelects, operations);
		return this;
	}

	public ComplexSelect intersect(Select... selects) {
		List<PlainSelect> plainSelects = new ArrayList<PlainSelect>();
		List<SetOperation> operations = new ArrayList<SetOperation>();
		for (Select select : selects) {
			plainSelects.add(select.getPlainSelect());
			operations.add(new IntersectOp());
		}
		operationList.setOpsAndSelects(plainSelects, operations);
		return this;
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
