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
package org.tinygroup.flow.test.testcase.component;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;

public class CreateExceptionComponent implements ComponentInterface{
	int exceptionNo;
	
	public int getExceptionNo() {
		return exceptionNo;
	}

	public void setExceptionNo(int exceptionNo) {
		this.exceptionNo = exceptionNo;
	}

	public void execute(Context context) {
		switch (exceptionNo) {
		case 0:
			throw new ExceptionNew0();
		case 1:
			throw new ExceptionNew1();
		case 2:
			throw new ExceptionNew2();	
		case 3:
			throw new ExceptionNew3InOtherNode();	
		default:
			throw new ExceptionNew4InOtherFlow();	
		}
	}

}
