package org.tinygroup.weixinkf.kfaccount;

import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获得管理者列表
 * @author yancheng11334
 *
 */
public class GetCustomerAccountList implements ToServerMessage{

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "getKfList";
	}
}
