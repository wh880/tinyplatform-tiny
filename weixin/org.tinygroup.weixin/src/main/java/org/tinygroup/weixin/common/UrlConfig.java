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
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 访问微信的url相关配置信息
 * Created by luoguo on 2015/5/25.
 */
@XStreamAlias("url-config")
public class UrlConfig {
	
	/**
	 * 默认的上下文名称
	 */
	public static final String DEFAULT_CONTEXT_NAME="UrlConfig";
	
    /**
     * 某种操作的Key，必须唯一
     */
	@XStreamAsAttribute
    String key;
	
    /**
     * 要访问的URL
     */
    String url;
    
    /**
     * 访问URL的方法，比如：get，post等等
     */
	@XStreamAsAttribute
    String method;
	
	/**
	 * 配置证书的bean
	 */
	@XStreamAsAttribute
    @XStreamAlias("cert-bean")
	String certBean;

	/**
	 * 可能返回的结果类型，如果有多个采用英文逗号分隔
	 */
	@XStreamAlias("result-types")
    String resultTypes;

    public String getResultTypes() {
		return resultTypes;
	}

	public void setResultTypes(String resultTypes) {
		this.resultTypes = resultTypes;
	}

	public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public String getCertBean() {
		return certBean;
	}

	public void setCertBean(String certBean) {
		this.certBean = certBean;
	}
    
}
