package org.tinygroup.tinysqldsl;

import static org.tinygroup.tinysqldsl.CustomTable.*;
import static org.tinygroup.tinysqldsl.Select.*;
import static org.tinygroup.tinysqldsl.Insert.*;
import static org.tinygroup.tinysqldsl.Delete.*;
import static org.tinygroup.tinysqldsl.Update.*;
import java.util.List;

public class CustomDao {
	private DslSqlSession dslSqlSession;

	public DslSqlSession getDslSqlSession() {
		return dslSqlSession;
	}

	public void setDslSqlSession(DslSqlSession dslSqlSession) {
		this.dslSqlSession = dslSqlSession;
	}

	public Custom insertCustom(Custom custom) {
		Insert insert = insertInto(CUSTOM).values(
				CUSTOM.ID.value(custom.getId()),
				CUSTOM.NAME.value(custom.getName()),
				CUSTOM.AGE.value(custom.getAge()));
		dslSqlSession.execute(insert);
		return custom;
	}

	public void updateCustom(Custom custom) {// 以第一个字段作为条件
		Update update = update(CUSTOM).set(CUSTOM.NAME.value(custom.getName()),
				CUSTOM.AGE.value(custom.getAge())).where(
				CUSTOM.ID.eq(custom.getId()));
		dslSqlSession.execute(update);
	}

	public void deleteCustom(String id) {
		Delete delete = delete(CUSTOM).where(CUSTOM.ID.eq(id));
		dslSqlSession.execute(delete);
	}

	public Custom getCustomById(String id) {
		Select select = selectFrom(CUSTOM).where(CUSTOM.ID.eq(id));
		return dslSqlSession.fetchOneResult(select, Custom.class);
	}

	public List<Custom> queryCustom(Custom custom) {//如果id作为主键字段，不作为条件出现。
		Select select = selectFrom(CUSTOM).where(
				and(CUSTOM.ID.eq(custom.getId()),
						CUSTOM.NAME.equal(custom.getName()),
						CUSTOM.AGE.equal(custom.getAge())));
		return dslSqlSession.fetchList(select, Custom.class);
	}
}
