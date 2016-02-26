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
package org.tinygroup.httpvisitor.execption;

/**
 * HttpVisitor基本异常
 * @author yancheng11334
 *
 */
public class HttpVisitorException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 865684453491236817L;

	public HttpVisitorException(String message) {
        super(message);
    }

    public HttpVisitorException(Exception exception) {
        super(exception);
    }

    public HttpVisitorException(String message, Exception e) {
        super(message, e);
    }
}
