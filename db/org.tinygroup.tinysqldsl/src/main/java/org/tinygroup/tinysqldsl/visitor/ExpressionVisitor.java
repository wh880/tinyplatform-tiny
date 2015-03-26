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
package org.tinygroup.tinysqldsl.visitor;

import org.tinygroup.tinysqldsl.base.Column;
import org.tinygroup.tinysqldsl.base.Condition;
import org.tinygroup.tinysqldsl.expression.AllComparisonExpression;
import org.tinygroup.tinysqldsl.expression.AnalyticExpression;
import org.tinygroup.tinysqldsl.expression.AnyComparisonExpression;
import org.tinygroup.tinysqldsl.expression.CaseExpression;
import org.tinygroup.tinysqldsl.expression.DateValue;
import org.tinygroup.tinysqldsl.expression.DoubleValue;
import org.tinygroup.tinysqldsl.expression.ExtractExpression;
import org.tinygroup.tinysqldsl.expression.Function;
import org.tinygroup.tinysqldsl.expression.IntervalExpression;
import org.tinygroup.tinysqldsl.expression.JdbcNamedParameter;
import org.tinygroup.tinysqldsl.expression.JdbcParameter;
import org.tinygroup.tinysqldsl.expression.JsonExpression;
import org.tinygroup.tinysqldsl.expression.LongValue;
import org.tinygroup.tinysqldsl.expression.NullValue;
import org.tinygroup.tinysqldsl.expression.OracleHierarchicalExpression;
import org.tinygroup.tinysqldsl.expression.Parenthesis;
import org.tinygroup.tinysqldsl.expression.SignedExpression;
import org.tinygroup.tinysqldsl.expression.StringValue;
import org.tinygroup.tinysqldsl.expression.TimeValue;
import org.tinygroup.tinysqldsl.expression.TimestampValue;
import org.tinygroup.tinysqldsl.expression.WhenClause;
import org.tinygroup.tinysqldsl.expression.arithmetic.Addition;
import org.tinygroup.tinysqldsl.expression.arithmetic.BitwiseAnd;
import org.tinygroup.tinysqldsl.expression.arithmetic.BitwiseOr;
import org.tinygroup.tinysqldsl.expression.arithmetic.BitwiseXor;
import org.tinygroup.tinysqldsl.expression.arithmetic.Concat;
import org.tinygroup.tinysqldsl.expression.arithmetic.Division;
import org.tinygroup.tinysqldsl.expression.arithmetic.Modulo;
import org.tinygroup.tinysqldsl.expression.arithmetic.Multiplication;
import org.tinygroup.tinysqldsl.expression.arithmetic.Subtraction;
import org.tinygroup.tinysqldsl.expression.conditional.AndExpression;
import org.tinygroup.tinysqldsl.expression.conditional.ConditionExpressionList;
import org.tinygroup.tinysqldsl.expression.conditional.OrExpression;
import org.tinygroup.tinysqldsl.expression.relational.Between;
import org.tinygroup.tinysqldsl.expression.relational.EqualsTo;
import org.tinygroup.tinysqldsl.expression.relational.ExistsExpression;
import org.tinygroup.tinysqldsl.expression.relational.GreaterThan;
import org.tinygroup.tinysqldsl.expression.relational.GreaterThanEquals;
import org.tinygroup.tinysqldsl.expression.relational.InExpression;
import org.tinygroup.tinysqldsl.expression.relational.IsNullExpression;
import org.tinygroup.tinysqldsl.expression.relational.LikeExpression;
import org.tinygroup.tinysqldsl.expression.relational.Matches;
import org.tinygroup.tinysqldsl.expression.relational.MinorThan;
import org.tinygroup.tinysqldsl.expression.relational.MinorThanEquals;
import org.tinygroup.tinysqldsl.expression.relational.NotEqualsTo;
import org.tinygroup.tinysqldsl.expression.relational.RegExpMatchOperator;
import org.tinygroup.tinysqldsl.expression.relational.RegExpMySQLOperator;
import org.tinygroup.tinysqldsl.formitem.SubSelect;

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
