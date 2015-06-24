package org.tinygroup.tinysqldsl.selectitem;

import org.tinygroup.tinysqldsl.base.FragmentSql;
import org.tinygroup.tinysqldsl.base.StatementSqlBuilder;

/**
 * select 与 from 之间的特殊化sql片段
 *
 * @author renhui
 */
public class FragmentSelectItemSql extends FragmentSql implements SelectItem {

    public FragmentSelectItemSql(String fragment) {
        super(fragment);
    }

	public void builderSelectItem(StatementSqlBuilder builder) {
		builder.appendSql(getFragment());
	}
}
