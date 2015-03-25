package org.tinygroup.tinydbdsl.visitor;

import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.expression.AllComparisonExpression;
import org.tinygroup.tinydbdsl.expression.AnalyticExpression;
import org.tinygroup.tinydbdsl.expression.AnyComparisonExpression;
import org.tinygroup.tinydbdsl.expression.CaseExpression;
import org.tinygroup.tinydbdsl.expression.DateValue;
import org.tinygroup.tinydbdsl.expression.DoubleValue;
import org.tinygroup.tinydbdsl.expression.ExtractExpression;
import org.tinygroup.tinydbdsl.expression.Function;
import org.tinygroup.tinydbdsl.expression.IntervalExpression;
import org.tinygroup.tinydbdsl.expression.JdbcNamedParameter;
import org.tinygroup.tinydbdsl.expression.JdbcParameter;
import org.tinygroup.tinydbdsl.expression.JsonExpression;
import org.tinygroup.tinydbdsl.expression.LongValue;
import org.tinygroup.tinydbdsl.expression.NullValue;
import org.tinygroup.tinydbdsl.expression.OracleHierarchicalExpression;
import org.tinygroup.tinydbdsl.expression.Parenthesis;
import org.tinygroup.tinydbdsl.expression.SignedExpression;
import org.tinygroup.tinydbdsl.expression.StringValue;
import org.tinygroup.tinydbdsl.expression.TimeValue;
import org.tinygroup.tinydbdsl.expression.TimestampValue;
import org.tinygroup.tinydbdsl.expression.WhenClause;
import org.tinygroup.tinydbdsl.expression.arithmetic.Addition;
import org.tinygroup.tinydbdsl.expression.arithmetic.BitwiseAnd;
import org.tinygroup.tinydbdsl.expression.arithmetic.BitwiseOr;
import org.tinygroup.tinydbdsl.expression.arithmetic.BitwiseXor;
import org.tinygroup.tinydbdsl.expression.arithmetic.Concat;
import org.tinygroup.tinydbdsl.expression.arithmetic.Division;
import org.tinygroup.tinydbdsl.expression.arithmetic.Modulo;
import org.tinygroup.tinydbdsl.expression.arithmetic.Multiplication;
import org.tinygroup.tinydbdsl.expression.arithmetic.Subtraction;
import org.tinygroup.tinydbdsl.expression.conditional.AndExpression;
import org.tinygroup.tinydbdsl.expression.conditional.ConditionExpressionList;
import org.tinygroup.tinydbdsl.expression.conditional.OrExpression;
import org.tinygroup.tinydbdsl.expression.relational.Between;
import org.tinygroup.tinydbdsl.expression.relational.EqualsTo;
import org.tinygroup.tinydbdsl.expression.relational.ExistsExpression;
import org.tinygroup.tinydbdsl.expression.relational.GreaterThan;
import org.tinygroup.tinydbdsl.expression.relational.GreaterThanEquals;
import org.tinygroup.tinydbdsl.expression.relational.InExpression;
import org.tinygroup.tinydbdsl.expression.relational.IsNullExpression;
import org.tinygroup.tinydbdsl.expression.relational.LikeExpression;
import org.tinygroup.tinydbdsl.expression.relational.Matches;
import org.tinygroup.tinydbdsl.expression.relational.MinorThan;
import org.tinygroup.tinydbdsl.expression.relational.MinorThanEquals;
import org.tinygroup.tinydbdsl.expression.relational.NotEqualsTo;
import org.tinygroup.tinydbdsl.expression.relational.RegExpMatchOperator;
import org.tinygroup.tinydbdsl.expression.relational.RegExpMySQLOperator;
import org.tinygroup.tinydbdsl.formitem.SubSelect;

/**
 *  表达式的结构的访问者 
 * @author renhui
 *
 */
public interface ExpressionVisitor {

	void visit(NullValue nullValue);

	void visit(Function function);

	void visit(SignedExpression signedExpression);

	void visit(JdbcParameter jdbcParameter);

    void visit(JdbcNamedParameter jdbcNamedParameter);

	void visit(DoubleValue doubleValue);

	void visit(LongValue longValue);

	void visit(DateValue dateValue);

	void visit(TimeValue timeValue);

	void visit(TimestampValue timestampValue);

	void visit(Parenthesis parenthesis);

	void visit(StringValue stringValue);

	void visit(Addition addition);

	void visit(Division division);

	void visit(Multiplication multiplication);

	void visit(Subtraction subtraction);

	void visit(AndExpression andExpression);

	void visit(OrExpression orExpression);

	void visit(Between between);

	void visit(EqualsTo equalsTo);

	void visit(GreaterThan greaterThan);

	void visit(GreaterThanEquals greaterThanEquals);

	void visit(InExpression inExpression);

	void visit(IsNullExpression isNullExpression);

	void visit(LikeExpression likeExpression);

	void visit(MinorThan minorThan);

	void visit(MinorThanEquals minorThanEquals);

	void visit(NotEqualsTo notEqualsTo);

	void visit(Column tableColumn);

	void visit(SubSelect subSelect);

	void visit(CaseExpression caseExpression);

	void visit(WhenClause whenClause);

	void visit(ExistsExpression existsExpression);

	void visit(AllComparisonExpression allComparisonExpression);

	void visit(AnyComparisonExpression anyComparisonExpression);

	void visit(Concat concat);

	void visit(Matches matches);

	void visit(BitwiseAnd bitwiseAnd);

	void visit(BitwiseOr bitwiseOr);

	void visit(BitwiseXor bitwiseXor);

	void visit(Modulo modulo);

	void visit(AnalyticExpression aexpr);

	void visit(ExtractExpression eexpr);

	void visit(IntervalExpression iexpr);

	void visit(OracleHierarchicalExpression oexpr);

	void visit(RegExpMatchOperator rexpr);
	
	void visit(JsonExpression jsonExpr);

	void visit(RegExpMySQLOperator regExpMySQLOperator);
	
	void visit(ConditionExpressionList expressionList);
	
	void visit(Condition condition);
}
