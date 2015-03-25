package org.tinygroup.tinydbdsl.visitor;

import org.tinygroup.tinydbdsl.base.SelectBody;
import org.tinygroup.tinydbdsl.delete.DeleteBody;
import org.tinygroup.tinydbdsl.insert.InsertBody;
import org.tinygroup.tinydbdsl.update.UpdateBody;

/**
 * delete,insert,update访问者
 * 
 * @author renhui
 * 
 */
public interface StatementVisitor {

	void visit(DeleteBody delete);

	void visit(UpdateBody update);

	void visit(InsertBody insert);

	void visit(SelectBody select);
}
