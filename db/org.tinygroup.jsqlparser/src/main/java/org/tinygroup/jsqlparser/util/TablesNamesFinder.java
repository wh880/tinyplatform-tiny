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
package org.tinygroup.jsqlparser.util;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.jsqlparser.expression.AllComparisonExpression;
import org.tinygroup.jsqlparser.expression.AnalyticExpression;
import org.tinygroup.jsqlparser.expression.AnyComparisonExpression;
import org.tinygroup.jsqlparser.expression.BinaryExpression;
import org.tinygroup.jsqlparser.expression.CaseExpression;
import org.tinygroup.jsqlparser.expression.CastExpression;
import org.tinygroup.jsqlparser.expression.DateValue;
import org.tinygroup.jsqlparser.expression.DoubleValue;
import org.tinygroup.jsqlparser.expression.Expression;
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
import org.tinygroup.jsqlparser.statement.Statement;
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
import org.tinygroup.jsqlparser.statement.select.Join;
import org.tinygroup.jsqlparser.statement.select.LateralSubSelect;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.statement.select.SelectExpressionItem;
import org.tinygroup.jsqlparser.statement.select.SelectItem;
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
 * Find all used tables within an select statement.
 */
public class TablesNamesFinder implements StatementVisitor,SelectVisitor, FromItemVisitor, ExpressionVisitor, ItemsListVisitor, SelectItemVisitor {

    private static final String TABLE_TYPE = "table";
	private List<String> tables;
    /**
     * There are special names, that are not table names but are parsed as
     * tables. These names are collected here and are not included in the tables
     * - names anymore.
     */
    private List<String> otherItemNames;

    /**
     * Main entry for this Tool class. A list of found tables is returned.
     *
     * @param delete
     * @return
     */
    public List<String> getTableList(Delete delete) {
        init();
        tables.add(delete.getTable().getName());
        if (delete.getWhere() != null) {
            delete.getWhere().accept(this);
        }

        return tables;
    }

    /**
     * Main entry for this Tool class. A list of found tables is returned.
     *
     * @param insert
     * @return
     */
    public List<String> getTableList(Insert insert) {
        init();
        tables.add(insert.getTable().getName());
        if (insert.getItemsList() != null) {
            insert.getItemsList().accept(this);
        }

        return tables;
    }

    /**
     * Main entry for this Tool class. A list of found tables is returned.
     *
     * @param replace
     * @return
     */
    public List<String> getTableList(Replace replace) {
        init();
        tables.add(replace.getTable().getName());
        if (replace.getExpressions() != null) {
            for (Expression expression : replace.getExpressions()) {
                expression.accept(this);
            }
        }
        if (replace.getItemsList() != null) {
            replace.getItemsList().accept(this);
        }

        return tables;
    }

    /**
     * Main entry for this Tool class. A list of found tables is returned.
     *
     * @param select
     * @return
     */
    public List<String> getTableList(Select select) {
        init();
        if (select.getWithItemsList() != null) {
            for (WithItem withItem : select.getWithItemsList()) {
                withItem.accept(this);
            }
        }
        select.getSelectBody().accept(this);

        return tables;
    }

    /**
     * Main entry for this Tool class. A list of found tables is returned.
     *
     * @param update
     * @return
     */
    public List<String> getTableList(Update update) {
        init();
        for (Table table : update.getTables()) {
            tables.add(table.getName());
        }
        if (update.getExpressions() != null) {
            for (Expression expression : update.getExpressions()) {
                expression.accept(this);
            }
        }

        if (update.getFromItem() != null) {
            update.getFromItem().accept(this);
        }

        if (update.getJoins() != null) {
            for (Join join : update.getJoins()) {
                join.getRightItem().accept(this);
            }
        }

        if (update.getWhere() != null) {
            update.getWhere().accept(this);
        }

        return tables;
    }

    
    public void visit(WithItem withItem) {
        otherItemNames.add(withItem.getName().toLowerCase());
        withItem.getSelectBody().accept(this);
    }

    
    public void visit(PlainSelect plainSelect) {
        if (plainSelect.getSelectItems() != null) {
            for (SelectItem item : plainSelect.getSelectItems()) {
                item.accept(this);
            }
        }

        plainSelect.getFromItem().accept(this);

        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                join.getRightItem().accept(this);
            }
        }
        if (plainSelect.getWhere() != null) {
            plainSelect.getWhere().accept(this);
        }
        if (plainSelect.getOracleHierarchical() != null) {
            plainSelect.getOracleHierarchical().accept(this);
        }
    }

    
    public void visit(Table tableName) {
        String tableWholeName = tableName.getFullyQualifiedName();
        if (!otherItemNames.contains(tableWholeName.toLowerCase())
                && !tables.contains(tableWholeName)) {
            tables.add(tableWholeName);
        }
    }

    
    public void visit(SubSelect subSelect) {
        subSelect.getSelectBody().accept(this);
    }

    
    public void visit(Addition addition) {
        visitBinaryExpression(addition);
    }

    
    public void visit(AndExpression andExpression) {
        visitBinaryExpression(andExpression);
    }

    
    public void visit(Between between) {
        between.getLeftExpression().accept(this);
        between.getBetweenExpressionStart().accept(this);
        between.getBetweenExpressionEnd().accept(this);
    }

    
    public void visit(Column tableColumn) {
    }

    
    public void visit(Division division) {
        visitBinaryExpression(division);
    }

    
    public void visit(DoubleValue doubleValue) {
    }

    
    public void visit(EqualsTo equalsTo) {
        visitBinaryExpression(equalsTo);
    }

    
    public void visit(Function function) {
    }

    
    public void visit(GreaterThan greaterThan) {
        visitBinaryExpression(greaterThan);
    }

    
    public void visit(GreaterThanEquals greaterThanEquals) {
        visitBinaryExpression(greaterThanEquals);
    }

    
    public void visit(InExpression inExpression) {
        inExpression.getLeftExpression().accept(this);
        inExpression.getRightItemsList().accept(this);
    }

    
    public void visit(SignedExpression signedExpression) {
        signedExpression.getExpression().accept(this);
    }

    
    public void visit(IsNullExpression isNullExpression) {
    }

    
    public void visit(JdbcParameter jdbcParameter) {
    }

    
    public void visit(LikeExpression likeExpression) {
        visitBinaryExpression(likeExpression);
    }

    
    public void visit(ExistsExpression existsExpression) {
        existsExpression.getRightExpression().accept(this);
    }

    
    public void visit(LongValue longValue) {
    }

    
    public void visit(MinorThan minorThan) {
        visitBinaryExpression(minorThan);
    }

    
    public void visit(MinorThanEquals minorThanEquals) {
        visitBinaryExpression(minorThanEquals);
    }

    
    public void visit(Multiplication multiplication) {
        visitBinaryExpression(multiplication);
    }

    
    public void visit(NotEqualsTo notEqualsTo) {
        visitBinaryExpression(notEqualsTo);
    }

    
    public void visit(NullValue nullValue) {
    }

    
    public void visit(OrExpression orExpression) {
        visitBinaryExpression(orExpression);
    }

    
    public void visit(Parenthesis parenthesis) {
        parenthesis.getExpression().accept(this);
    }

    
    public void visit(StringValue stringValue) {
    }

    
    public void visit(Subtraction subtraction) {
        visitBinaryExpression(subtraction);
    }

    public void visitBinaryExpression(BinaryExpression binaryExpression) {
        binaryExpression.getLeftExpression().accept(this);
        binaryExpression.getRightExpression().accept(this);
    }

    
    public void visit(ExpressionList expressionList) {
        for (Expression expression : expressionList.getExpressions()) {
            expression.accept(this);
        }

    }

    
    public void visit(DateValue dateValue) {
    }

    
    public void visit(TimestampValue timestampValue) {
    }

    
    public void visit(TimeValue timeValue) {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.tinygroup.jsqlparser.expression.ExpressionVisitor#visit(org.tinygroup.jsqlparser.expression.CaseExpression)
     */
    
    public void visit(CaseExpression caseExpression) {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.tinygroup.jsqlparser.expression.ExpressionVisitor#visit(org.tinygroup.jsqlparser.expression.WhenClause)
     */
    
    public void visit(WhenClause whenClause) {
    }

    
    public void visit(AllComparisonExpression allComparisonExpression) {
        allComparisonExpression.getSubSelect().getSelectBody().accept(this);
    }

    
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        anyComparisonExpression.getSubSelect().getSelectBody().accept(this);
    }

    
    public void visit(SubJoin subjoin) {
        subjoin.getLeft().accept(this);
        subjoin.getJoin().getRightItem().accept(this);
    }

    
    public void visit(Concat concat) {
        visitBinaryExpression(concat);
    }

    
    public void visit(Matches matches) {
        visitBinaryExpression(matches);
    }

    
    public void visit(BitwiseAnd bitwiseAnd) {
        visitBinaryExpression(bitwiseAnd);
    }

    
    public void visit(BitwiseOr bitwiseOr) {
        visitBinaryExpression(bitwiseOr);
    }

    
    public void visit(BitwiseXor bitwiseXor) {
        visitBinaryExpression(bitwiseXor);
    }

    
    public void visit(CastExpression cast) {
        cast.getLeftExpression().accept(this);
    }

    
    public void visit(Modulo modulo) {
        visitBinaryExpression(modulo);
    }

    
    public void visit(AnalyticExpression analytic) {
    }

    
    public void visit(SetOperationList list) {
        for (PlainSelect plainSelect : list.getPlainSelects()) {
            visit(plainSelect);
        }
    }

    
    public void visit(ExtractExpression eexpr) {
    }

    
    public void visit(LateralSubSelect lateralSubSelect) {
        lateralSubSelect.getSubSelect().getSelectBody().accept(this);
    }

    
    public void visit(MultiExpressionList multiExprList) {
        for (ExpressionList exprList : multiExprList.getExprList()) {
            exprList.accept(this);
        }
    }

    
    public void visit(ValuesList valuesList) {
    }

    private void init() {
        otherItemNames = new ArrayList<String>();
        tables = new ArrayList<String>();
    }

    
    public void visit(IntervalExpression iexpr) {
    }

    
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
    }

    
    public void visit(OracleHierarchicalExpression oexpr) {
        if (oexpr.getStartExpression() != null) {
            oexpr.getStartExpression().accept(this);
        }

        if (oexpr.getConnectExpression() != null) {
            oexpr.getConnectExpression().accept(this);
        }
    }

    
    public void visit(RegExpMatchOperator rexpr) {
        visitBinaryExpression(rexpr);
    }

    
    public void visit(RegExpMySQLOperator rexpr) {
        visitBinaryExpression(rexpr);
    }

    
    public void visit(JsonExpression jsonExpr) {
    }

    
    public void visit(AllColumns allColumns) {
    }

    
    public void visit(AllTableColumns allTableColumns) {
    }

    
    public void visit(SelectExpressionItem item) {
        item.getExpression().accept(this);
    }

	public void visit(Select select) {
         getTableList(select);		
	}

	public void visit(Delete delete) {
		getTableList(delete);
	}

	public void visit(Update update) {
		getTableList(update);
	}

	public void visit(Insert insert) {
		getTableList(insert);
	}

	public void visit(Replace replace) {
		getTableList(replace);
	}

	public void visit(Drop drop) {
		  init();	
		  String type=drop.getType();
		  if(TABLE_TYPE.equalsIgnoreCase(type)){
			  tables.add(drop.getName());
		  }
	}

	public void visit(Truncate truncate) {
	     init();	
	     tables.add(truncate.getTable().getName());
	}

	public void visit(CreateIndex createIndex) {
		 init();	
		 tables.add(createIndex.getTable().getName());
	}

	public void visit(CreateTable createTable) {
		init();
		tables.add(createTable.getTable().getName());
		if(createTable.getSelect()!=null){
			createTable.getSelect().accept(this);
		}
	}

	public void visit(CreateView createView) {
		init();
		tables.add(createView.getView().getName());
		if(createView.getSelectBody()!=null){
			createView.getSelectBody().accept(this);
		}
		
	}

	public void visit(Alter alter) {
		init();
		tables.add(alter.getTable().getName());
	}

	public void visit(Statements stmts) {
		init();
		List<Statement> statements=stmts.getStatements();
		for (Statement statement : statements) {
		       statement.accept(this);	
		}
	}

	public void visit(Execute execute) {
		init();
		ExpressionList exprList=execute.getExprList();
		for (Expression expression : exprList.getExpressions()) {
			expression.accept(this);
		}
	}
	
	public boolean existTable(String tableName){
		if(tables!=null&&tables.size()>0){
			for (String table : tables) {
				if(table.equalsIgnoreCase(tableName)){
					return true;
				}
			}
		}
		return false;
	}
}
