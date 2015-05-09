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

import org.tinygroup.event.central.Node;
import org.tinygroup.exception.ErrorCode;
import org.tinygroup.exception.TinySysRuntimeException;

public class CEPConnectException extends TinySysRuntimeException {
	private Node node;
	private Exception exception;

	public CEPConnectException(Exception e, Node node) {
		super(ErrorCode.UNKNOWN_ERROR, e, node.toString(), e
				.getMessage());
		this.node = node;
		this.exception = e;
	}

	public Node getNode() {
		return node;
	}

	public Exception getException() {
		return exception;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6589362229300815033L;

}
