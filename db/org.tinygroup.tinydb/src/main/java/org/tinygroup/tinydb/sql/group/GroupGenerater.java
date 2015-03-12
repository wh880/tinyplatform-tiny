package org.tinygroup.tinydb.sql.group;

/**
 * 生成group by 子句
 * @author renhui
 *
 */
public interface GroupGenerater {

	String generateGroupBy(String columnName);
}
