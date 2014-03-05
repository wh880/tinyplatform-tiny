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
package org.tinygroup.vfs.net.ftp.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.tinygroup.vfs.impl.ftp.FtpClientFactory;

public class TinyFTPClient {

	public TinyFTPClient(String hostname, Integer port, String username,
			String password) {
		this.hostname = hostname;
		if (port == null) {
			this.port = DEFAULT_PORT;
		} else {
			this.port = port.intValue();
		}
		if (username == null || username.trim().length() == 0) {
			this.username = DEFAULT_USERNAME;
		} else {
			this.username = username;
		}
		if (password == null || password.trim().length() == 0) {
			this.password = DEFAULT_PASSWORD;
		} else {
			this.password = password;
		}
	}

	public FTPFile[] listFiles(String pathname) {
		FTPClient ftpClient = getFTPClient();
		try {
			if (ftpClient != null) {
				FTPFile[] ftpFiles = ftpClient.listFiles(pathname);
				return ftpFiles;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public InputStream retrieveFileStream(String remote) {
		FTPClient ftpClient = getFTPClient();
		if (ftpClient != null) {
			try {
				InputStream is = ftpClient.retrieveFileStream(remote);
				// ftpClient.completePendingCommand();
				return is;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
		return null;
	}

	private static final int DEFAULT_PORT = 21;
	private static final long DEFAULT_EXCEED_TIME = 3000000;
	private static final int MAX_SIZE = 5;
	private static final String DEFAULT_USERNAME = "anonymous";
	private static final String DEFAULT_PASSWORD = "anonymous";
	private static final Map<String, TinyFTPConnection[]> POOL = new HashMap<String, TinyFTPConnection[]>();
	private String hostname;
	private int port;
	private String username;
	private String password;
	private TinyFTPConnection connection;

	private static class TinyFTPConnection {
		TinyFTPConnection(FTPClient client) {
			latelyUseTime = System.currentTimeMillis();
			this.client = client;
		}

		private long latelyUseTime;
		private boolean idle = true;
		private FTPClient client;
	}

	private static String connectionInfo(String hostname, Integer port,
			String username, String password) {
		StringBuilder info = new StringBuilder();
		info.append(username).append(":").append(password);
		info.append("@").append(hostname).append(":").append(port);
		return info.toString();
	}

	private FTPClient getFTPClient() {
		String connectionInfo = connectionInfo(hostname, port, username,
				password);
		TinyFTPConnection[] conns = POOL.get(connectionInfo);
		if (conns == null) {
			conns = new TinyFTPConnection[MAX_SIZE];
			POOL.put(connectionInfo, conns);
			connection = newFTPClient(conns); // 增加连接
		} else {
			TinyFTPConnection conn = null;
			int i = 0;
			for (; i < conns.length; i++) {
				conn = conns[i];
				if (!conn.idle) {
					// 正在使用中
					;
				} else if ((System.currentTimeMillis() - conn.latelyUseTime) > DEFAULT_EXCEED_TIME) {
					// 过期
					conns[i] = null; // 从连接池中删除
					try {
						conn.client.disconnect(); // 断开连接
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					conn.idle = false;
					connection = conn;
					break;
				}
			}
			if (i == conns.length) {
				// 没有可用的
				newFTPClient(conns); // 增加连接
			}
		}
		if (connection != null) {
			return connection.client;
		}
		return null;
	}

	private TinyFTPConnection newFTPClient(TinyFTPConnection[] conns) {
		// 新的连接
		FTPClient ftpClient = null;
		try {
			ftpClient = FtpClientFactory.createConnection(hostname, port,
					username, password, "/");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		TinyFTPConnection conn = new TinyFTPConnection(ftpClient);

		for (int i = 0; i < conns.length; i++) {
			if (conns[i] == null) {
				conns[i] = conn;
				return conn;
			}
		}

		return null;
	}

	private void close() {
		if (connection != null) {
			connection.idle = true;
			connection.latelyUseTime = System.currentTimeMillis();
		}
	}

}
