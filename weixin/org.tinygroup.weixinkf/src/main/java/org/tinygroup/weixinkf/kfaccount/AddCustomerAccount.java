package org.tinygroup.weixinkf.kfaccount;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 增加管理账户
 * 
 * @author yancheng11334
 * 
 */
public class AddCustomerAccount extends ManageCustomerAccount {

	public AddCustomerAccount(String accountId) {
		super(accountId);
	}

	public AddCustomerAccount(String accountId, String nickName, String password) {
		super(accountId, nickName, password);
	}

	@JSONField(serialize = false)
	public String getWeiXinKey() {
		return "addKf";
	}

}
