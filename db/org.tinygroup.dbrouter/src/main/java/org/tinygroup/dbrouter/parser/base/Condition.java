package org.tinygroup.dbrouter.parser.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储sql解析后的条件信息
 * @author renhui
 *
 */
public class Condition {

	private ColumnInfo column;
	private String operator;

	private List<Object> values = new ArrayList<Object>();

	public ColumnInfo getColumn() {
		return column;
	}

	public void setColumn(ColumnInfo column) {
		this.column = column;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<Object> getValues() {
		return values;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Condition other = (Condition) obj;
		if (column == null) {
			if (other.column != null) {
				return false;
			}
		} else if (!column.equals(other.column)) {
			return false;
		}
		if (operator == null) {
			if (other.operator != null) {
				return false;
			}
		} else if (!operator.equals(other.operator)) {
			return false;
		}
		return true;
	}

	public String toString() {
		return this.column.toString() + " " + this.operator+values.toString();
	}
}