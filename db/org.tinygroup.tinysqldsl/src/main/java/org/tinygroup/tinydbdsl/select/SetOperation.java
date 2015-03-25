package org.tinygroup.tinydbdsl.select;

import org.tinygroup.tinydbdsl.SetOperationList.SetOperationType;

/**
 * Single Set-Operation (name). Placeholder for one specific set operation, e.g.
 * union, intersect.
 *
 */
public abstract class SetOperation{

	private SetOperationType type;

	public SetOperation(SetOperationType type) {
		this.type = type;
	}


	public String toString() {
		return type.name();
	}
}
