/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.weixin.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 微信客户号设置
 * @author yancheng11334
 *
 */
public class Client {
	
	/**
	 * 默认的bean配置名称
	 */
	public static final String DEFAULT_BEAN_NAME="weiXinClient";
	
    /**
     * 默认的context名称
     */
	public static final String DEFAULT_CONTEXT_NAME = "WEIXIN_CLIENT";
	
	/**
	 * 微信的appid
	 */
    @XStreamAlias("appid")
    String appId;
    
    /**
     * 微信的app秘钥
     */
    String secret;
    
    /**
     * 认证的token字符串
     */
    String token;
    
    /**
     * 商品平台的key字符串
     */
    @XStreamAlias("paytoken")
    String payToken;
  
    /**
     * 微信消息加密密钥
     */
    @XStreamAlias("encode-ase-key")
    String encodeAseKey;
    
    /**
     * HTTP是否强制检查签名
     */
    @XStreamAlias("check-signature")
    boolean checkSignature;

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

	public String getPayToken() {
		return payToken;
	}

	public void setPayToken(String payToken) {
		this.payToken = payToken;
	}

	public String getEncodeAseKey() {
		return encodeAseKey;
	}

	public void setEncodeAseKey(String encodeAseKey) {
		this.encodeAseKey = encodeAseKey;
	}

	public boolean isCheckSignature() {
		return checkSignature;
	}

	public void setCheckSignature(boolean checkSignature) {
		this.checkSignature = checkSignature;
	}
    
}
