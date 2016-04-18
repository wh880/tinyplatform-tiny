package org.tinygroup.weixintool.result;

import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 长链接转短链接的结果
 * @author yancheng11334
 *
 */
public class ShortUrlResult implements ToServerResult{

	@JSONField(name="errcode")
    private String errCode;
    
    @JSONField(name="errmsg")
    private String errMsg;
    
    @JSONField(name="short_url")
    private String shortUrl;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
    
    
}
