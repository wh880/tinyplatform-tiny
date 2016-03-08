/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.tinysqldsl.extend;

import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.formitem.FromItemList;
import org.tinygroup.tinysqldsl.select.Fetch;
import org.tinygroup.tinysqldsl.select.Offset;
import org.tinygroup.tinysqldsl.selectitem.AllColumns;
import org.tinygroup.tinysqldsl.selectitem.SelectItem;

/**
 * 与sqlserver数据库相关的查询语句
 *
 * @author renhui
 *
 */
public class DerbySelect extends Select<DerbySelect> {

    public DerbySelect() {
        super();
    }

    public static DerbySelect select(SelectItem... selectItems) {
        DerbySelect select = new DerbySelect();
        select.getPlainSelect().addSelectItems(selectItems);
        return select;
    }

    public static DerbySelect selectFrom(Table... tables) {
        DerbySelect select = new DerbySelect();
        select.getPlainSelect().addSelectItems(new AllColumns());
        select.getPlainSelect().setFromItem(new FromItemList(tables));
        return select;
    }

    public DerbySelect into(Table... tables) {
        plainSelect.addIntoTables(tables);
        return this;
    }

    public DerbySelect offset(Offset offset) {
        plainSelect.setOffset(offset);
        return this;
    }

    public DerbySelect fetch(Fetch fetch) {
        plainSelect.setFetch(fetch);
        return this;
    }

    @Override
    protected Select newSelect() {
        return new DerbySelect();
    }

}
