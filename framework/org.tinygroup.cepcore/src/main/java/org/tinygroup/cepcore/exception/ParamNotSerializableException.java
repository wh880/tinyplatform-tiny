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

public class ParamNotSerializableException extends BaseRuntimeException{

	public ParamNotSerializableException(String serviceId,String paramName){
		//TODO:此处第二个参数被当成了defaultMessage，需要重构父类解决
		super(CEPCoreExceptionCode.PARAM_NOT_SERIALIZABLE_EXCEPTION_CODE, serviceId,paramName);
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5136744538649261870L;

}
