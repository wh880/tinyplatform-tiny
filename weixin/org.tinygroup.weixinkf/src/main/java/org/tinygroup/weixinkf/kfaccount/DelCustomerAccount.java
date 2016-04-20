package org.tinygroup.weixinkf.kfaccount;

import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 删除管理账户
 * @author yancheng11334
 *
 */
public class DelCustomerAccount implements ToServerMessage{

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "delKf";
	}

}
