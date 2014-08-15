package org.tinygroup.tinydb.operator;

import java.util.Collection;

import org.tinygroup.dynamicdatasource.ConnectionTrace;

/**
 * db 监控信息接口
 * @author renhui
 *
 */
public interface DbMonitorOperator {
	
	/**
	 * 获取当前正在使用的连接信息
	 * @return
	 */
	public Collection<ConnectionTrace> queryAllActiveConnection();

}
