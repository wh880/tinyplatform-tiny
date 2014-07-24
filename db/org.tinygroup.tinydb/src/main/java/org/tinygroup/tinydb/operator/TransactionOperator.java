package org.tinygroup.tinydb.operator;

/**
 * 事务操作api
 * @author renhui
 *
 */
public interface TransactionOperator {

	/**
	 * 开始事务
	 */
	public void beginTransaction();
	
	/**
	 * 提交事务
	 */
	public  void commitTransaction();
	
	/**
	 * 回滚事务
	 */
	public void rollbackTransaction();
	
	/**
	 * 以事务方式执行回调处理
	 * @param callback
	 * @return
	 */
	public Object execute(TransactionCallBack callback);
	
	
}
