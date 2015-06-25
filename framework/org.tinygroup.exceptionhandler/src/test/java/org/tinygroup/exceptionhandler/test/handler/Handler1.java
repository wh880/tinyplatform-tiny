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
package org.tinygroup.exceptionhandler.test.handler;

import org.tinygroup.event.Event;
import org.tinygroup.exceptionhandler.ExceptionHandler;
import org.tinygroup.exceptionhandler.test.exception.Exception1;
import org.tinygroup.exceptionhandler.test.util.ResultUtil;

public class Handler1 implements ExceptionHandler<Exception1> {

	public void handle(Exception1 t, Event event) {
		System.out.println("e1:"+t.getMessage());
		ResultUtil.plus(1);
	}

}
