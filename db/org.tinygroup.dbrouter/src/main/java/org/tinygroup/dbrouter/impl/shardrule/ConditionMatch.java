package org.tinygroup.dbrouter.impl.shardrule;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.jsqlparser.expression.BinaryExpression;
import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.JdbcParameter;
import org.tinygroup.jsqlparser.expression.LongValue;
import org.tinygroup.jsqlparser.expression.operators.conditional.AndExpression;
import org.tinygroup.jsqlparser.expression.operators.conditional.OrExpression;
import org.tinygroup.jsqlparser.expression.operators.relational.EqualsTo;
import org.tinygroup.jsqlparser.schema.Column;

/**
 * 条件匹配类
 * 
 * @author renhui
 *
 */
public class ConditionMatch {

	private int paramIndex;

	private Object[] preparedParams;

	private ShardRuleMatchWithSections matchWithSections;

	private String fieldName;

	public ConditionMatch(int paramIndex, String fieldName,
			ShardRuleMatchWithSections sectionAndHash, Object[] preparedParams) {
		super();
		this.paramIndex = paramIndex;
		this.preparedParams = preparedParams;
		this.fieldName = fieldName;
		this.matchWithSections = sectionAndHash;
	}

	public boolean match(Expression where) {
		if (where == null) {// 如果没有条件字段 直接返回true
			return true;
		}
		List<String> values = new ArrayList<String>();
		dealMatchFieldValue(where, values);
		for (String value : values) {
			boolean match = matchWithSections.valueMatch(value);
			if (match) {
				return match;
			}
		}
		return false;
	}

	private void dealMatchFieldValue(Expression where, List<String> values) {
		if (where instanceof EqualsTo) {
			EqualsTo equalsTo = (EqualsTo) where;
			Expression leftExpression = equalsTo.getLeftExpression();
			Expression rightExpression = equalsTo.getRightExpression();
			if (leftExpression instanceof Column) {
				Column column = (Column) leftExpression;
				if (column.getColumnName().equalsIgnoreCase(fieldName)) {
					if (rightExpression instanceof LongValue) {
						LongValue longValue = (LongValue) rightExpression;
						values.add(longValue.getStringValue());
					} else if (rightExpression instanceof JdbcParameter) {
						values.add(preparedParams[paramIndex].toString());
					}
				} else {// 条件关联的字段与分片字段不匹配，如果其条件值是JdbcParameter,则参数位数移动一位
					if (rightExpression instanceof JdbcParameter) {
						paramIndex++;
					}
				}
			}
		} else if (where instanceof AndExpression
				|| where instanceof OrExpression) {
			BinaryExpression binaryExpression = (BinaryExpression) where;
			Expression leftExpression = binaryExpression.getLeftExpression();
			dealMatchFieldValue(leftExpression, values);
			Expression rightExpression = binaryExpression.getRightExpression();
			dealMatchFieldValue(rightExpression, values);
		}
	}

}
