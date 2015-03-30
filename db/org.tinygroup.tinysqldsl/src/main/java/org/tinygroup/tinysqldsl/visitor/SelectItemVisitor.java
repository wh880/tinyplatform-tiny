/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p/>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.gnu.org/licenses/gpl.html
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.tinysqldsl.visitor;

import org.tinygroup.tinysqldsl.base.Column;
import org.tinygroup.tinysqldsl.expression.Function;
import org.tinygroup.tinysqldsl.selectitem.*;

/**
 * selectitem的访问者
 * @author renhui
 *
 */
public interface SelectItemVisitor {

    void visit(AllColumns allColumns);

    void visit(AllTableColumns allTableColumns);

    void visit(SelectExpressionItem selectExpressionItem);

    void visit(Distinct distinct);

    void visit(Top top);

    void visit(CustomSelectItem selectItem);

    void visit(Function function);

    void visit(Column column);

    void visit(FragmentSelectItemSql fragment);
}