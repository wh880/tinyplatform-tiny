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

import org.tinygroup.tinydbdsl.base.Condition;

/**
 * 二元操作接口
 * 
 * @author renhui
 * 
 */
public interface BinaryOperator {
	Condition eq(Object value);
	
	Condition equal(Object value);

	Condition between(Object start, Object end);

	Condition neq(Object value);

	Condition notEqual(Object value);

	Condition gt(Object value);

	Condition greaterThan(Object value);

	Condition gte(Object value);

	Condition greaterThanEqual(Object value);

	Condition lt(Object value);

	Condition lessThan(Object value);

	Condition lessThanEqual(Object value);

	Condition lte(Object value);

	Condition isNull();

	Condition isNotNull();

	Condition like(String value);
	
	Condition notLike(String value);

	Condition leftLike(String value);

	Condition rightLike(String value);
}
