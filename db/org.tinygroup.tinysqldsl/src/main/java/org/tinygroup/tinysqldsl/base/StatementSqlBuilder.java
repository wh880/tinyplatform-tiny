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
package org.tinygroup.tinysqldsl.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.tinygroup.commons.tools.Assert;
import org.tinygroup.tinysqldsl.expression.BinaryExpression;
import org.tinygroup.tinysqldsl.expression.conditional.ConditionExpressionList;
import org.tinygroup.tinysqldsl.expression.relational.EqualsTo;
import org.tinygroup.tinysqldsl.expression.relational.OldOracleJoinBinaryExpression;
import org.tinygroup.tinysqldsl.formitem.FromItem;
import org.tinygroup.tinysqldsl.select.Fetch;
import org.tinygroup.tinysqldsl.select.Join;
import org.tinygroup.tinysqldsl.select.Limit;
import org.tinygroup.tinysqldsl.select.Offset;
import org.tinygroup.tinysqldsl.select.OrderByElement;
import org.tinygroup.tinysqldsl.select.OrderByElement.NullOrdering;
import org.tinygroup.tinysqldsl.util.NamedParameterUtils;

/**
 * select结构的解析器
 * 
 * @author renhui
 * 
 */
public abstract class StatementSqlBuilder {

	/**
	 * 存储参数值
	 */
	private List<Object> values;
	
	private List<String> namedList=new ArrayList<String>();
	/**
	 * 生成的sql语句
	 */
	protected StringBuilder stringBuilder;
	
	private transient boolean hasBuild;
	
	private boolean isNamedSql=false;
	/**
	 * 表达式之间是否用括号
	 */
	private boolean useBracketsInExprList = true;

	public StatementSqlBuilder() {
		this(new StringBuilder(), new ArrayList<Object>());
	}

	public StatementSqlBuilder(StringBuilder stringBuilder, List<Object> values) {
		super();
		this.values = values;
		this.stringBuilder = stringBuilder;
	}

	public void build(StatementBody statementBody) {
		statementBody.builderStatement(this);
	}

	public void appendSql(String segment) {
		stringBuilder.append(segment);
	}

	public void addParamValue(Object... values) {
		Collections.addAll(this.values, values);
	}
	
	public void addParamName(String name){
		isNamedSql=true;//认为sql中存在命名参数
		 namedList.add(name);
	}
	
	
	public Map<String, Object> mapValue(){
		 Assert.assertTrue(namedList.size()==values.size(),"命名参数名称列表与值列表长度不一样");
		 Map<String, Object> mapValues=new HashMap<String, Object>();
         for (int i = 0; i < namedList.size(); i++) {
			mapValues.put(namedList.get(i), values.get(i));
		}
         return mapValues;
	}

	public List<Object> getValues() {
		return values;
	}

	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}
	
	
	public boolean isNamedSql() {
		return isNamedSql;
	}

	public List<String> getNamedList() {
		return namedList;
	}

	public String sql() {
		if (hasBuild) {
			return stringBuilder.toString();
		}
		parserStatementBody();
		hasBuild = true;
		return stringBuilder.toString();
	}
	
	public String parsedSql(){
		String parsedSql=sql();
		if(isNamedSql){
			Map<String, Object> mapValue=mapValue();
			parsedSql=NamedParameterUtils.substituteNamedParameters(parsedSql,mapValue);	
		}
		return parsedSql;
	}
	
	public String newSql(){
		reset();
		return parsedSql();
	}
	
	public void reset(){
		hasBuild=false;
		isNamedSql=false;
		values.clear();
		namedList.clear();
		stringBuilder=new StringBuilder();
	}

	protected abstract void parserStatementBody();
	
	public static Condition and(Condition... conditions) {
		return conditional(" and ", conditions);
	}

	private static Condition conditional(String comma, Condition... conditions) {
		Assert.assertNotNull(conditions, "conditions must not null");
		Assert.assertTrue(conditions.length >= 2, "conditions 长度必须大于等于2");
		ConditionExpressionList expressionList = new ConditionExpressionList();
		expressionList.setComma(comma);
		expressionList.setUseBrackets(true);
		for (Condition condition : conditions) {
			if (condition != null) {
				expressionList.addExpression(condition);
			}
		}
		if(expressionList.getExpressions().isEmpty()){//conditions中条件对象都为空，则返回null
			return null;
		}
		return new Condition(expressionList);
	}

	public static Condition or(Condition... conditions) {
		return conditional(" or ", conditions);
	}

	public void visitBinaryExpression(BinaryExpression binaryExpression,
			String operator) {
		if (binaryExpression.isNot()) {
			stringBuilder.append(" NOT ");
		}
		binaryExpression.getLeftExpression().builderExpression(this);
		stringBuilder.append(operator);
		binaryExpression.getRightExpression().builderExpression(this);
	}

	public void visitOldOracleJoinBinaryExpression(
			OldOracleJoinBinaryExpression expression, String operator) {
		if (expression.isNot()) {
			stringBuilder.append(" NOT ");
		}
		expression.getLeftExpression().builderExpression(this);
		if (expression.getOldOracleJoinSyntax() == EqualsTo.ORACLE_JOIN_RIGHT) {
			stringBuilder.append("(+)");
		}
		stringBuilder.append(operator);
		expression.getRightExpression().builderExpression(this);
		if (expression.getOldOracleJoinSyntax() == EqualsTo.ORACLE_JOIN_LEFT) {
			stringBuilder.append("(+)");
		}
	}

	public void deparseJoin(Join join) {
		if (join.isSimple()) {
			stringBuilder.append(",");
		} else {
			if (join.isRight()) {
				stringBuilder.append(" RIGHT");
			} else if (join.isNatural()) {
				stringBuilder.append(" NATURAL");
			} else if (join.isFull()) {
				stringBuilder.append(" FULL");
			} else if (join.isLeft()) {
				stringBuilder.append(" LEFT");
			} else if (join.isCross()) {
				stringBuilder.append(" CROSS");
			}
			if (join.isOuter()) {
				stringBuilder.append(" OUTER");
			}
			stringBuilder.append(" JOIN ");
		}

		FromItem fromItem = join.getRightItem();
		fromItem.builderFromItem(this);
		if (join.getOnExpression() != null) {
			stringBuilder.append(" ON ");
			join.getOnExpression().builderExpression(this);
		}
		if (join.getUsingColumns() != null) {
			stringBuilder.append(" USING (");
			for (Iterator<Column> iterator = join.getUsingColumns().iterator(); iterator
					.hasNext();) {
				Column column = iterator.next();
				stringBuilder.append(column.getFullyQualifiedName());
				if (iterator.hasNext()) {
					stringBuilder.append(",");
				}
			}
			stringBuilder.append(")");
		}

	}

	public void deparseOrderBy(List<OrderByElement> orderByElements) {
		deparseOrderBy(false, orderByElements);
	}

	public void deparseOrderBy(boolean oracleSiblings,
			List<OrderByElement> orderByElements) {
		if (oracleSiblings) {
			stringBuilder.append(" ORDER SIBLINGS BY ");
		} else {
			stringBuilder.append(" ORDER BY ");
		}
		for (Iterator<OrderByElement> iter = orderByElements.iterator(); iter
				.hasNext();) {
			OrderByElement orderByElement = iter.next();
			orderByElement.getExpression().builderExpression(this);
			if (!orderByElement.isAsc()) {
				stringBuilder.append(" DESC");
	        }else if(orderByElement.isAscDescPresent()){
	        	stringBuilder.append(" ASC");
	        }

	        if (orderByElement.getNullOrdering() != null) {
	        	stringBuilder.append(' ');
	        	stringBuilder.append(orderByElement.getNullOrdering() == NullOrdering.NULLS_FIRST ? "NULLS FIRST" : "NULLS LAST");
	        }
			if (iter.hasNext()) {
				stringBuilder.append(",");
			}
		}
	}

	public void deparseLimit(Limit limit) {
		// LIMIT n OFFSET skip
		if (limit.isRowCountJdbcParameter()) {
			stringBuilder.append(" LIMIT ");
			stringBuilder.append("?");
			values.add(limit.getRowCount());
		} else if (limit.getRowCount() >= 0) {
			stringBuilder.append(" LIMIT ");
			stringBuilder.append(limit.getRowCount());
		} else if (limit.isLimitNull()) {
			stringBuilder.append(" LIMIT NULL");
		}

		if (limit.isOffsetJdbcParameter()) {
			stringBuilder.append(" OFFSET ?");
			values.add(limit.getOffset());
		} else if (limit.getOffset() != 0) {
			stringBuilder.append(" OFFSET ").append(limit.getOffset());
		}

	}

	public void deparseOffset(Offset offset) {
		// OFFSET offset
		// or OFFSET offset (ROW | ROWS)
		if (offset.getOffset() >= 0) {
			if (offset.isOffsetJdbcParameter()) {
				stringBuilder.append(" OFFSET ?");
				values.add(offset.getOffset());
			} else {
				stringBuilder.append(" OFFSET ");
				stringBuilder.append(offset.getOffset());
			}
			if (offset.getOffsetParam() != null) {
				stringBuilder.append(" ").append(offset.getOffsetParam());
			}
		}

	}

	public void deparseFetch(Fetch fetch) {
		// FETCH (FIRST | NEXT) row_count (ROW | ROWS) ONLY
		stringBuilder.append(" FETCH ");
		if (fetch.isFetchParamFirst()) {
			stringBuilder.append("FIRST ");
		} else {
			stringBuilder.append("NEXT ");
		}
		if (fetch.isFetchJdbcParameter()) {
			stringBuilder.append("?");
			values.add(fetch.getRowCount());
		} else {
			stringBuilder.append(fetch.getRowCount());
		}
		stringBuilder.append(" ").append(fetch.getFetchParam()).append(" ONLY");

	}
	
	/**
	 * Render the <tt>rownumber() over ( .... ) as rownumber_,</tt> 
	 * bit, that goes in the select list
	 */
	private String getRowNumber(String sql) {
		StringBuilder rownumber = new StringBuilder(50)
			.append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if ( orderByIndex>0 && !hasDistinct(sql) ) {
			rownumber.append( sql.substring(orderByIndex) );
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}

	public StringBuilder getLimitString(String sql,int start,int limit) {
		int startOfSelect = sql.toLowerCase().indexOf("select");
		StringBuilder pagingSelect = new StringBuilder( sql.length()+100 )
					.append( sql.substring(0, startOfSelect) ) //add the comment
					.append("select * from ( select ") //nest the main query in an outer select
					.append( getRowNumber(sql) ); //add the rownnumber bit into the outer query select list

		if ( hasDistinct(sql) ) {
			pagingSelect.append(" row_.* from ( ") //add another (inner) nested select
				.append( sql.substring(startOfSelect) ) //add the main query
				.append(" ) as row_"); //close off the inner nested select
		}
		else {
			pagingSelect.append( sql.substring( startOfSelect + 6 ) ); //add the main query
		}
		pagingSelect.append(" ) as temp_ where rownumber_ ");
		//add the restriction to the outer select
		if (start>0) {
			pagingSelect.append("between ").append(start+1).append(" and ").append(start+limit);
		}
		else {
			pagingSelect.append("<=").append(limit);
		}

		return pagingSelect;
	}

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct")>=0;
	}

	public boolean isUseBracketsInExprList() {
		return useBracketsInExprList;
	}

	public void setUseBracketsInExprList(boolean useBracketsInExprList) {
		this.useBracketsInExprList = useBracketsInExprList;
	}

}
