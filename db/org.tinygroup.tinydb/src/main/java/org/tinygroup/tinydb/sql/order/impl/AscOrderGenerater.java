package org.tinygroup.tinydb.sql.order.impl;

import org.tinygroup.tinydb.sql.order.OrderGenerater;

/**
 * 降序
 * @author renhui
 *
 */
public class AscOrderGenerater implements OrderGenerater {

	public String getOrderMode() {
		return "desc";
	}

	public String generateOrder(String columnName) {
		return columnName +" desc ";
	}

}
