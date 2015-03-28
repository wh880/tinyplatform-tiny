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
package org.tinygroup.tinysqldsl.visitor;

import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.formitem.FragmentFromItemSql;
import org.tinygroup.tinysqldsl.formitem.FromItemList;
import org.tinygroup.tinysqldsl.formitem.LateralSubSelect;
import org.tinygroup.tinysqldsl.formitem.SubJoin;
import org.tinygroup.tinysqldsl.formitem.SubSelect;
import org.tinygroup.tinysqldsl.formitem.ValuesList;

/**
 * fromitem的访问者
 * @author renhui
 *
 */
public interface FromItemVisitor {

	void visit(Table tableName);

	void visit(SubSelect subSelect);

	void visit(SubJoin subjoin);

	void visit(LateralSubSelect lateralSubSelect);

	void visit(ValuesList valuesList);
	
	void visit(FromItemList fromItemList);
	
	void visit(FragmentFromItemSql fragment);
}
