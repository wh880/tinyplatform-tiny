/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.jsqlparser.extend;

import org.tinygroup.jsqlparser.expression.ExpressionVisitorAdapter;
import org.tinygroup.jsqlparser.statement.StatementVisitorAdapter;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.statement.select.WithItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 查找参数信息
 * 
 * @author renhui
 * 
 */
public class ParameterFinder extends StatementVisitorAdapter {
	private Map<String, Integer> positionMap = new HashMap<String, Integer>();
	private AtomicInteger paramLength = new AtomicInteger(0);

	@Override
	public void visit(Select select) {
		ParameterSelectVisitor selectVisitor = new ParameterSelectVisitor(
				positionMap, paramLength);
		ExpressionVisitorAdapter expressionVisitor = new ParameterExpressionVisitor(
				positionMap, paramLength, selectVisitor);
		selectVisitor.setExpressionVisitor(expressionVisitor);
		if (select.getWithItemsList() != null
				&& !select.getWithItemsList().isEmpty()) {
			for (Iterator<WithItem> iter = select.getWithItemsList().iterator(); iter
					.hasNext();) {
				WithItem withItem = iter.next();
				withItem.accept(selectVisitor);
			}
		}
		select.getSelectBody().accept(selectVisitor);
	}

	private void init(){
		positionMap = new HashMap<String, Integer>();
		paramLength=new AtomicInteger(0);
	}

	public ParameterEntry getParameterEntry(Select select) {
		init();
		visit(select);
		return new ParameterEntry(positionMap, paramLength);
	}

	
	public class ParameterEntry{
		private Map<String, Integer> positionMap;
		private AtomicInteger paramLength ;
		public ParameterEntry(Map<String, Integer> positionMap,
				AtomicInteger paramLength) {
			super();
			this.positionMap = positionMap;
			this.paramLength = paramLength;
		}
		public Map<String, Integer> getPositionMap() {
			return positionMap;
		}
		public int getParamLength() {
			return paramLength.get();
		}
	}
}
