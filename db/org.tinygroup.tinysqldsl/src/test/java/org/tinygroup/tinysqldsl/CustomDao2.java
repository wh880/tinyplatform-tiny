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
package org.tinygroup.tinysqldsl;

import java.util.List;

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.Delete.delete;
import static org.tinygroup.tinysqldsl.Insert.insertInto;
import static org.tinygroup.tinysqldsl.Select.selectFrom;
import static org.tinygroup.tinysqldsl.Update.update;
import static org.tinygroup.tinysqldsl.base.StatementSqlBuilder.and;

public class CustomDao2 {
	private DslSession dslSession;

	public DslSession getDslSession() {
		return dslSession;
	}

	public void setDslSession(DslSession dslSession) {
		this.dslSession = dslSession;
	}

	public void insertCustom(Custom custom) {
		dslSession.execute(
			insertInto(CUSTOM).values(
				CUSTOM.ID.value(custom.getId()),
				CUSTOM.NAME.value(custom.getName()),
				CUSTOM.AGE.value(custom.getAge())
			)
		);
	}

	public void updateCustom(Custom custom) {// 以第一个字段作为条件
		dslSession.execute(
			update(CUSTOM).set(
				CUSTOM.NAME.value(custom.getName()),
				CUSTOM.AGE.value(custom.getAge())).where(
				CUSTOM.ID.eq(custom.getId())
			)
		);
	}

	public void deleteCustom(String id) {
		dslSession.execute(
				delete(CUSTOM).where(
						CUSTOM.ID.eq(id)
				)
		);
	}

	public Custom getCustomById(String id) {
		return dslSession.fetchOneResult(
			selectFrom(CUSTOM).where(
					CUSTOM.ID.eq(id)
			)
		, Custom.class);
	}

	public List<Custom> queryCustom(Custom custom) {//如果id作为主键字段，不作为条件出现。
		return dslSession.fetchList(
			selectFrom(CUSTOM).where(
				and(
						CUSTOM.ID.eq(custom.getId()),
						CUSTOM.NAME.equal(custom.getName()),
						CUSTOM.AGE.equal(custom.getAge())
				)
			)
		, Custom.class);
	}
}
