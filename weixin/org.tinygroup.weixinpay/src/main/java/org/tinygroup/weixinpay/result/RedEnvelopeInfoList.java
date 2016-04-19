package org.tinygroup.weixinpay.result;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 红包信息列表
 * @author yancheng11334
 *
 */
public class RedEnvelopeInfoList {
	@XStreamImplicit
    private List<RedEnvelopeInfo>  intoList;

	public List<RedEnvelopeInfo> getIntoList() {
		return intoList;
	}

	public void setIntoList(List<RedEnvelopeInfo> intoList) {
		this.intoList = intoList;
	}
    
}
