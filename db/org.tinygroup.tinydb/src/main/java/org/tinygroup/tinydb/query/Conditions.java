package org.tinygroup.tinydb.query;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.commons.tools.ObjectUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.tinydb.BeanDbNameConverter;
import org.tinygroup.tinydb.impl.DefaultNameConverter;

public class Conditions {

	private List<Condition> conditionList = new ArrayList<Condition>();

	private BeanDbNameConverter beanDbNameConverter = new DefaultNameConverter();

	public Conditions condition(String name, String operator, Object value) {
		return append(name, operator, value);
	}

	public Conditions and() {
		return append("and");
	}

	public Conditions or() {
		return append("or");
	}

	public Conditions left() {
		return append("(");
	}

	public Conditions right() {
		return append(")");
	}

	public List<Object> getParamterList() {
		List<Object> params = new ArrayList<Object>();
		for (Condition condition : conditionList) {
			Object value = condition.getValue();
			if (!ObjectUtil.isEmptyObject(value)) {
				params.add(condition.getValue());
			}
		}
		return params;
	}

	public String getSegmentWithVariable() {
		StringBuilder builder = new StringBuilder();
		for (Condition condition : conditionList) {
			String name = condition.getName();
			String operator = condition.getOperator();
			Object value = condition.getValue();
			if (!StringUtil.isBlank(name)) {
				builder.append(beanDbNameConverter
						.propertyNameToDbFieldName(name));
			}
			if (!StringUtil.isBlank(operator)) {
				builder.append(" ").append(operator.trim()).append(" ");
			}
			if (!ObjectUtil.isEmptyObject(value)) {
				builder.append("?");
			}
		}
		return builder.toString().replaceAll("\\s{2}+", " ").trim();
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Condition condition : conditionList) {
			String name = condition.getName();
			String operator = condition.getOperator();
			Object value = condition.getValue();
			if (!StringUtil.isBlank(name)) {
				builder.append(beanDbNameConverter
						.propertyNameToDbFieldName(name));
			}
			if (!StringUtil.isBlank(operator)) {
				builder.append(" ").append(operator.trim()).append(" ");
			}
			if (!ObjectUtil.isEmptyObject(value)) {
				builder.append("'").append(value).append("'");
			}
		}
		return builder.toString().replaceAll("\\s{2}+", " ").trim();
	}

	private Conditions append(String name, String operator, Object value) {
		Condition condition = new Condition(name, operator, value);
		conditionList.add(condition);
		return this;
	}

	private Conditions append(String operator) {
		return append("", operator, "");
	}

	private class Condition {

		private String name;
		private String operator;
		private Object value;

		private Condition(String name, String operator, Object value) {
			this.name = name;
			this.operator = operator;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getOperator() {
			return operator;
		}

		public Object getValue() {
			return value;
		}
	}

	public static void main(String[] args) {
		Conditions conditions = new Conditions().condition("f1", "=", "1")
				.and().left().condition("f2", "=", "2").or()
				.condition("f3", "=", "3").right();

		System.out.println(conditions.toString());
	}
}