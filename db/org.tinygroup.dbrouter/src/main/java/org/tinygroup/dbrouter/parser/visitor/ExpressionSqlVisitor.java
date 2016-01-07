package org.tinygroup.dbrouter.parser.visitor;

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
import org.tinygroup.jsqlparser.expression.operators.relational.EqualsTo;
import org.tinygroup.jsqlparser.expression.operators.relational.InExpression;
import org.tinygroup.jsqlparser.expression.operators.relational.NotEqualsTo;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.statement.select.SelectVisitor;
import org.tinygroup.jsqlparser.util.deparser.ExpressionDeParser;

/**
 * 表达式的访问者
 * @author renhui
 *
 */
public class ExpressionSqlVisitor extends ExpressionDeParser {

	public static final String NEQ_OPERATOR = "<>";
	public static final String IN_OPERATOR = "in";
	public static final String NOTIN_OPERATOR = "not in";
	public static final String EQUALS_OPERATOR = "=";
	private SqlParserContext sqlParserContext;
	
	public ExpressionSqlVisitor(SelectVisitor selectVisitor,
			SqlParserContext sqlParserContext) {
		super(selectVisitor, sqlParserContext.getBuffer());
		this.sqlParserContext=sqlParserContext;
	}

	@Override
	public void visit(EqualsTo equalsTo) {
		Expression leftExpression = equalsTo.getLeftExpression();
		if (leftExpression instanceof Column) {
			Column column = (Column) leftExpression;
			if (equalsTo.isNot()) {
				addCondition(column, NEQ_OPERATOR);
			} else {
				addCondition(column, EQUALS_OPERATOR);
			}
		}
		super.visit(equalsTo);
	}
	
	protected void addCondition(Column column, String operator) {
		int columnIndex=sqlParserContext.getColumnIndex();
		columnIndex++;
		ColumnInfo columnInfo = new ColumnInfo(column);
		Condition condition = new Condition();
		condition.setColumn(columnInfo);
		condition.setOperator(operator);
		sqlParserContext.getConditions().add(condition);
		sqlParserContext.getConditionMap().put(columnIndex, condition);
		sqlParserContext.setColumnIndex(columnIndex);
	}


	@Override
	public void visit(DoubleValue doubleValue) {
		Condition condition = getCurrentCondition();
		if(condition!=null){
			condition.getValues().add(doubleValue.getValue());
		}
		super.visit(doubleValue);
	}

	private Condition getCurrentCondition() {
		return sqlParserContext.getConditionMap().get(sqlParserContext.getColumnIndex());
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
		super.visit(inExpression);
	}

	@Override
	public void visit(JdbcParameter jdbcParameter) {
		Condition condition = getCurrentCondition();
		if(condition!=null){
			condition.getValues().add(sqlParserContext.getParamValue());
		}
		super.visit(jdbcParameter);
	}

	@Override
	public void visit(LongValue longValue) {
		Condition condition = getCurrentCondition();
		if(condition!=null){
			condition.getValues().add(longValue.getValue());
		}
		super.visit(longValue);
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		Expression leftExpression = notEqualsTo.getLeftExpression();
		if (leftExpression instanceof Column) {
			Column column = (Column) leftExpression;
			if (notEqualsTo.isNot()) {
				addCondition(column, EQUALS_OPERATOR);
			} else {
				addCondition(column, NEQ_OPERATOR);
			}
		}
		super.visit(notEqualsTo);
	}

	@Override
	public void visit(StringValue stringValue) {
		Condition condition = getCurrentCondition();
		if(condition!=null){
			condition.getValues().add(stringValue.getValue());
		}
		super.visit(stringValue);
	}

	@Override
	public void visit(Column tableColumn) {
		sqlParserContext.getColumns().add(new ColumnInfo(tableColumn));
		super.visit(tableColumn);
	}

	@Override
	public void visit(Function function) {
		if (sqlParserContext.isGroupFunction(function.getName())) {
			sqlParserContext.setExistGroupFunction(true);
			if (SqlParserContext.AVG.equalsIgnoreCase(function.getName())) {
				sqlParserContext.setExistAvg(true);
			}
		}
		super.visit(function);
	}

	@Override
	public void visit(DateValue dateValue) {
		Condition condition = getCurrentCondition();
		if(condition!=null){
			condition.getValues().add(dateValue.getValue());
		}
		super.visit(dateValue);
	}

	@Override
	public void visit(TimestampValue timestampValue) {
		Condition condition = getCurrentCondition();
		if(condition!=null){
			condition.getValues().add(timestampValue.getValue());
		}
		super.visit(timestampValue);
	}

	@Override
	public void visit(TimeValue timeValue) {
		Condition condition = getCurrentCondition();
		if(condition!=null){
			condition.getValues().add(timeValue.getValue());
		}
		super.visit(timeValue);
	}

}
