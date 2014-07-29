package org.tinygroup.tinydb.operator;

import org.springframework.transaction.TransactionStatus;
import org.tinygroup.tinydb.exception.TinyDbException;


/**
 * 事务回调接口
 * @author renhui
 *
 */
public interface TransactionCallBack {

	public Object callBack(TransactionStatus status)throws TinyDbException;
	
}
