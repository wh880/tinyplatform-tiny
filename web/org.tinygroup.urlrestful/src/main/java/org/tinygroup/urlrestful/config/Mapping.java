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
package org.tinygroup.urlrestful.config;

import org.tinygroup.commons.tools.StringUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/**
 * @author renhui
 *
 */
@XStreamAlias("mapping")
public class Mapping {

	public static final String TEXT_HTML = "text/html";

	@XStreamAsAttribute
	@XStreamAlias("url")
	private String url;
	@XStreamAsAttribute
	@XStreamAlias("method")
	private String method;
	@XStreamAsAttribute
	private String accept;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAccept() {
		if(StringUtil.isBlank(accept)){
			accept=TEXT_HTML;
		}
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}
	
}
