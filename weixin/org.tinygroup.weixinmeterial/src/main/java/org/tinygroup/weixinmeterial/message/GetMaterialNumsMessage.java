package org.tinygroup.weixinmeterial.message;

import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获得素材总数
 * @author yancheng11334
 *
 */
public class GetMaterialNumsMessage implements ToServerMessage{

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "getMaterialNums";
	}
}
