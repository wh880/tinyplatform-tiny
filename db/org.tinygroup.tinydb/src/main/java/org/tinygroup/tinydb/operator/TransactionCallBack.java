package org.tinygroup.tinydb.operator;

import org.springframework.transaction.TransactionStatus;


/**
 * 事务回调接口
 * @author renhui
 *
 */
public interface TransactionCallBack {

	public Object callBack(TransactionStatus status);
	
}
