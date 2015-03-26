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

import org.tinygroup.tinydbdsl.base.SelectBody;
import org.tinygroup.tinydbdsl.select.Fetch;
import org.tinygroup.tinydbdsl.select.Limit;
import org.tinygroup.tinydbdsl.select.Offset;
import org.tinygroup.tinydbdsl.select.OrderByElement;
import org.tinygroup.tinydbdsl.select.SetOperation;
import org.tinygroup.tinydbdsl.util.DslUtil;
import org.tinygroup.tinydbdsl.visitor.SelectVisitor;
import org.tinygroup.tinydbdsl.visitor.StatementVisitor;

/**
 * A database set operation. This operation consists of a list of plainSelects
 * connected by set operations (UNION,INTERSECT,MINUS,EXCEPT). All these
 * operations have the same priority.
 * 
 */
public class SetOperationList implements SelectBody {

	private List<PlainSelect> plainSelects;
	private List<SetOperation> operations;
	private List<OrderByElement> orderByElements;
	private Limit limit;
	private Offset offset;
	private Fetch fetch;

	public List<OrderByElement> getOrderByElements() {
		return orderByElements;
	}

	public List<PlainSelect> getPlainSelects() {
		return plainSelects;
	}

	public List<SetOperation> getOperations() {
		return operations;
	}

	public void setOrderByElements(List<OrderByElement> orderByElements) {
		this.orderByElements = orderByElements;
	}

	public void setOpsAndSelects(List<PlainSelect> select,
			List<SetOperation> ops) {
		plainSelects = select;
		operations = ops;

		if (select.size() - 1 != ops.size()) {
			throw new IllegalArgumentException("list sizes are not valid");
		}
	}

	public void addOrderByElements(OrderByElement... orderBys) {
		if (orderByElements == null) {
			orderByElements = new ArrayList<OrderByElement>();
		}
		Collections.addAll(orderByElements, orderBys);
	}

	public Limit getLimit() {
		return limit;
	}

	public void setLimit(Limit limit) {
		this.limit = limit;
	}

	public Offset getOffset() {
		return offset;
	}

	public void setOffset(Offset offset) {
		this.offset = offset;
	}

	public Fetch getFetch() {
		return fetch;
	}

	public void setFetch(Fetch fetch) {
		this.fetch = fetch;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		for (int i = 0; i < plainSelects.size(); i++) {
			if (i != 0) {
				buffer.append(" ").append(operations.get(i - 1).toString())
						.append(" ");
			}
			buffer.append("(").append(plainSelects.get(i).toString())
					.append(")");
		}

		if (orderByElements != null) {
			buffer.append(DslUtil.orderByToString(orderByElements));
		}
		if (limit != null) {
			buffer.append(limit.toString());
		}
		if (offset != null) {
			buffer.append(offset.toString());
		}
		if (fetch != null) {
			buffer.append(fetch.toString());
		}
		return buffer.toString();
	}

	/**
	 * list of set operations.
	 */
	public enum SetOperationType {

		INTERSECT, EXCEPT, MINUS, UNION
	}

	public void accept(SelectVisitor selectVisitor) {
		selectVisitor.visit(this);
	}

	public void accept(StatementVisitor visitor) {
		visitor.visit(this);
	}
}
