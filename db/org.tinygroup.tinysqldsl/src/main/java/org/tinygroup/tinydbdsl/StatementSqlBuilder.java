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
package org.tinygroup.tinydbdsl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tinygroup.commons.tools.Assert;
import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.StatementBody;
import org.tinygroup.tinydbdsl.expression.conditional.ConditionExpressionList;
import org.tinygroup.tinydbdsl.visitor.StatementVisitor;
import org.tinygroup.tinydbdsl.visitor.impl.StatementDeParser;

/**
 * select结构的解析器
 * 
 * @author renhui
 * 
 */
public abstract class StatementSqlBuilder {

	private List<Object> values;

	protected StringBuilder stringBuilder;

	private StatementVisitor visitor;

	private boolean parsed;
	
	public StatementSqlBuilder() {
		this(new StringBuilder(), new ArrayList<Object>());
	}

	public StatementSqlBuilder(StringBuilder stringBuilder, List<Object> values) {
		super();
		this.values = values;
		this.stringBuilder = stringBuilder;
		visitor = new StatementDeParser(stringBuilder, values);
	}

	public void build(StatementBody statementBody) {
		statementBody.accept(visitor);
	}

	public List<Object> getValues() {
		return values;
	}

	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	public String sql() {
		if (parsed) {
			return stringBuilder.toString();
		}
		parserStatementBody();
		parsed=true;
		return stringBuilder.toString();
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
		expressionList.setUseBrackets(false);
		List<Object> values = new ArrayList<Object>();
		for (Condition condition : conditions) {
			expressionList.addExpression(condition.getExpression());
			Collections.addAll(values, condition.getValues());
		}
		return new Condition(expressionList, values);
	}

	public static Condition or(Condition... conditions) {
		return conditional(" or ", conditions);
	}

}
