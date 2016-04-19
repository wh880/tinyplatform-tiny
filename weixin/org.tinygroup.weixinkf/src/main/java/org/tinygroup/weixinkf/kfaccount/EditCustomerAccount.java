package org.tinygroup.weixinkf.kfaccount;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 编辑管理账户
 * @author yancheng11334
 *
 */
public class EditCustomerAccount extends ManageCustomerAccount{

	public EditCustomerAccount(String accountId) {
		super(accountId);
	}

	public EditCustomerAccount(String accountId, String nickName, String password) {
		super(accountId, nickName, password);
	}
	
	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "editKf";
	}
}
