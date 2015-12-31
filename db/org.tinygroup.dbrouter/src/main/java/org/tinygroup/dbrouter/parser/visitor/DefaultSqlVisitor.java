package org.tinygroup.dbrouter.parser.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.commons.tools.Assert;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.dbrouter.exception.DbrouterRuntimeException;
import org.tinygroup.dbrouter.parser.GroupByColumn;
import org.tinygroup.dbrouter.parser.OrderByColumn;
import org.tinygroup.dbrouter.parser.base.ColumnInfo;
import org.tinygroup.dbrouter.parser.base.Condition;
import org.tinygroup.jsqlparser.expression.DateValue;
import org.tinygroup.jsqlparser.expression.DoubleValue;
import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.Function;
import org.tinygroup.jsqlparser.expression.JdbcParameter;
import org.tinygroup.jsqlparser.expression.LongValue;
import org.tinygroup.jsqlparser.expression.StringValue;
import org.tinygroup.jsqlparser.expression.TimeValue;
import org.tinygroup.jsqlparser.expression.TimestampValue;
import org.tinygroup.jsqlparser.expression.operators.conditional.AndExpression;
import org.tinygroup.jsqlparser.expression.operators.conditional.OrExpression;
import org.tinygroup.jsqlparser.expression.operators.relational.EqualsTo;
import org.tinygroup.jsqlparser.expression.operators.relational.ExpressionList;
import org.tinygroup.jsqlparser.expression.operators.relational.InExpression;
import org.tinygroup.jsqlparser.expression.operators.relational.ItemsList;
import org.tinygroup.jsqlparser.expression.operators.relational.MultiExpressionList;
import org.tinygroup.jsqlparser.expression.operators.relational.NotEqualsTo;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.select.Fetch;
import org.tinygroup.jsqlparser.statement.select.Limit;
import org.tinygroup.jsqlparser.statement.select.Offset;
import org.tinygroup.jsqlparser.statement.select.OrderByElement;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;
import org.tinygroup.jsqlparser.statement.select.SelectExpressionItem;
import org.tinygroup.jsqlparser.statement.select.SelectItem;
import org.tinygroup.jsqlparser.statement.select.SetOperationList;
import org.tinygroup.jsqlparser.statement.select.WithItem;

/**
 * 默认的sql访问者
 * 
 * @author renhui
 *
 */
public class DefaultSqlVisitor extends StatementSqlVisitor1 {

	private final List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
	private final List<Condition> conditions = new ArrayList<Condition>();
	private final List<OrderByColumn> orderByColumns = new ArrayList<OrderByColumn>();
	private final List<GroupByColumn> groupByColumns = new ArrayList<GroupByColumn>();

	private static final String NEQ_OPERATOR = "<>";
	private static final String IN_OPERATOR = "in";
	private static final String NOTIN_OPERATOR = "not in";
	private static final String EQUALS_OPERATOR = "=";
	private int columnIndex = 0;// 字段的索引
	private int jdbcParameterIndex = 0;// ?符合出现的位置
	private Map<Integer, Condition> conditionMap = new HashMap<Integer, Condition>();
	private boolean existGroupFunction;//
	private boolean existAvg;
	private long skip;
	private long rowCount;
	private long max;
	private boolean isForUpdate;
	
	private List<Object> arguments = new ArrayList<Object>();// sql的参数信息,sql语句"?"符号关联的参数值
	private final static int DEFAULT_SKIP_MAX = -1000;

	public DefaultSqlVisitor(String logicTable, String targetTable,
			List<Object> arguments) {
		super(logicTable, targetTable);
		this.arguments = arguments;
	}

	public DefaultSqlVisitor(List<Object> arguments) {
		super();
		this.arguments = arguments;
	}



	public boolean isExistGroupFunction() {
		return existGroupFunction;
	}

	public boolean isExistAvg() {
		return existAvg;
	}

	public List<OrderByColumn> getOrderByColumns() {
		return orderByColumns;
	}

	public List<GroupByColumn> getGroupByColumns() {
		return groupByColumns;
	}

	public long getSkip() {
		return skip;
	}

	public long getRowCount() {
		return rowCount;
	}

	public long getMax() {
		return max;
	}

	public boolean isForUpdate() {
		return isForUpdate;
	}

	@Override
	public void visit(ExpressionList expressionList) {
		List<Expression> expressions = expressionList.getExpressions();
		if (!CollectionUtil.isEmpty(expressions)) {
			for (Expression expression : expressions) {
				expression.accept(this);
			}
		}
	}

	@Override
	public void visit(MultiExpressionList multiExprList) {
		List<ExpressionList> exprList = multiExprList.getExprList();
		if (!CollectionUtil.isEmpty(exprList)) {
			for (ExpressionList expressionList : exprList) {
				visit(expressionList);
			}
		}
	}

	@Override
	public void visit(Function function) {
		if (isGroupFunction(function.getName())) {
			existGroupFunction = true;
			if (AVG.equalsIgnoreCase(function.getName())) {
				existAvg = true;
			}
		}
	}

	public boolean isGroupFunction(String functionName) {
		if (StringUtil.isBlank(functionName)) {
			return false;
		}
		if (functionName.equalsIgnoreCase(AVG)
				|| functionName.equalsIgnoreCase(COUNT)
				|| functionName.equalsIgnoreCase(MAX)
				|| functionName.equalsIgnoreCase(MIN)
				|| functionName.equalsIgnoreCase(SUM)) {
			return true;
		}
		return false;
	}

	@Override
	public void visit(JdbcParameter jdbcParameter) {
		Condition condition = conditionMap.get(columnIndex);
		checkCondition(condition);
		condition.getValues().add(arguments.get(jdbcParameterIndex++));
	}

	private void checkCondition(Condition condition) {
		Assert.assertNotNull(condition, "sql关联的条件信息不能为空");
	}

	@Override
	public void visit(DoubleValue doubleValue) {
		Condition condition = conditionMap.get(columnIndex);
		checkCondition(condition);
		condition.getValues().add(doubleValue);
	}

	@Override
	public void visit(LongValue longValue) {
		Condition condition = conditionMap.get(columnIndex);
		checkCondition(condition);
		condition.getValues().add(longValue);
	}

	@Override
	public void visit(DateValue dateValue) {
		Condition condition = conditionMap.get(columnIndex);
		checkCondition(condition);
		condition.getValues().add(dateValue);
	}

	@Override
	public void visit(TimeValue timeValue) {
		Condition condition = conditionMap.get(columnIndex);
		checkCondition(condition);
		condition.getValues().add(timeValue);
	}

	@Override
	public void visit(TimestampValue timestampValue) {
		Condition condition = conditionMap.get(columnIndex);
		checkCondition(condition);
		condition.getValues().add(timestampValue);
	}

	@Override
	public void visit(StringValue stringValue) {
		Condition condition = conditionMap.get(columnIndex);
		checkCondition(condition);
		condition.getValues().add(stringValue);
	}

	@Override
	public void visit(AndExpression andExpression) {
		andExpression.getLeftExpression().accept(this);
		andExpression.getRightExpression().accept(this);
	}

	@Override
	public void visit(OrExpression orExpression) {
		orExpression.getLeftExpression().accept(this);
		orExpression.getRightExpression().accept(this);
	}

	@Override
	public void visit(EqualsTo equalsTo) {
		Expression leftExpression = equalsTo.getLeftExpression();
		Expression rightExpression = equalsTo.getRightExpression();
		if (leftExpression instanceof Column) {
			Column column = (Column) leftExpression;
			if (equalsTo.isNot()) {
				addCondition(column, NEQ_OPERATOR);
			} else {
				addCondition(column, EQUALS_OPERATOR);
			}
		}
		rightExpression.accept(this);
	}

	protected void addCondition(Column column, String operator) {
		columnIndex++;
		ColumnInfo columnInfo = new ColumnInfo(column);
		Condition condition = new Condition();
		condition.setColumn(columnInfo);
		condition.setOperator(EQUALS_OPERATOR);
		conditions.add(condition);
		conditionMap.put(columnIndex, condition);
	}

	@Override
	public void visit(InExpression inExpression) {
		Expression leftExpression = inExpression.getLeftExpression();
		if (leftExpression instanceof Column) {
			Column column = (Column) leftExpression;
			if (inExpression.isNot()) {
				addCondition(column, NOTIN_OPERATOR);
			} else {
				addCondition(column, IN_OPERATOR);
			}
		}
		ItemsList rightItemsList = inExpression.getRightItemsList();
		rightItemsList.accept(this);
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		Expression leftExpression = notEqualsTo.getLeftExpression();
		if (leftExpression instanceof Column) {
			columnIndex++;
			Column column = (Column) leftExpression;
			if (notEqualsTo.isNot()) {
				addCondition(column, EQUALS_OPERATOR);
			} else {
				addCondition(column, NEQ_OPERATOR);
			}
		}
	}

	@Override
	public void visit(Column tableColumn) {
		columns.add(new ColumnInfo(tableColumn));
	}

	@Override
	public void visit(Table tableName) {
		tableNames.add(tableName.getName());
	}

	@Override
	public void visit(SelectExpressionItem selectExpressionItem) {
		selectExpressionItem.getExpression().accept(this);
	}

	@Override
	public void visit(OrderByElement orderBy) {
		Expression expression=orderBy.getExpression();
		if(expression instanceof Column){
			Column column=(Column)expression;
			orderByColumns.add(new OrderByColumn(column, orderBy.isAsc()));
		}
	}

	@Override
	public void visit(PlainSelect plainSelect) {

		if (plainSelect.getDistinct() != null) {
			iterateSelectItems(plainSelect.getDistinct().getOnSelectItems());
		}

		if (plainSelect.getSelectItems() != null) {
			iterateSelectItems(plainSelect.getSelectItems());
		}

		if (plainSelect.getIntoTables() != null) {
			iterateTables(plainSelect.getIntoTables());
		}

		if (plainSelect.getFromItem() != null) {
			plainSelect.getFromItem().accept(this);
		}

		if (plainSelect.getWhere() != null) {//
			plainSelect.getWhere().accept(this);
		}

		if (plainSelect.getGroupByColumnReferences() != null) {
			List<Expression> expressions = plainSelect
					.getGroupByColumnReferences();
			for (Expression expression : expressions) {
				if (expression instanceof Column) {
					Column column = (Column) expression;
					groupByColumns.add(new GroupByColumn(column));
				}
			}
		}
		if (plainSelect.getHaving() != null) {
			plainSelect.getHaving().accept(this);
		}
		if (plainSelect.getOrderByElements() != null) {
			List<OrderByElement> orderByElements = plainSelect
					.getOrderByElements();
			for (OrderByElement orderByElement : orderByElements) {
				Expression expression = orderByElement.getExpression();
				if (expression instanceof Column) {
					Column column = (Column) expression;
					orderByColumns.add(new OrderByColumn(column, orderByElement
							.isAsc()));
				}

			}
		}
		long skip = DEFAULT_SKIP_MAX;
		long rowCount = DEFAULT_SKIP_MAX;
		if (plainSelect.getLimit() != null) {
			Limit limit = plainSelect.getLimit();
			skip = getSkip(limit.isOffsetJdbcParameter(), limit.getOffset());

			rowCount = getRowCount(limit.isRowCountJdbcParameter(),
					limit.getRowCount());
		}

		if (plainSelect.getOffset() != null) {
			Offset offset = plainSelect.getOffset();
			skip = getSkip(offset.isOffsetJdbcParameter(), offset.getOffset());
		}

		if (plainSelect.getFetch() != null) {
			Fetch fetch = plainSelect.getFetch();
			rowCount = getRowCount(fetch.isFetchJdbcParameter(),
					fetch.getRowCount());
		}
		if (skip != DEFAULT_SKIP_MAX && skip < 0) {
			throw new DbrouterRuntimeException("the skip is less than 0");
		}
		if (rowCount != DEFAULT_SKIP_MAX && rowCount < 0) {
			throw new DbrouterRuntimeException(
					"the rowcount is less than 0");
		}
		this.skip = skip;
		this.rowCount = rowCount;
		this.max = getMax(skip, rowCount);
		this.isForUpdate=plainSelect.isForUpdate();
	}

	private long getMax(long skip, long rowCount) {
		if (skip == DEFAULT_SKIP_MAX) {
			if (rowCount == DEFAULT_SKIP_MAX) {
				// 如果skip和rowcount都不存在就返回默认值.
				return DEFAULT_SKIP_MAX;
			} else {
				// 如果skip不存在，就返回rowcount.
				return rowCount;
			}
		} else {
			if (rowCount == DEFAULT_SKIP_MAX) {
				return skip;
			} else {
				return skip + rowCount;
			}
		}
	}

	private long getSkip(boolean isJdbcParameter, long offset) {
		long skip = DEFAULT_SKIP_MAX;
		if (isJdbcParameter) {
			Object value = arguments.get(jdbcParameterIndex++);
			if (value == null) {
				skip = DEFAULT_SKIP_MAX;
			} else {
				try {
					skip = Long.parseLong(value.toString());
				} catch (Exception e) {
					throw new DbrouterRuntimeException(
							"bind limit var has an error , " + value
									+ " is not a long value");
				}
			}
		} else {
			skip = offset;
		}
		return skip;
	}

	private long getRowCount(boolean isJdbcParameter, long count) {
		long rowCount = DEFAULT_SKIP_MAX;
		if (isJdbcParameter) {
			Object value = arguments.get(jdbcParameterIndex++);
			if (value == null) {
				rowCount = DEFAULT_SKIP_MAX;
			} else {
				try {
					rowCount = Long.parseLong(value.toString());
				} catch (Exception e) {
					throw new DbrouterRuntimeException(
							"bind rowCount var has an error , " + value
									+ " is not a long value");
				}
			}
		} else {
			rowCount = count;
		}
		return rowCount;
	}

	private void iterateSelectItems(List<SelectItem> onSelectItems) {
		if (!CollectionUtil.isEmpty(onSelectItems)) {
			for (SelectItem selectItem : onSelectItems) {
				selectItem.accept(this);
			}
		}
	}

	private void iterateTables(List<Table> tables) {
		if (!CollectionUtil.isEmpty(tables)) {
			for (Table table : tables) {
				table.accept(this);
			}
		}
	}

	@Override
	public void visit(SetOperationList setOpList) {
		// TODO Auto-generated method stub
		super.visit(setOpList);
	}

	@Override
	public void visit(WithItem withItem) {
		List<SelectItem> selectItems = withItem.getWithItemList();
		if (!CollectionUtil.isEmpty(selectItems)) {
			for (SelectItem selectItem : selectItems) {
				selectItem.accept(this);
			}
		}
		withItem.getSelectBody().accept(this);
	}

}
