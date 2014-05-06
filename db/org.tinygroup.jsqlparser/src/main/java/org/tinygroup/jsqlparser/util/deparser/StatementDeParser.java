/*
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2013 JSQLParser
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 2.1 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package org.tinygroup.jsqlparser.util.deparser;

import java.util.Iterator;

import org.tinygroup.jsqlparser.statement.StatementVisitor;
import org.tinygroup.jsqlparser.statement.Statements;
import org.tinygroup.jsqlparser.statement.alter.Alter;
import org.tinygroup.jsqlparser.statement.create.index.CreateIndex;
import org.tinygroup.jsqlparser.statement.create.table.CreateTable;
import org.tinygroup.jsqlparser.statement.create.view.CreateView;
import org.tinygroup.jsqlparser.statement.delete.Delete;
import org.tinygroup.jsqlparser.statement.drop.Drop;
import org.tinygroup.jsqlparser.statement.insert.Insert;
import org.tinygroup.jsqlparser.statement.replace.Replace;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.statement.select.WithItem;
import org.tinygroup.jsqlparser.statement.truncate.Truncate;
import org.tinygroup.jsqlparser.statement.update.Update;

public class StatementDeParser implements StatementVisitor {

	private StringBuilder buffer;

	public StatementDeParser(StringBuilder buffer) {
		this.buffer = buffer;
	}


	public void visit(CreateIndex createIndex) {
		CreateIndexDeParser createIndexDeParser = new CreateIndexDeParser(buffer);
		createIndexDeParser.deParse(createIndex);
	}


	public void visit(CreateTable createTable) {
		CreateTableDeParser createTableDeParser = new CreateTableDeParser(buffer);
		createTableDeParser.deParse(createTable);
	}


	public void visit(CreateView createView) {
		CreateViewDeParser createViewDeParser = new CreateViewDeParser(buffer);
		createViewDeParser.deParse(createView);
	}


	public void visit(Delete delete) {
		SelectDeParser selectDeParser = new SelectDeParser();
		selectDeParser.setBuffer(buffer);
		ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		DeleteDeParser deleteDeParser = new DeleteDeParser(expressionDeParser, buffer);
		deleteDeParser.deParse(delete);
	}


	public void visit(Drop drop) {
		// TODO Auto-generated method stub
	}


	public void visit(Insert insert) {
		SelectDeParser selectDeParser = new SelectDeParser();
		selectDeParser.setBuffer(buffer);
		ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		InsertDeParser insertDeParser = new InsertDeParser(expressionDeParser, selectDeParser, buffer);
		insertDeParser.deParse(insert);

	}


	public void visit(Replace replace) {
		SelectDeParser selectDeParser = new SelectDeParser();
		selectDeParser.setBuffer(buffer);
		ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		ReplaceDeParser replaceDeParser = new ReplaceDeParser(expressionDeParser, selectDeParser, buffer);
		replaceDeParser.deParse(replace);
	}


	public void visit(Select select) {
		SelectDeParser selectDeParser = new SelectDeParser();
		selectDeParser.setBuffer(buffer);
		ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		if (select.getWithItemsList() != null && !select.getWithItemsList().isEmpty()) {
			buffer.append("WITH ");
			for (Iterator<WithItem> iter = select.getWithItemsList().iterator(); iter.hasNext();) {
				WithItem withItem = iter.next();
				buffer.append(withItem);
				if (iter.hasNext()) {
					buffer.append(",");
				}
				buffer.append(" ");
			}
		}
		select.getSelectBody().accept(selectDeParser);
	}


	public void visit(Truncate truncate) {
	}


	public void visit(Update update) {
		SelectDeParser selectDeParser = new SelectDeParser();
		selectDeParser.setBuffer(buffer);
		ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
		UpdateDeParser updateDeParser = new UpdateDeParser(expressionDeParser, buffer);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		updateDeParser.deParse(update);

	}

	public StringBuilder getBuffer() {
		return buffer;
	}

	public void setBuffer(StringBuilder buffer) {
		this.buffer = buffer;
	}


	public void visit(Alter alter) {
		
	}


    public void visit(Statements stmts) {
        stmts.accept(this);
    }
}
