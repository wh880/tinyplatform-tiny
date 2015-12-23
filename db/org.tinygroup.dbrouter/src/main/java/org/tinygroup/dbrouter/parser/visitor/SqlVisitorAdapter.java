package org.tinygroup.dbrouter.parser.visitor;

import org.tinygroup.jsqlparser.expression.AllComparisonExpression;
import org.tinygroup.jsqlparser.expression.AnalyticExpression;
import org.tinygroup.jsqlparser.expression.AnyComparisonExpression;
import org.tinygroup.jsqlparser.expression.CaseExpression;
import org.tinygroup.jsqlparser.expression.CastExpression;
import org.tinygroup.jsqlparser.expression.DateValue;
import org.tinygroup.jsqlparser.expression.DoubleValue;
import org.tinygroup.jsqlparser.expression.ExpressionVisitor;
import org.tinygroup.jsqlparser.expression.ExtractExpression;
import org.tinygroup.jsqlparser.expression.Function;
import org.tinygroup.jsqlparser.expression.IntervalExpression;
import org.tinygroup.jsqlparser.expression.JdbcNamedParameter;
import org.tinygroup.jsqlparser.expression.JdbcParameter;
import org.tinygroup.jsqlparser.expression.JsonExpression;
import org.tinygroup.jsqlparser.expression.LongValue;
import org.tinygroup.jsqlparser.expression.NullValue;
import org.tinygroup.jsqlparser.expression.OracleHierarchicalExpression;
import org.tinygroup.jsqlparser.expression.Parenthesis;
import org.tinygroup.jsqlparser.expression.SignedExpression;
import org.tinygroup.jsqlparser.expression.StringValue;
import org.tinygroup.jsqlparser.expression.TimeValue;
import org.tinygroup.jsqlparser.expression.TimestampValue;
import org.tinygroup.jsqlparser.expression.WhenClause;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Addition;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Concat;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Division;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Modulo;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Multiplication;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Subtraction;
import org.tinygroup.jsqlparser.expression.operators.conditional.AndExpression;
import org.tinygroup.jsqlparser.expression.operators.conditional.OrExpression;
import org.tinygroup.jsqlparser.expression.operators.relational.Between;
import org.tinygroup.jsqlparser.expression.operators.relational.EqualsTo;
import org.tinygroup.jsqlparser.expression.operators.relational.ExistsExpression;
import org.tinygroup.jsqlparser.expression.operators.relational.ExpressionList;
import org.tinygroup.jsqlparser.expression.operators.relational.GreaterThan;
import org.tinygroup.jsqlparser.expression.operators.relational.GreaterThanEquals;
import org.tinygroup.jsqlparser.expression.operators.relational.InExpression;
import org.tinygroup.jsqlparser.expression.operators.relational.IsNullExpression;
import org.tinygroup.jsqlparser.expression.operators.relational.ItemsListVisitor;
import org.tinygroup.jsqlparser.expression.operators.relational.LikeExpression;
import org.tinygroup.jsqlparser.expression.operators.relational.Matches;
import org.tinygroup.jsqlparser.expression.operators.relational.MinorThan;
import org.tinygroup.jsqlparser.expression.operators.relational.MinorThanEquals;
import org.tinygroup.jsqlparser.expression.operators.relational.MultiExpressionList;
import org.tinygroup.jsqlparser.expression.operators.relational.NotEqualsTo;
import org.tinygroup.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import org.tinygroup.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.StatementVisitor;
import org.tinygroup.jsqlparser.statement.Statements;
import org.tinygroup.jsqlparser.statement.alter.Alter;
import org.tinygroup.jsqlparser.statement.create.index.CreateIndex;
import org.tinygroup.jsqlparser.statement.create.table.CreateTable;
import org.tinygroup.jsqlparser.statement.create.view.CreateView;
import org.tinygroup.jsqlparser.statement.delete.Delete;
import org.tinygroup.jsqlparser.statement.drop.Drop;
import org.tinygroup.jsqlparser.statement.execute.Execute;
import org.tinygroup.jsqlparser.statement.insert.Insert;
import org.tinygroup.jsqlparser.statement.replace.Replace;
import org.tinygroup.jsqlparser.statement.select.AllColumns;
import org.tinygroup.jsqlparser.statement.select.AllTableColumns;
import org.tinygroup.jsqlparser.statement.select.FromItemVisitor;
import org.tinygroup.jsqlparser.statement.select.LateralSubSelect;
import org.tinygroup.jsqlparser.statement.select.OrderByElement;
import org.tinygroup.jsqlparser.statement.select.OrderByVisitor;
import org.tinygroup.jsqlparser.statement.select.Pivot;
import org.tinygroup.jsqlparser.statement.select.PivotVisitor;
import org.tinygroup.jsqlparser.statement.select.PivotXml;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.statement.select.SelectExpressionItem;
import org.tinygroup.jsqlparser.statement.select.SelectItemVisitor;
import org.tinygroup.jsqlparser.statement.select.SelectVisitor;
import org.tinygroup.jsqlparser.statement.select.SetOperationList;
import org.tinygroup.jsqlparser.statement.select.SubJoin;
import org.tinygroup.jsqlparser.statement.select.SubSelect;
import org.tinygroup.jsqlparser.statement.select.ValuesList;
import org.tinygroup.jsqlparser.statement.select.WithItem;
import org.tinygroup.jsqlparser.statement.truncate.Truncate;
import org.tinygroup.jsqlparser.statement.update.Update;

/**
 * sqlvisitor的适配器
 * @author renhui
 *
 */
public abstract class SqlVisitorAdapter implements StatementVisitor,SelectVisitor,OrderByVisitor, SelectItemVisitor, FromItemVisitor, PivotVisitor,ExpressionVisitor,ItemsListVisitor{

	protected static final String COUNT = "count";
	protected static final String MAX = "max";
	protected static final String AVG = "avg";
	protected static final String MIN = "min";
	protected static final String SUM = "sum";
	
	
	public void visit(Select select) {
		
	}

	public void visit(Delete delete) {
		
		
	}

	public void visit(Update update) {
		
		
	}

	public void visit(Insert insert) {
		
		
	}

	public void visit(Replace replace) {
		
		
	}

	public void visit(Drop drop) {
		
		
	}

	public void visit(Truncate truncate) {
		
		
	}

	public void visit(CreateIndex createIndex) {
		
		
	}

	public void visit(CreateTable createTable) {
		
		
	}

	public void visit(CreateView createView) {
		
		
	}

	public void visit(Alter alter) {
		
		
	}

	public void visit(Statements stmts) {
		
		
	}

	public void visit(Execute execute) {
		
		
	}

	public void visit(ExpressionList expressionList) {
		
		
	}

	public void visit(MultiExpressionList multiExprList) {
		
		
	}

	public void visit(NullValue nullValue) {
		
		
	}

	public void visit(Function function) {
		
		
	}

	public void visit(SignedExpression signedExpression) {
		
		
	}

	public void visit(JdbcParameter jdbcParameter) {
		
		
	}

	public void visit(JdbcNamedParameter jdbcNamedParameter) {
		
		
	}

	public void visit(DoubleValue doubleValue) {
		
		
	}

	public void visit(LongValue longValue) {
		
		
	}

	public void visit(DateValue dateValue) {
		
		
	}

	public void visit(TimeValue timeValue) {
		
		
	}

	public void visit(TimestampValue timestampValue) {
		
		
	}

	public void visit(Parenthesis parenthesis) {
		
		
	}

	public void visit(StringValue stringValue) {
		
		
	}

	public void visit(Addition addition) {
		
		
	}

	public void visit(Division division) {
		
		
	}

	public void visit(Multiplication multiplication) {
		
		
	}

	public void visit(Subtraction subtraction) {
		
		
	}

	public void visit(AndExpression andExpression) {
		
		
	}

	public void visit(OrExpression orExpression) {
		
		
	}

	public void visit(Between between) {
		
		
	}

	public void visit(EqualsTo equalsTo) {
		
		
	}

	public void visit(GreaterThan greaterThan) {
		
		
	}

	public void visit(GreaterThanEquals greaterThanEquals) {
		
		
	}

	public void visit(InExpression inExpression) {
		
		
	}

	public void visit(IsNullExpression isNullExpression) {
		
		
	}

	public void visit(LikeExpression likeExpression) {
		
		
	}

	public void visit(MinorThan minorThan) {
		
		
	}

	public void visit(MinorThanEquals minorThanEquals) {
		
		
	}

	public void visit(NotEqualsTo notEqualsTo) {
		
		
	}

	public void visit(Column tableColumn) {
		
		
	}

	public void visit(CaseExpression caseExpression) {
		
		
	}

	public void visit(WhenClause whenClause) {
		
		
	}

	public void visit(ExistsExpression existsExpression) {
		
		
	}

	public void visit(AllComparisonExpression allComparisonExpression) {
		
		
	}

	public void visit(AnyComparisonExpression anyComparisonExpression) {
		
		
	}

	public void visit(Concat concat) {
		
		
	}

	public void visit(Matches matches) {
		
		
	}

	public void visit(BitwiseAnd bitwiseAnd) {
		
		
	}

	public void visit(BitwiseOr bitwiseOr) {
		
		
	}

	public void visit(BitwiseXor bitwiseXor) {
		
		
	}

	public void visit(CastExpression cast) {
		
		
	}

	public void visit(Modulo modulo) {
		
		
	}

	public void visit(AnalyticExpression aexpr) {
		
		
	}

	public void visit(ExtractExpression eexpr) {
		
		
	}

	public void visit(IntervalExpression iexpr) {
		
		
	}

	public void visit(OracleHierarchicalExpression oexpr) {
		
		
	}

	public void visit(RegExpMatchOperator rexpr) {
		
		
	}

	public void visit(JsonExpression jsonExpr) {
		
		
	}

	public void visit(RegExpMySQLOperator regExpMySQLOperator) {
		
		
	}

	public void visit(Pivot pivot) {
		
		
	}

	public void visit(PivotXml pivot) {
		
		
	}

	public void visit(Table tableName) {
		
		
	}

	public void visit(SubSelect subSelect) {
		
		
	}

	public void visit(SubJoin subjoin) {
		
		
	}

	public void visit(LateralSubSelect lateralSubSelect) {
		
		
	}

	public void visit(ValuesList valuesList) {
		
		
	}

	public void visit(AllColumns allColumns) {
		
		
	}

	public void visit(AllTableColumns allTableColumns) {
		
		
	}

	public void visit(SelectExpressionItem selectExpressionItem) {
		
	}

	public void visit(OrderByElement orderBy) {
		
	}

	public void visit(PlainSelect plainSelect) {
		
		
	}

	public void visit(SetOperationList setOpList) {
		
		
	}

	public void visit(WithItem withItem) {
		
		
	}


}
