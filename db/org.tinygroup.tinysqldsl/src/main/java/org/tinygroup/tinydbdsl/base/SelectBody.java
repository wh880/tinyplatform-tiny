package org.tinygroup.tinydbdsl.base;

import org.tinygroup.tinydbdsl.visitor.SelectVisitor;



/**
 * select对象的标识接口
 * @author renhui
 *
 */
public interface SelectBody extends StatementBody{
	void accept(SelectVisitor selectVisitor);
}
