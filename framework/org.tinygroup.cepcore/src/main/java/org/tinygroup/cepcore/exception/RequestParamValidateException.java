/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
package org.tinygroup.cepcore.exception;

import org.tinygroup.cepcoreexceptioncode.CEPCoreExceptionCode;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.validate.ValidateResult;

public class RequestParamValidateException extends BaseRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ValidateResult result;

	public RequestParamValidateException(ValidateResult result) {
		//TODO:目前此errorCode被当成了message
		super(CEPCoreExceptionCode.PARAM_VALIDATE_EXCEPTION_CODE);
		this.result = result;
	}

	public ValidateResult getResult() {
		return result;
	}

}
