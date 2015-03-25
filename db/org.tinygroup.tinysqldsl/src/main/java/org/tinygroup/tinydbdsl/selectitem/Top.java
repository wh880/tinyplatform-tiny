package org.tinygroup.tinydbdsl.selectitem;

import org.tinygroup.tinydbdsl.visitor.SelectItemVisitor;


/**
 * A top clause in the form [TOP (row_count) or TOP row_count]
 */
public class Top implements SelectItem {

    private long rowCount;
    private boolean rowCountJdbcParameter = false;
    private boolean hasParenthesis = false;
    private boolean isPercentage = false;
    
    public Top(long rowCount, boolean rowCountJdbcParameter,
			boolean hasParenthesis, boolean isPercentage) {
		super();
		this.rowCount = rowCount;
		this.rowCountJdbcParameter = rowCountJdbcParameter;
		this.hasParenthesis = hasParenthesis;
		this.isPercentage = isPercentage;
	}
    
    public  static Top top(long rowCount) {
		return new Top(rowCount, false, true, false);
	}
    

	public Top topParameter(long rowCount) {
		return new Top(rowCount, true, true, false);
	}

	public long getRowCount() {
        return rowCount;
    }

    // TODO instead of a plain number, an expression should be added, which could be a NumberExpression, a GroupedExpression or a JdbcParameter
    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    public boolean isRowCountJdbcParameter() {
        return rowCountJdbcParameter;
    }

    public void setRowCountJdbcParameter(boolean rowCountJdbcParameter) {
        this.rowCountJdbcParameter = rowCountJdbcParameter;
    }

    public boolean hasParenthesis() {
        return hasParenthesis;
    }

    public void setParenthesis(boolean hasParenthesis) {
        this.hasParenthesis = hasParenthesis;
    }

    public boolean isPercentage() {
        return isPercentage;
    }

    public void setPercentage(boolean percentage) {
        this.isPercentage = percentage;
    }
    
    public void accept(SelectItemVisitor selectItemVisitor) {
		  selectItemVisitor.visit(this);
	}

    public String toString() {
        String result = "TOP ";

        if (hasParenthesis) {
            result += "(";
        }

        result += rowCountJdbcParameter ? "?"
                : rowCount;

        if (hasParenthesis) {
            result += ")";
        }

        if (isPercentage) {
            result += " PERCENT";
        }

        return result;
    }
}
