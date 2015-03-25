package org.tinygroup.tinydbdsl.formitem;

import org.tinygroup.tinydbdsl.base.Alias;
import org.tinygroup.tinydbdsl.base.SelectBody;
import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.expression.relational.ItemsList;
import org.tinygroup.tinydbdsl.visitor.ExpressionVisitor;
import org.tinygroup.tinydbdsl.visitor.FromItemVisitor;
import org.tinygroup.tinydbdsl.visitor.ItemsListVisitor;

/**
 * A subselect followed by an optional alias.
 */
public class SubSelect implements FromItem, Expression, ItemsList {

	private SelectBody selectBody;
	private Alias alias;
    private boolean useBrackets = true;

	public SubSelect(SelectBody selectBody, Alias alias, boolean useBrackets) {
		super();
		this.selectBody = selectBody;
		this.alias = alias;
		this.useBrackets = useBrackets;
	}
	public SelectBody getSelectBody() {
		return selectBody;
	}

	public void setSelectBody(SelectBody body) {
		selectBody = body;
	}

	
	public Alias getAlias() {
		return alias;
	}

	
	public void setAlias(Alias alias) {
		this.alias = alias;
	}


    public boolean isUseBrackets() {
        return useBrackets;
    }

    public void setUseBrackets(boolean useBrackets) {
        this.useBrackets = useBrackets;
    }

	
	public String toString() {
		return (useBrackets?"(":"") + selectBody + (useBrackets?")":"") 
                + ((alias != null) ? alias.toString() : "");
	}
	public void accept(FromItemVisitor fromItemVisitor) {
		fromItemVisitor.visit(this);
	}
	public void accept(ItemsListVisitor itemsListVisitor) {
		itemsListVisitor.visit(this);
	}
	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);		
	}
}
