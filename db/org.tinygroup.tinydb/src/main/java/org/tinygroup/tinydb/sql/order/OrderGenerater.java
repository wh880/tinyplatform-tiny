package org.tinygroup.tinydb.sql.order;

/**
 * 生成order by 的sql片段
 * @author renhui
 *
 */
public interface OrderGenerater {
	
	String getOrderMode();

	String generateOrder(String columnName);
}
