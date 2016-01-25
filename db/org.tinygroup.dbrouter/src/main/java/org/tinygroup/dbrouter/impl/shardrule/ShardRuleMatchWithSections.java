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
package org.tinygroup.dbrouter.impl.shardrule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.dbrouter.config.Partition;
import org.tinygroup.jsqlparser.expression.BinaryExpression;
import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.JdbcParameter;
import org.tinygroup.jsqlparser.expression.LongValue;
import org.tinygroup.jsqlparser.expression.operators.relational.ExpressionList;
import org.tinygroup.jsqlparser.expression.operators.relational.ItemsList;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.Statement;
import org.tinygroup.jsqlparser.statement.delete.Delete;
import org.tinygroup.jsqlparser.statement.insert.Insert;
import org.tinygroup.jsqlparser.statement.select.FromItem;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.statement.select.SelectBody;
import org.tinygroup.jsqlparser.statement.update.Update;


public class ShardRuleMatchWithSections {

    private static final String PARAMETER_SHIFT = "parameter_shift";
	protected List<Section> sections;
    protected String tableName;
    protected String fieldName;
    protected Partition partition;
    protected Object[] preparedParams;

    public ShardRuleMatchWithSections(List<Section> sections, String tableName,
                                      String fieldName, Partition partition, Object[] preparedParams) {
        super();
        this.sections = sections;
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.partition = partition;
        this.preparedParams = preparedParams;
    }

    public boolean insertMatch(Statement statement) {
        Insert insert = (Insert) statement;
        int paramIndex = 0;
        if (tableName.equalsIgnoreCase(insert.getTable().getName())) {
            ItemsList itemsList = insert.getItemsList();
            if (itemsList instanceof ExpressionList) {
                List<Expression> expressions = ((ExpressionList) itemsList)
                        .getExpressions();
                for (int i = 0; i < insert.getColumns().size(); i++) {
                    Column column = insert.getColumns().get(i);
                    Expression expression = expressions.get(i);
                    if (column.getColumnName().equalsIgnoreCase(
                            fieldName)) {
                        if (expression instanceof LongValue) {
                            LongValue longValue = (LongValue) expression;
                            if (valueMatch(longValue.getStringValue())) {
                                return true;
                            }
                        } else if (expression instanceof JdbcParameter) {
                            if (valueMatch(preparedParams[paramIndex].toString())) {
                                return true;
                            }
                        }
                    }
                    if (expression instanceof JdbcParameter) {
                        paramIndex++;
                    }
                }
            }
        }
        return false;
    }

    public boolean updateMatch(Statement statement) {
        Update update = (Update) statement;
        List<Expression> expressions = update.getExpressions();
        Map<String, Integer> shiftMap=new HashMap<String, Integer>();
        shiftMap.put(PARAMETER_SHIFT, 0);
        for (Expression expression : expressions) {
            shiftParameter(expression, shiftMap);
        }
        List<Table> tables = update.getTables();
        if (tables != null) {
            for (Table table : tables) {
                if (tableName.equalsIgnoreCase(table.getName())) {
                    return getWhereExpression(shiftMap.get(PARAMETER_SHIFT), update.getWhere(), partition, preparedParams);
                }
            }
        }
        return false;
    }
    
    private void shiftParameter(Expression expression,Map<String, Integer> shiftMap){
    	if(expression instanceof JdbcParameter){
    		int paramIndex=shiftMap.get(PARAMETER_SHIFT);
    		shiftMap.put(PARAMETER_SHIFT, ++paramIndex);
    	}else if(expression instanceof BinaryExpression){
    		BinaryExpression binaryExpression=(BinaryExpression)expression;
        	Expression rightExpression=binaryExpression.getRightExpression();
        	shiftParameter(rightExpression, shiftMap);
    	}
    }

    public boolean deleteMatch(Statement statement) {
        Delete delete = (Delete) statement;
        if (tableName.equalsIgnoreCase(delete.getTable().getName())) {
            return getWhereExpression(0, delete.getWhere(), partition, preparedParams);
        }
        return false;
    }

    public boolean selectMatch(Statement statement) {
        Select select = (Select) statement;
        SelectBody body = select.getSelectBody();
        if (body instanceof PlainSelect) {
            PlainSelect plainSelect = (PlainSelect) body;
            FromItem fromItem = plainSelect.getFromItem();
            if (fromItem instanceof Table) {
                Table table = (Table) fromItem;
                if (tableName.equalsIgnoreCase(table.getName())) {
                    return getWhereExpression(0, plainSelect.getWhere(),
                            partition, preparedParams);
                }
            }
        }
        return false;
    }

    private boolean getWhereExpression(int paramIndex, Expression where, Partition partition, Object... preparedParams) {
    	ConditionMatch conditionMatch = new ConditionMatch(paramIndex,
				fieldName, this, preparedParams);
		return conditionMatch.match(where);
    }

    protected boolean valueMatch(String paramValue) {
	    return isInScope(sections, Long.parseLong(paramValue));
	}
    
    protected boolean isInScope(List<Section> sections, long value) {
        for (Section section : sections) {
            if (section.getStart() > value) {
                return false;
            }
            if (isInScope(section, value)) {
                return true;
            }
        }
        return false;

    }

    private boolean isInScope(Section section, long value) {
        return section.getStart() <= value && value <= section.getEnd();

    }

}
