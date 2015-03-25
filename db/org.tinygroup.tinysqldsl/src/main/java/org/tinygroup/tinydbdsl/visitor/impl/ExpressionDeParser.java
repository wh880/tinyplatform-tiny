package org.tinygroup.tinydbdsl.visitor.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.expression.AllComparisonExpression;
import org.tinygroup.tinydbdsl.expression.AnalyticExpression;
import org.tinygroup.tinydbdsl.expression.AnyComparisonExpression;
import org.tinygroup.tinydbdsl.expression.BinaryExpression;
import org.tinygroup.tinydbdsl.expression.CaseExpression;
import org.tinygroup.tinydbdsl.expression.DateValue;
import org.tinygroup.tinydbdsl.expression.DoubleValue;
import org.tinygroup.tinydbdsl.expression.Expression;
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
import org.tinygroup.tinydbdsl.expression.relational.ExpressionList;
import org.tinygroup.tinydbdsl.expression.relational.GreaterThan;
import org.tinygroup.tinydbdsl.expression.relational.GreaterThanEquals;
import org.tinygroup.tinydbdsl.expression.relational.InExpression;
import org.tinygroup.tinydbdsl.expression.relational.IsNullExpression;
import org.tinygroup.tinydbdsl.expression.relational.LikeExpression;
import org.tinygroup.tinydbdsl.expression.relational.Matches;
import org.tinygroup.tinydbdsl.expression.relational.MinorThan;
import org.tinygroup.tinydbdsl.expression.relational.MinorThanEquals;
import org.tinygroup.tinydbdsl.expression.relational.MultiExpressionList;
import org.tinygroup.tinydbdsl.expression.relational.NotEqualsTo;
import org.tinygroup.tinydbdsl.expression.relational.OldOracleJoinBinaryExpression;
import org.tinygroup.tinydbdsl.expression.relational.RegExpMatchOperator;
import org.tinygroup.tinydbdsl.expression.relational.RegExpMySQLOperator;
import org.tinygroup.tinydbdsl.expression.relational.SupportsOldOracleJoinSyntax;
import org.tinygroup.tinydbdsl.formitem.SubSelect;
import org.tinygroup.tinydbdsl.visitor.ExpressionVisitor;
import org.tinygroup.tinydbdsl.visitor.ItemsListVisitor;
import org.tinygroup.tinydbdsl.visitor.SelectVisitor;

/**
 * A class to de-parse (that is, tranform from JSqlParser hierarchy into a
 * string) an {@link org.tinygroup.jsqlparser.expression.Expression}
 */
public class ExpressionDeParser implements ExpressionVisitor, ItemsListVisitor {

    private StringBuilder buffer;
    private List<Object> values=new ArrayList<Object>();
    private SelectVisitor selectVisitor;
    private boolean useBracketsInExprList = true;

    public ExpressionDeParser() {
    }

    /**
     * @param selectVisitor a SelectVisitor to de-parse SubSelects. It has to
     * share the same<br> StringBuilder as this object in order to work, as:
     *
     * <pre>
     * <code>
     * StringBuilder myBuf = new StringBuilder();
     * MySelectDeparser selectDeparser = new  MySelectDeparser();
     * selectDeparser.setBuffer(myBuf);
     * ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeparser, myBuf);
     * </code>
     * </pre>
     *
     * @param buffer the buffer that will be filled with the expression
     */
    public ExpressionDeParser(SelectVisitor selectVisitor, StringBuilder buffer,List<Object> values) {
        this.selectVisitor = selectVisitor;
        this.buffer = buffer;
        this.values=values;
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

	public boolean isUseBracketsInExprList() {
		return useBracketsInExprList;
	}

	public void setUseBracketsInExprList(boolean useBracketsInExprList) {
		this.useBracketsInExprList = useBracketsInExprList;
	}

	public void visit(Addition addition) {
        visitBinaryExpression(addition, " + ");
    }

    
    public void visit(AndExpression andExpression) {
        visitBinaryExpression(andExpression, " AND ");
    }

    
    public void visit(Between between) {
        between.getLeftExpression().accept(this);
        if (between.isNot()) {
            buffer.append(" NOT");
        }

        buffer.append(" BETWEEN ");
        between.getBetweenExpressionStart().accept(this);
        buffer.append(" AND ");
        between.getBetweenExpressionEnd().accept(this);

    }

    
    public void visit(EqualsTo equalsTo) {
        visitOldOracleJoinBinaryExpression(equalsTo, " = ");
    }

    
    public void visit(Division division) {
        visitBinaryExpression(division, " / ");

    }

    
    public void visit(DoubleValue doubleValue) {
        buffer.append(doubleValue.toString());

    }

    public void visitOldOracleJoinBinaryExpression(OldOracleJoinBinaryExpression expression, String operator) {
        if (expression.isNot()) {
            buffer.append(" NOT ");
        }
        expression.getLeftExpression().accept(this);
        if (expression.getOldOracleJoinSyntax() == EqualsTo.ORACLE_JOIN_RIGHT) {
            buffer.append("(+)");
        }
        buffer.append(operator);
        expression.getRightExpression().accept(this);
        if (expression.getOldOracleJoinSyntax() == EqualsTo.ORACLE_JOIN_LEFT) {
            buffer.append("(+)");
        }
    }

    
    public void visit(GreaterThan greaterThan) {
        visitOldOracleJoinBinaryExpression(greaterThan, " > ");
    }

    
    public void visit(GreaterThanEquals greaterThanEquals) {
        visitOldOracleJoinBinaryExpression(greaterThanEquals, " >= ");

    }

    
    public void visit(InExpression inExpression) {
        if (inExpression.getLeftExpression() == null) {
            inExpression.getLeftItemsList().accept(this);
        } else {
            inExpression.getLeftExpression().accept(this);
            if (inExpression.getOldOracleJoinSyntax() == SupportsOldOracleJoinSyntax.ORACLE_JOIN_RIGHT) {
                buffer.append("(+)");
            }
        }
        if (inExpression.isNot()) {
            buffer.append(" NOT");
        }
        buffer.append(" IN ");

        inExpression.getRightItemsList().accept(this);
    }

    
    public void visit(SignedExpression signedExpression) {
        buffer.append(signedExpression.getSign());
        signedExpression.getExpression().accept(this);
    }

    
    public void visit(IsNullExpression isNullExpression) {
        isNullExpression.getLeftExpression().accept(this);
        if (isNullExpression.isNot()) {
            buffer.append(" IS NOT NULL");
        } else {
            buffer.append(" IS NULL");
        }
    }

    
    public void visit(JdbcParameter jdbcParameter) {
        buffer.append("?");
    }

    
    public void visit(LikeExpression likeExpression) {
        visitBinaryExpression(likeExpression, " LIKE ");
        String escape = likeExpression.getEscape();
        if (escape != null) {
            buffer.append(" ESCAPE '").append(escape).append('\'');
        }
    }

    
    public void visit(ExistsExpression existsExpression) {
        if (existsExpression.isNot()) {
            buffer.append("NOT EXISTS ");
        } else {
            buffer.append("EXISTS ");
        }
        existsExpression.getRightExpression().accept(this);
    }

    
    public void visit(LongValue longValue) {
        buffer.append(longValue.getStringValue());

    }

    
    public void visit(MinorThan minorThan) {
        visitOldOracleJoinBinaryExpression(minorThan, " < ");

    }

    
    public void visit(MinorThanEquals minorThanEquals) {
        visitOldOracleJoinBinaryExpression(minorThanEquals, " <= ");

    }

    
    public void visit(Multiplication multiplication) {
        visitBinaryExpression(multiplication, " * ");

    }

    
    public void visit(NotEqualsTo notEqualsTo) {
        visitOldOracleJoinBinaryExpression(notEqualsTo, " " + notEqualsTo.getStringExpression() + " ");

    }

    
    public void visit(NullValue nullValue) {
        buffer.append("NULL");

    }

    
    public void visit(OrExpression orExpression) {
        visitBinaryExpression(orExpression, " OR ");

    }

    
    public void visit(Parenthesis parenthesis) {
        if (parenthesis.isNot()) {
            buffer.append(" NOT ");
        }

        buffer.append("(");
        parenthesis.getExpression().accept(this);
        buffer.append(")");

    }

    
    public void visit(StringValue stringValue) {
        buffer.append("'").append(stringValue.getValue()).append("'");

    }

    
    public void visit(Subtraction subtraction) {
        visitBinaryExpression(subtraction, " - ");

    }

    private void visitBinaryExpression(BinaryExpression binaryExpression, String operator) {
        if (binaryExpression.isNot()) {
            buffer.append(" NOT ");
        }
        binaryExpression.getLeftExpression().accept(this);
        buffer.append(operator);
        binaryExpression.getRightExpression().accept(this);

    }

    
    public void visit(SubSelect subSelect) {
        buffer.append("(");
        subSelect.getSelectBody().accept(selectVisitor);
        buffer.append(")");
    }

    
    public void visit(Column tableColumn) {
        final Table table = tableColumn.getTable();
        String tableName = null;
        if (table != null) {
            if (table.getAlias() != null) {
                tableName = table.getAlias().getName();
            } else {
                tableName = table.getFullyQualifiedName();
            }
        }
        if (tableName != null && !(tableName == null || tableName.length() == 0)) {
            buffer.append(tableName).append(".");
        }

        buffer.append(tableColumn.getColumnName());
    }

    
    public void visit(Function function) {
        if (function.isEscaped()) {
            buffer.append("{fn ");
        }

        buffer.append(function.getName());
        if (function.isAllColumns() && function.getParameters() == null) {
            buffer.append("(*)");
        } else if (function.getParameters() == null) {
            buffer.append("()");
        } else {
            boolean oldUseBracketsInExprList = useBracketsInExprList;
            if (function.isDistinct()) {
                useBracketsInExprList = false;
                buffer.append("(DISTINCT ");
            } else if (function.isAllColumns()) {
                useBracketsInExprList = false;
                buffer.append("(ALL ");
            }
            visit(function.getParameters());
            useBracketsInExprList = oldUseBracketsInExprList;
            if (function.isDistinct() || function.isAllColumns()) {
                buffer.append(")");
            }
        }

        if (function.isEscaped()) {
            buffer.append("}");
        }
    }

    
    public void visit(ExpressionList expressionList) {
        if (useBracketsInExprList) {
            buffer.append("(");
        }
        for (Iterator<Expression> iter = expressionList.getExpressions().iterator(); iter.hasNext();) {
            Expression expression = iter.next();
            expression.accept(this);
            if (iter.hasNext()) {
                buffer.append(", ");
            }
        }
        if (useBracketsInExprList) {
            buffer.append(")");
        }
    }

    public SelectVisitor getSelectVisitor() {
        return selectVisitor;
    }

    public void setSelectVisitor(SelectVisitor visitor) {
        selectVisitor = visitor;
    }

    
    public void visit(DateValue dateValue) {
        buffer.append("{d '").append(dateValue.getValue().toString()).append("'}");
    }

    
    public void visit(TimestampValue timestampValue) {
        buffer.append("{ts '").append(timestampValue.getValue().toString()).append("'}");
    }

    
    public void visit(TimeValue timeValue) {
        buffer.append("{t '").append(timeValue.getValue().toString()).append("'}");
    }

    
    public void visit(CaseExpression caseExpression) {
        buffer.append("CASE ");
        Expression switchExp = caseExpression.getSwitchExpression();
        if (switchExp != null) {
            switchExp.accept(this);
            buffer.append(" ");
        }

        for (Expression exp : caseExpression.getWhenClauses()) {
            exp.accept(this);
        }

        Expression elseExp = caseExpression.getElseExpression();
        if (elseExp != null) {
            buffer.append("ELSE ");
            elseExp.accept(this);
            buffer.append(" ");
        }

        buffer.append("END");
    }

    
    public void visit(WhenClause whenClause) {
        buffer.append("WHEN ");
        whenClause.getWhenExpression().accept(this);
        buffer.append(" THEN ");
        whenClause.getThenExpression().accept(this);
        buffer.append(" ");
    }

    
    public void visit(AllComparisonExpression allComparisonExpression) {
        buffer.append(" ALL ");
        allComparisonExpression.getSubSelect().accept((ExpressionVisitor) this);
    }

    
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        buffer.append(" ANY ");
        anyComparisonExpression.getSubSelect().accept((ExpressionVisitor) this);
    }

    
    public void visit(Concat concat) {
        visitBinaryExpression(concat, " || ");
    }

    
    public void visit(Matches matches) {
        visitOldOracleJoinBinaryExpression(matches, " @@ ");
    }

    
    public void visit(BitwiseAnd bitwiseAnd) {
        visitBinaryExpression(bitwiseAnd, " & ");
    }

    
    public void visit(BitwiseOr bitwiseOr) {
        visitBinaryExpression(bitwiseOr, " | ");
    }

    
    public void visit(BitwiseXor bitwiseXor) {
        visitBinaryExpression(bitwiseXor, " ^ ");
    }

    
    public void visit(Modulo modulo) {
        visitBinaryExpression(modulo, " % ");
    }

    
    public void visit(AnalyticExpression aexpr) {
        buffer.append(aexpr.toString());
    }

    
    public void visit(ExtractExpression eexpr) {
        buffer.append(eexpr.toString());
    }

    
    public void visit(MultiExpressionList multiExprList) {
        for (Iterator<ExpressionList> it = multiExprList.getExprList().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                buffer.append(", ");
            }
        }
    }

    
    public void visit(IntervalExpression iexpr) {
        buffer.append(iexpr.toString());
    }

    
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
        buffer.append(jdbcNamedParameter.toString());
    }

    
    public void visit(OracleHierarchicalExpression oexpr) {
        buffer.append(oexpr.toString());
    }

    
    public void visit(RegExpMatchOperator rexpr) {
        visitBinaryExpression(rexpr, " " + rexpr.getStringExpression() + " ");
    }

    
	public void visit(RegExpMySQLOperator rexpr) {
    	visitBinaryExpression(rexpr, " " + rexpr.getStringExpression() + " ");
	}
    
    
    public void visit(JsonExpression jsonExpr) {
        buffer.append(jsonExpr.toString());
    }

	public void visit(Condition condition) {
		Expression expression=condition.getExpression();
		expression.accept(this);
		Collections.addAll(values, condition.getValues());
	}

	public void visit(ConditionExpressionList expressionList) {
		buffer.append(expressionList);
	}

	
}
