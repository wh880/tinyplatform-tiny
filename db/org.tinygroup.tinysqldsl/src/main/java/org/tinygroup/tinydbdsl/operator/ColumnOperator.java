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
package org.tinygroup.tinydbdsl.operator;

import org.tinygroup.tinydbdsl.expression.Function;
import org.tinygroup.tinydbdsl.expression.relational.ExpressionList;

public abstract class ColumnOperator extends SimpleBinaryOperator implements
		StatisticsOperator {

	public Function sum() {
		return new Function("sum", ExpressionList.expressionList(this));
	}

	public Function count() {
		return new Function("count", ExpressionList.expressionList(this));
	}

	public Function avg() {
		return new Function("avg", ExpressionList.expressionList(this));
	}

	public Function max() {
		return new Function("max", ExpressionList.expressionList(this));
	}

	public Function min() {
		return new Function("min", ExpressionList.expressionList(this));
	}

}