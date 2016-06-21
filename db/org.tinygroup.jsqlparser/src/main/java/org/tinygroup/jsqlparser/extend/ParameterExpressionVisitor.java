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
package org.tinygroup.jsqlparser.extend;

import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.ExpressionVisitorAdapter;
import org.tinygroup.jsqlparser.expression.JdbcParameter;
import org.tinygroup.jsqlparser.expression.operators.relational.ExpressionList;
import org.tinygroup.jsqlparser.expression.operators.relational.GreaterThan;
import org.tinygroup.jsqlparser.expression.operators.relational.GreaterThanEquals;
import org.tinygroup.jsqlparser.expression.operators.relational.MinorThan;
import org.tinygroup.jsqlparser.expression.operators.relational.MinorThanEquals;
import org.tinygroup.jsqlparser.expression.operators.relational.MultiExpressionList;
import org.tinygroup.jsqlparser.statement.select.SelectVisitor;
import org.tinygroup.jsqlparser.statement.select.SubSelect;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author renhui
 * 
 */
public class ParameterExpressionVisitor extends ExpressionVisitorAdapter {
	public static final String GREATER_THAN = "greaterThan";
	public static final String GREATER_THAN_EQUALS = "greaterThanEquals";
	public static final String MINOR_THAN_EQUALS = "minorThanEquals";
	public static final String MINOR_THAN = "minorThan";
	private Map<String, Integer> positionMap;
	private AtomicInteger paramLength;
	private SelectVisitor selectVisitor;

	public ParameterExpressionVisitor(Map<String, Integer> positionMap,
			AtomicInteger paramLength, SelectVisitor selectVisitor) {
		super();
		this.positionMap = positionMap;
		this.paramLength = paramLength;
		this.selectVisitor = selectVisitor;
	}

	@Override
	public void visit(JdbcParameter parameter) {
		paramLength.incrementAndGet();
	}

	@Override
	public void visit(SubSelect subSelect) {
		subSelect.getSelectBody().accept(selectVisitor);
	}

	@Override
	public void visit(ExpressionList expressionList) {
		List<Expression> expressions = expressionList.getExpressions();
		if (expressions != null) {
			for (Expression expression : expressions) {
				expression.accept(this);
			}
		}
	}

	@Override
	public void visit(MultiExpressionList multiExprList) {
		List<ExpressionList> expressionLists = multiExprList.getExprList();
		if (expressionLists != null) {
			for (ExpressionList expressionList : expressionLists) {
				expressionList.accept(this);
			}
		}
	}

	public void visit(GreaterThan expr) {
		int before = paramLength.intValue();
		super.visit(expr);
		int after = paramLength.intValue();
		if (before != after) {
			positionMap.put(GREATER_THAN, after);
		}

	}

	public void visit(GreaterThanEquals expr) {
		int before = paramLength.intValue();
		super.visit(expr);
		int after = paramLength.intValue();
		if (before != after) {
			positionMap.put(GREATER_THAN_EQUALS, after);
		}
	}

	public void visit(MinorThan expr) {
		int before = paramLength.intValue();
		super.visit(expr);
		int after = paramLength.intValue();
		if (before != after) {
			positionMap.put(MINOR_THAN, after);
		}
	}

	public void visit(MinorThanEquals expr) {
		int before = paramLength.intValue();
		super.visit(expr);
		int after = paramLength.intValue();
		if (before != after) {
			positionMap.put(MINOR_THAN_EQUALS, after);
		}
	}
}
