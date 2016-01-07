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
package org.tinygroup.tinysqldsl.extend;

import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.formitem.FromItemList;
import org.tinygroup.tinysqldsl.selectitem.AllColumns;
import org.tinygroup.tinysqldsl.selectitem.SelectItem;

/**
 * DB2数据库相关的查询
 * @author renhui
 *
 */
public class DB2Select extends Select<DB2Select> {

	public DB2Select() {
		super();
	}

	public static DB2Select select(SelectItem... selectItems) {
		DB2Select select = new DB2Select();
		select.getPlainSelect().addSelectItems(selectItems);
		return select;
	}

	public static DB2Select selectFrom(Table... tables) {
		DB2Select select = new DB2Select();
		select.getPlainSelect().addSelectItems(new AllColumns());
		select.getPlainSelect().setFromItem(new FromItemList(tables));
		return select;
	}
	
	public DB2Select into(Table... tables) {
		plainSelect.addIntoTables(tables);
		return this;
	}

	public DB2Select page(int start, int limit) {
		this.stringBuilder = getLimitString(sql(), start, limit);
		return this;
	}
	
	@Override
	protected Select newSelect() {
		return new DB2Select();
	}
	
	public static void main(String[] args) {
		DB2Select db2Select=new DB2Select();
		String sql="select distinct(id) from custom where name=dsff";
		
		System.out.println(db2Select.getLimitString(sql, 5, 5));
	}
}
