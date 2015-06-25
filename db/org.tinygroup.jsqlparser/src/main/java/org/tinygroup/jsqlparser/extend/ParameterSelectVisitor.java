/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.jsqlparser.extend;

import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.ExpressionVisitorAdapter;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.select.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author renhui
 * 
 */
public class ParameterSelectVisitor implements SelectVisitor, OrderByVisitor,
		SelectItemVisitor, FromItemVisitor, PivotVisitor {

	private Map<String, Integer> positionMap;
	private AtomicInteger paramLength;
	private ExpressionVisitorAdapter expressionVisitor;

	public ParameterSelectVisitor(Map<String, Integer> positionMap,
			AtomicInteger paramLength) {
		super();
		this.positionMap = positionMap;
		this.paramLength = paramLength;
	}
	
	public ExpressionVisitorAdapter getExpressionVisitor() {
		return expressionVisitor;
	}

	public void setExpressionVisitor(ExpressionVisitorAdapter expressionVisitor) {
		this.expressionVisitor = expressionVisitor;
	}



	public void visit(Pivot pivot) {

	}

	public void visit(PivotXml pivot) {

	}

	public void visit(Table tableName) {

	}

	public void visit(SubSelect subSelect) {
		subSelect.getSelectBody().accept(this);
	}

	public void visit(SubJoin subjoin) {
        subjoin.getLeft().accept(this);
        deparseJoin(subjoin.getJoin());
	}

	public void visit(LateralSubSelect lateralSubSelect) {
		lateralSubSelect.getSubSelect().accept(this);
	}

	public void visit(ValuesList valuesList) {
		valuesList.getMultiExpressionList().accept(expressionVisitor);
	}

	public void visit(AllColumns allColumns) {

	}

	public void visit(AllTableColumns allTableColumns) {

	}

	public void visit(SelectExpressionItem selectExpressionItem) {
		selectExpressionItem.getExpression().accept(expressionVisitor);
	}

	public void visit(OrderByElement orderBy) {
		orderBy.getExpression().accept(expressionVisitor);
	}

	public void visit(PlainSelect plainSelect) {
		if (plainSelect.getDistinct() != null) {
			if (plainSelect.getDistinct().getOnSelectItems() != null) {
				for (Iterator<SelectItem> iter = plainSelect.getDistinct()
						.getOnSelectItems().iterator(); iter.hasNext();) {
					SelectItem selectItem = iter.next();
					selectItem.accept(this);
				}
			}
		}
		Top top = plainSelect.getTop();
		if (top != null) {
			if (top.isRowCountJdbcParameter()) {
				positionMap.put("top", paramLength.incrementAndGet());
			}
		}

		for (Iterator<SelectItem> iter = plainSelect.getSelectItems()
				.iterator(); iter.hasNext();) {
			SelectItem selectItem = iter.next();
			selectItem.accept(this);
		}

		if (plainSelect.getFromItem() != null) {
			plainSelect.getFromItem().accept(this);
		}

		if (plainSelect.getJoins() != null) {
			for (Join join : plainSelect.getJoins()) {
				deparseJoin(join);
			}
		}

		if (plainSelect.getWhere() != null) {
			plainSelect.getWhere().accept(expressionVisitor);
		}

		if (plainSelect.getOracleHierarchical() != null) {
			plainSelect.getOracleHierarchical().accept(expressionVisitor);
		}

		if (plainSelect.getGroupByColumnReferences() != null) {
			for (Iterator<Expression> iter = plainSelect
					.getGroupByColumnReferences().iterator(); iter.hasNext();) {
				Expression columnReference = iter.next();
				columnReference.accept(expressionVisitor);
			}
		}

		if (plainSelect.getHaving() != null) {
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
	}

	public void deparseLimit(Limit limit) {
		if (limit.isOffsetJdbcParameter()) {
			positionMap.put("start", paramLength.incrementAndGet());
		}

		if (limit.isRowCountJdbcParameter()) {
			positionMap.put("limit", paramLength.incrementAndGet());
		}

	}

	public void deparseOffset(Offset offset) {
		// OFFSET offset
		// or OFFSET offset (ROW | ROWS)
		if (offset.isOffsetJdbcParameter()) {
			positionMap.put("offset", paramLength.incrementAndGet());
		}

	}

	public void deparseFetch(Fetch fetch) {
		if (fetch.isFetchJdbcParameter()) {
			positionMap.put("fetch", paramLength.incrementAndGet());
		}
	}

	public void deparseOrderBy(List<OrderByElement> orderByElements) {
		deparseOrderBy(false, orderByElements);
	}

	public void deparseOrderBy(boolean oracleSiblings,
			List<OrderByElement> orderByElements) {
		for (Iterator<OrderByElement> iter = orderByElements.iterator(); iter
				.hasNext();) {
			OrderByElement orderByElement = iter.next();
			orderByElement.accept(this);
		}
	}

	public void deparseJoin(Join join) {
		FromItem fromItem = join.getRightItem();
		fromItem.accept(this);
		if (join.getOnExpression() != null) {
			join.getOnExpression().accept(expressionVisitor);
		}
	}

	public void visit(SetOperationList list) {
		for (int i = 0; i < list.getPlainSelects().size(); i++) {
			PlainSelect plainSelect = list.getPlainSelects().get(i);
			plainSelect.accept(this);
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

	public void visit(WithItem withItem) {
		if (withItem.getWithItemList() != null) {
			for (Iterator<SelectItem> iter = withItem.getWithItemList()
					.iterator(); iter.hasNext();) {
				SelectItem selectItem = iter.next();
				selectItem.accept(this);
			}
		}
		withItem.getSelectBody().accept(this);
	}

}
