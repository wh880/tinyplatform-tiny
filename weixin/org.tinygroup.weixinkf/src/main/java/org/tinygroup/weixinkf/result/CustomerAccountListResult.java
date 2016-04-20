package org.tinygroup.weixinkf.result;

import java.util.List;

import org.tinygroup.weixin.common.ToServerResult;
import org.tinygroup.weixinkf.kfaccount.CustomerServiceAccount;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获得全部客服的列表结果
 * @author yancheng11334
 *
 */
public class CustomerAccountListResult implements ToServerResult{

	@JSONField(name="kf_list")
	private List<CustomerServiceAccount>  customerServiceAccountList;

	public List<CustomerServiceAccount> getCustomerServiceAccountList() {
		return customerServiceAccountList;
	}

	public void setCustomerServiceAccountList(
			List<CustomerServiceAccount> customerServiceAccountList) {
		this.customerServiceAccountList = customerServiceAccountList;
	}
}
