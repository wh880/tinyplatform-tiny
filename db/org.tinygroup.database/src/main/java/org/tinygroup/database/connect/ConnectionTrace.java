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
package org.tinygroup.database.connect;

import java.sql.Connection;
import java.util.Date;


/**
 * 具体的连接监控信息
 * @author renhui
 *
 */
public class ConnectionTrace {

	private Connection connection;
	private java.util.Date createDate;
	private long threadId;
	private String threadNameString;
	private String stackTrace;
	public ConnectionTrace(Connection connection, Date createDate, long threadId,
			String threadNameString, String stackTrace) {
		super();
		this.connection = connection;
		this.createDate = createDate;
		this.threadId = threadId;
		this.threadNameString = threadNameString;
		this.stackTrace = stackTrace;
	}
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public long getThreadId() {
		return threadId;
	}
	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}
	public String getThreadNameString() {
		return threadNameString;
	}
	public void setThreadNameString(String threadNameString) {
		this.threadNameString = threadNameString;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
}
