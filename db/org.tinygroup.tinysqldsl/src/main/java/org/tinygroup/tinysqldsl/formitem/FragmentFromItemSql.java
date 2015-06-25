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
package org.tinygroup.tinysqldsl.formitem;

import org.tinygroup.tinysqldsl.base.Alias;
import org.tinygroup.tinysqldsl.base.FragmentSql;
import org.tinygroup.tinysqldsl.base.StatementSqlBuilder;

/**
 * fromitem的sql片段，允许加入SQL字符串片断
 * 
 * @author renhui
 */
public class FragmentFromItemSql extends FragmentSql implements FromItem {

	private Alias alias;

	public FragmentFromItemSql(String fragment, Alias alias) {
		super(fragment);
		this.alias = alias;
	}

	public FragmentFromItemSql(String fragment) {
		super(fragment);
	}

	public Alias getAlias() {
		return alias;
	}

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return getFragment() + ((alias != null) ? alias.toString() : "");
	}

	public void builderFromItem(StatementSqlBuilder builder) {
		builder.appendSql(toString());
	}

}
