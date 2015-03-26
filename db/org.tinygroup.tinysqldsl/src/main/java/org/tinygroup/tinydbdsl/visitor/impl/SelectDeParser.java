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
package org.tinygroup.tinydbdsl.visitor.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.tinydbdsl.PlainSelect;
import org.tinygroup.tinydbdsl.SetOperationList;
import org.tinygroup.tinydbdsl.base.Alias;
import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.expression.Function;
import org.tinygroup.tinydbdsl.formitem.FromItem;
import org.tinygroup.tinydbdsl.formitem.FromItemList;
import org.tinygroup.tinydbdsl.formitem.LateralSubSelect;
import org.tinygroup.tinydbdsl.formitem.SubJoin;
import org.tinygroup.tinydbdsl.formitem.SubSelect;
import org.tinygroup.tinydbdsl.formitem.ValuesList;
import org.tinygroup.tinydbdsl.select.Fetch;
import org.tinygroup.tinydbdsl.select.Join;
import org.tinygroup.tinydbdsl.select.Limit;
import org.tinygroup.tinydbdsl.select.Offset;
import org.tinygroup.tinydbdsl.select.OrderByElement;
import org.tinygroup.tinydbdsl.selectitem.AllColumns;
import org.tinygroup.tinydbdsl.selectitem.AllTableColumns;
import org.tinygroup.tinydbdsl.selectitem.CustomSelectItem;
import org.tinygroup.tinydbdsl.selectitem.Distinct;
import org.tinygroup.tinydbdsl.selectitem.SelectExpressionItem;
import org.tinygroup.tinydbdsl.selectitem.SelectItem;
import org.tinygroup.tinydbdsl.selectitem.Top;
import org.tinygroup.tinydbdsl.util.DslUtil;
import org.tinygroup.tinydbdsl.visitor.ExpressionVisitor;
import org.tinygroup.tinydbdsl.visitor.FromItemVisitor;
import org.tinygroup.tinydbdsl.visitor.OrderByVisitor;
import org.tinygroup.tinydbdsl.visitor.SelectItemVisitor;
import org.tinygroup.tinydbdsl.visitor.SelectVisitor;

/**
 * A class to de-parse (that is, tranform from JSqlParser hierarchy into a
 * string) a {@link org.tinygroup.jsqlparser.statement.select.Select}
 */
public class SelectDeParser implements SelectVisitor, OrderByVisitor,
		SelectItemVisitor, FromItemVisitor {

	private StringBuilder buffer;
	private List<Object> values = new ArrayList<Object>();
	private ExpressionVisitor expressionVisitor;

	public SelectDeParser() {
	}

	/**
	 * @param expressionVisitor
	 *            a {@link ExpressionVisitor} to de-parse expressions. It has to
	 *            share the same<br>
	 *            StringBuilder (buffer parameter) as this object in order to
	 *            work
	 * @param buffer
	 *            the buffer that will be filled with the select
	 */
	public SelectDeParser(ExpressionVisitor expressionVisitor,
			StringBuilder buffer, List<Object> values) {
		this.buffer = buffer;
		this.expressionVisitor = expressionVisitor;
		this.values = values;
	}

	public void visit(PlainSelect plainSelect) {
		buffer.append("SELECT ");
		for (Iterator<SelectItem> iter = plainSelect.getSelectItems()
				.iterator(); iter.hasNext();) {
			SelectItem selectItem = iter.next();
			selectItem.accept(this);
			if (iter.hasNext()) {
				buffer.append(",");
			}
		}

		if (!CollectionUtil.isEmpty(plainSelect.getIntoTables())) {
			buffer.append(" INTO ");
			for (Iterator<Table> iter = plainSelect.getIntoTables().iterator(); iter
					.hasNext();) {
				visit(iter.next());
				if (iter.hasNext()) {
					buffer.append(",");
				}
			}
		}

		if (plainSelect.getFromItem() != null) {
			buffer.append(" FROM ");
			plainSelect.getFromItem().accept(this);
		}

		if (plainSelect.getJoins() != null) {
			for (Join join : plainSelect.getJoins()) {
				deparseJoin(join);
			}
		}

		if (plainSelect.getWhere() != null) {
			buffer.append(" WHERE ");
			plainSelect.getWhere().accept(expressionVisitor);
		}

		if (plainSelect.getOracleHierarchical() != null) {
			plainSelect.getOracleHierarchical().accept(expressionVisitor);
		}

		if (plainSelect.getGroupByColumnReferences() != null) {
			buffer.append(" GROUP BY ");
			for (Iterator<Expression> iter = plainSelect
					.getGroupByColumnReferences().iterator(); iter.hasNext();) {
				Expression columnReference = iter.next();
				columnReference.accept(expressionVisitor);
				if (iter.hasNext()) {
					buffer.append(",");
				}
			}
		}

		if (plainSelect.getHaving() != null) {
			buffer.append(" HAVING ");
			plainSelect.getHaving().accept(expressionVisitor);
		}

		if (plainSelect.getOrderByElements() != null) {
			deparseOrderBy(plainSelect.isOracleSiblings(),
					plainSelect.getOrderByElements());
		}

		if (plainSelect.getLimit() != null) {
			deparseLimit(plainSelect.getLimit());
		}
		if (plainSelect.getOffset() != null) {
			deparseOffset(plainSelect.getOffset());
		}
		if (plainSelect.getFetch() != null) {
			deparseFetch(plainSelect.getFetch());
		}
		if (plainSelect.isForUpdate()) {
			buffer.append(" FOR UPDATE");
		}

	}

	public void visit(OrderByElement orderBy) {
		orderBy.getExpression().accept(expressionVisitor);
		if (!orderBy.isAsc()) {
			buffer.append(" DESC");
		} else if (orderBy.isAscDescPresent()) {
			buffer.append(" ASC");
		}
		if (orderBy.getNullOrdering() != null) {
			buffer.append(' ');
			buffer.append(orderBy.getNullOrdering() == OrderByElement.NullOrdering.NULLS_FIRST ? "NULLS FIRST"
					: "NULLS LAST");
		}
	}

	public void visit(Column column) {
		buffer.append(column.getFullyQualifiedName());
	}

	public void visit(AllTableColumns allTableColumns) {
		buffer.append(allTableColumns.getTable().getFullyQualifiedName())
				.append(".*");
	}

	public void visit(SelectExpressionItem selectExpressionItem) {
		selectExpressionItem.getExpression().accept(expressionVisitor);
		if (selectExpressionItem.getAlias() != null) {
			buffer.append(selectExpressionItem.getAlias().toString());
		}

	}

	public void visit(SubSelect subSelect) {
		buffer.append("(");
		subSelect.getSelectBody().accept(this);
		buffer.append(")");
		Alias alias = subSelect.getAlias();
		if (alias != null) {
			buffer.append(alias.toString());
		}
	}

	public void visit(Table tableName) {
		buffer.append(tableName.getFullyQualifiedName());
		Alias alias = tableName.getAlias();
		if (alias != null) {
			buffer.append(alias);
		}
	}

	public void deparseOrderBy(List<OrderByElement> orderByElements) {
		deparseOrderBy(false, orderByElements);
	}

	public void deparseOrderBy(boolean oracleSiblings,
			List<OrderByElement> orderByElements) {
		if (oracleSiblings) {
			buffer.append(" ORDER SIBLINGS BY ");
		} else {
			buffer.append(" ORDER BY ");
		}
		for (Iterator<OrderByElement> iter = orderByElements.iterator(); iter
				.hasNext();) {
			OrderByElement orderByElement = iter.next();
			orderByElement.accept(this);
			if (iter.hasNext()) {
				buffer.append(",");
			}
		}
	}

	public void deparseLimit(Limit limit) {
		// LIMIT n OFFSET skip
		if (limit.isRowCountJdbcParameter()) {
			buffer.append(" LIMIT ");
			buffer.append("?");
			values.add(limit.getRowCount());
		} else if (limit.getRowCount() >= 0) {
			buffer.append(" LIMIT ");
			buffer.append(limit.getRowCount());
		} else if (limit.isLimitNull()) {
			buffer.append(" LIMIT NULL");
		}

		if (limit.isOffsetJdbcParameter()) {
			buffer.append(" OFFSET ?");
			values.add(limit.getOffset());
		} else if (limit.getOffset() != 0) {
			buffer.append(" OFFSET ").append(limit.getOffset());
		}

	}

	public void deparseOffset(Offset offset) {
		// OFFSET offset
		// or OFFSET offset (ROW | ROWS)
		if (offset.isOffsetJdbcParameter()) {
			buffer.append(" OFFSET ?");
			values.add(offset.getOffset());
		} else if (offset.getOffset() != 0) {
			buffer.append(" OFFSET ");
			buffer.append(offset.getOffset());
		}
		if (offset.getOffsetParam() != null) {
			buffer.append(" ").append(offset.getOffsetParam());
		}

	}

	public void deparseFetch(Fetch fetch) {
		// FETCH (FIRST | NEXT) row_count (ROW | ROWS) ONLY
		buffer.append(" FETCH ");
		if (fetch.isFetchParamFirst()) {
			buffer.append("FIRST ");
		} else {
			buffer.append("NEXT ");
		}
		if (fetch.isFetchJdbcParameter()) {
			buffer.append("?");
			values.add(fetch.getRowCount());
		} else {
			buffer.append(fetch.getRowCount());
		}
		buffer.append(" ").append(fetch.getFetchParam()).append(" ONLY");

	}

	public StringBuilder getBuffer() {
		return buffer;
	}

	public void setBuffer(StringBuilder buffer) {
		this.buffer = buffer;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	public ExpressionVisitor getExpressionVisitor() {
		return expressionVisitor;
	}

	public void setExpressionVisitor(ExpressionVisitor visitor) {
		expressionVisitor = visitor;
	}

	public void visit(SubJoin subjoin) {
		buffer.append("(");
		subjoin.getLeft().accept(this);
		deparseJoin(subjoin.getJoin());
		buffer.append(")");
	}

	public void deparseJoin(Join join) {
		if (join.isSimple()) {
			buffer.append(",");
		} else {

			if (join.isRight()) {
				buffer.append(" RIGHT");
			} else if (join.isNatural()) {
				buffer.append(" NATURAL");
			} else if (join.isFull()) {
				buffer.append(" FULL");
			} else if (join.isLeft()) {
				buffer.append(" LEFT");
			} else if (join.isCross()) {
				buffer.append(" CROSS");
			}

			if (join.isOuter()) {
				buffer.append(" OUTER");
			}

			buffer.append(" JOIN ");

		}

		FromItem fromItem = join.getRightItem();
		fromItem.accept(this);
		if (join.getOnExpression() != null) {
			buffer.append(" ON ");
			join.getOnExpression().accept(expressionVisitor);
		}
		if (join.getUsingColumns() != null) {
			buffer.append(" USING (");
			for (Iterator<Column> iterator = join.getUsingColumns().iterator(); iterator
					.hasNext();) {
				Column column = iterator.next();
				buffer.append(column.getFullyQualifiedName());
				if (iterator.hasNext()) {
					buffer.append(",");
				}
			}
			buffer.append(")");
		}

	}

	public void visit(SetOperationList list) {
		for (int i = 0; i < list.getPlainSelects().size(); i++) {
			if (i != 0) {
				buffer.append(' ').append(list.getOperations().get(i - 1))
						.append(' ');
			}
			buffer.append("(");
			PlainSelect plainSelect = list.getPlainSelects().get(i);
			plainSelect.accept(this);
			buffer.append(")");
		}
		if (list.getOrderByElements() != null) {
			deparseOrderBy(list.getOrderByElements());
		}

		if (list.getLimit() != null) {
			deparseLimit(list.getLimit());
		}
		if (list.getOffset() != null) {
			deparseOffset(list.getOffset());
		}
		if (list.getFetch() != null) {
			deparseFetch(list.getFetch());
		}
	}

	public void visit(LateralSubSelect lateralSubSelect) {
		buffer.append(lateralSubSelect.toString());
	}

	public void visit(ValuesList valuesList) {
		buffer.append(valuesList.toString());
	}

	public void visit(AllColumns allColumns) {
		buffer.append('*');
	}

	public void visit(FromItemList fromItemList) {
		buffer.append(DslUtil.getStringList(fromItemList.getFromItems()));
	}

	public void visit(Distinct distinct) {
		buffer.append("DISTINCT ");
		SelectItem selectItem = distinct.getSelectItem();
		if (selectItem != null) {
			buffer.append("(");
			selectItem.accept(this);
			buffer.append(") ");
		}
	}

	public void visit(Top top) {
		buffer.append("TOP ");
		if (top.hasParenthesis()) {
			buffer.append("(");
		}
		if (top.isRowCountJdbcParameter()) {
			buffer.append("?");
			values.add(top.getRowCount());
		} else {
			buffer.append(top.getRowCount());
		}
		if (top.hasParenthesis()) {
			buffer.append(")");
		}
		if (top.isPercentage()) {
			buffer.append(" PERCENT");
		}
	}

	public void visit(CustomSelectItem selectItem) {
		buffer.append(selectItem);
	}

	public void visit(Function function) {
		buffer.append(function);
	}
}