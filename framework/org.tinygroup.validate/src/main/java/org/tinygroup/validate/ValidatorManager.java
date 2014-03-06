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
package org.tinygroup.validate;


/**
 * 验证管理接口
 * @author renhui
 *
 */
public interface ValidatorManager {

	String VALIDATOR_MANAGER_BEAN_NAME = "complexValidatorManager";
	String XSTEAM_PACKAGE_NAME = "validate";
	String FIELD_TITLE_KEY = "field_title_key";
	/**
	 * 设置存储校验映射存储对象
	 * @param storage
	 */
    void setValidatorMapStorage(ValidatorMapStorage storage);	

	/**
	 * 校验接口
	 * @param object 校验场景
	 * @param object 需要校验的对象
	 * @param result 校验的结果
	 */
	void validate(String scene,Object object, ValidateResult result);

	/**
	 * 校验接口
	 * @param object 需要校验的对象
	 * @param result 校验的结果
	 */

	void validate(Object object, ValidateResult result);
}
