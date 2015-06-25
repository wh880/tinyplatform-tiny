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
package org.tinygroup.tinysqldsl.extend;

import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.formitem.FromItemList;
import org.tinygroup.tinysqldsl.select.Limit;
import org.tinygroup.tinysqldsl.selectitem.AllColumns;
import org.tinygroup.tinysqldsl.selectitem.SelectItem;

/**
 * mysql数据库相关的查询
 * 
 * @author renhui
 * 
 */
public class MysqlSelect extends Select<MysqlSelect> {
	private MysqlSelect() {
		super();
	}

	public static MysqlSelect select(SelectItem... selectItems) {
		MysqlSelect select = new MysqlSelect();
		select.getPlainSelect().addSelectItems(selectItems);
		return select;
	}

	public static MysqlSelect selectFrom(Table... tables) {
		MysqlSelect select = new MysqlSelect();
		select.getPlainSelect().addSelectItems(new AllColumns());
		select.getPlainSelect().setFromItem(new FromItemList(tables));
		return select;
	}

	public MysqlSelect limit(int start, int limit) {
		plainSelect.setLimit(new Limit(start, limit, false, false));
		return this;
	}

	/**
	 * 生成的sql语句 start和limit用？代替
	 * 
	 * @param limit
	 * @return
	 */
	public MysqlSelect limit(Limit limit) {
		plainSelect.setLimit(limit);
		return this;
	}

}
