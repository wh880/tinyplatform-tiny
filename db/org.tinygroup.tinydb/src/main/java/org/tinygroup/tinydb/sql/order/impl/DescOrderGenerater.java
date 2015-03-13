package org.tinygroup.tinydb.sql.order.impl;

import org.tinygroup.tinydb.sql.order.OrderGenerater;

/**
 * 升序
 * @author renhui
 *
 */
public class DescOrderGenerater implements OrderGenerater {

	public String getOrderMode() {
		return "asc";
	}

	public String generateOrder(String columnName) {
		return columnName +" asc ";
	}

}
