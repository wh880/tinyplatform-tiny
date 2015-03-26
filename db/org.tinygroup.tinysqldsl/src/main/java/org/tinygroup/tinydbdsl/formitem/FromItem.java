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
package org.tinygroup.tinydbdsl.formitem;

import org.tinygroup.tinydbdsl.base.Alias;
import org.tinygroup.tinydbdsl.visitor.FromItemVisitor;

/**
 * An item in a "SELECT [...] FROM item1" statement. (for example a table or a
 * sub-select)
 */
public interface FromItem {
	
	void accept(FromItemVisitor fromItemVisitor);

	Alias getAlias();

	void setAlias(Alias alias);

}