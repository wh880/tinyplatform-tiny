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