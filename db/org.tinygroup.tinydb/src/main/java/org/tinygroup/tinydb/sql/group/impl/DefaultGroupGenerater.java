package org.tinygroup.tinydb.sql.group.impl;

import org.tinygroup.tinydb.sql.group.GroupGenerater;

/**
 * 
 * @author renhui
 *
 */
public class DefaultGroupGenerater implements GroupGenerater {
	public String generateGroupBy(String columnName) {
		return columnName;
	}
}
