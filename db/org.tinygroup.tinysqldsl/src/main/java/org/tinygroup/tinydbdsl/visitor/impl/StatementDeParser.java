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
package org.tinygroup.tinydbdsl.visitor.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinydbdsl.base.SelectBody;
import org.tinygroup.tinydbdsl.delete.DeleteBody;
import org.tinygroup.tinydbdsl.insert.InsertBody;
import org.tinygroup.tinydbdsl.update.UpdateBody;
import org.tinygroup.tinydbdsl.visitor.StatementVisitor;

public class StatementDeParser implements StatementVisitor {

	private StringBuilder buffer;
	private List<Object> values = new ArrayList<Object>();

	public StatementDeParser(StringBuilder buffer, List<Object> values) {
		this.buffer = buffer;
		this.values = values;
	}

	public void visit(DeleteBody delete) {
		SelectDeParser selectDeParser = new SelectDeParser();
		selectDeParser.setBuffer(buffer);
		selectDeParser.setValues(values);
		ExpressionDeParser expressionDeParser = new ExpressionDeParser(
				selectDeParser, buffer, values);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		DeleteDeParser deleteDeParser = new DeleteDeParser(expressionDeParser,
				buffer, values);
		deleteDeParser.deParse(delete);

	}

	public void visit(UpdateBody update) {
		SelectDeParser selectDeParser = new SelectDeParser();
		selectDeParser.setBuffer(buffer);
		selectDeParser.setValues(values);
		ExpressionDeParser expressionDeParser = new ExpressionDeParser(
				selectDeParser, buffer, values);
		UpdateDeParser updateDeParser = new UpdateDeParser(expressionDeParser,
				selectDeParser, buffer, values);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		updateDeParser.deParse(update);
	}

	public void visit(InsertBody insert) {
		SelectDeParser selectDeParser = new SelectDeParser();
		selectDeParser.setBuffer(buffer);
		selectDeParser.setValues(values);
		ExpressionDeParser expressionDeParser = new ExpressionDeParser(
				selectDeParser, buffer, values);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		InsertDeparser insertDeParser = new InsertDeparser(expressionDeParser,
				selectDeParser, buffer, values);
		insertDeParser.deParse(insert);

	}

	public void visit(SelectBody select) {
		SelectDeParser selectDeParser = new SelectDeParser();
		selectDeParser.setBuffer(buffer);
		selectDeParser.setValues(values);
		ExpressionDeParser expressionDeParser = new ExpressionDeParser(
				selectDeParser, buffer, values);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		select.accept(selectDeParser);
	}
}
