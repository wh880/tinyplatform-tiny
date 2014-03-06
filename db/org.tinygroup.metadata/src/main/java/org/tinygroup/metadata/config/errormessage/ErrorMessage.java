/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.metadata.config.errormessage;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 错误信息
 * @author luoguo
 *
 */
@XStreamAlias("error-message")
public class ErrorMessage {
	@XStreamAsAttribute
	@XStreamAlias("error-id")
	private String errorId;// 错误ID
	@XStreamAsAttribute
	@XStreamAlias("error-message-i18n-key")
	private String errorMessageI18nKey;// i18n标识
	@XStreamAsAttribute
	@XStreamAlias("resolution-i18n-key")
	private String resolutionI18nKey;// 解决方案

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getErrorMessageI18nKey() {
		return errorMessageI18nKey;
	}

	public void setErrorMessageI18nKey(String errorMessageI18nKey) {
		this.errorMessageI18nKey = errorMessageI18nKey;
	}

	public String getResolutionI18nKey() {
		return resolutionI18nKey;
	}

	public void setResolutionI18nKey(String resolutionI18nKey) {
		this.resolutionI18nKey = resolutionI18nKey;
	}

}
