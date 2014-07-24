package org.tinygroup.tinydb.operator;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

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
	
	/**
	 * 设置事务管理对象
	 * @param transactionManager
	 */
	public void setTransactionManager(
			PlatformTransactionManager transactionManager) ;
	
	/**
	 * 设置事务定义，默认为PROPAGATION_REQUIRED,ISOLATION_DEFAULT
	 * @param transactionDefinition
	 */
	public void setTransactionDefinition(
			TransactionDefinition transactionDefinition);
	
	
}
