package org.tinygroup.tinydbdsl.base;

import org.tinygroup.tinydbdsl.visitor.StatementVisitor;


/**
 * Created by luoguo on 2015/3/11.
 */
public interface StatementBody {
	public void accept(StatementVisitor visitor);
}
