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
package org.tinygroup.vfs.impl.ftp;

import java.io.IOException;
import java.util.TimeZone;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.tinygroup.exception.TinySysRuntimeException;

/**
 * Create a FtpClient instance.
 * 
 * @author <a href="http://commons.apache.org/vfs/team-list.html">Commons VFS
 *         team</a>
 */
public final class FtpClientFactory {

	private FtpClientFactory() {
	}

	/**
	 * Creates a new connection to the server.
	 * 
	 * @param hostname
	 *            The host name of the server.
	 * @param port
	 *            The port to connect to.
	 * @param username
	 *            The name of the user for authentication.
	 * @param password
	 *            The user's password.
	 * @param workingDirectory
	 *            The base directory.
	 * @param fileSystemOptions
	 *            The FileSystemOptions.
	 * @return An FTPClient.
	 * @throws IOException
	 * @throws FileSystemException
	 *             if an error occurs while connecting.
	 */
	public static FTPClient createConnection(String hostname, int port,
			String username, String password, String workingDirectory)
			throws IOException {
		// Determine the username and password to use
		if (username == null || username.trim().length() == 0) {
			username = "anonymous";
		}
		if (password == null || password.trim().length() == 0) {
			password = "anonymous";
		}
		// try {
		final FTPClient client = new FTPClient(); // 新建连接
		configureClient(client); // 连接配置
		try {
			client.connect(hostname, port);
			int reply = client.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				throw new TinySysRuntimeException(
						"vfs.provider.ftp/connect-rejected.error", hostname);
			}
			// Login
			if (!client.login(username, password)) {
				throw new TinySysRuntimeException(
						"vfs.provider.ftp/login.error", new Object[] {
								hostname, username }, null);
			}
			// Set binary mode
			if (!client.setFileType(FTP.BINARY_FILE_TYPE)) {
				throw new TinySysRuntimeException(
						"vfs.provider.ftp/set-binary.error", hostname);
			}
			// Change to root by default
			// All file operations a relative to the filesystem-root
			// String root = getRoot().getName().getPath();
			// if (!client.changeWorkingDirectory(workingDirectory)) {
			// throw new TinySysRuntimeException(
			// "vfs.provider.ftp/change-work-directory.error",
			// workingDirectory);
			// }
		} catch (final IOException e) {
			e.printStackTrace();
			if (client.isConnected()) {
				try {
					client.disconnect();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			throw e;
		}
		return client;
		// } catch (final Exception exc) {
		// throw new TinySysRuntimeException("vfs.provider.ftp/connect.error",
		// new Object[] { hostname }, exc);
		// }
	}

	private static void configureClient(FTPClient client) {
		FTPClientConfig ftpClientConfig = new FTPClientConfig();
		ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		client.configure(ftpClientConfig);
	}
}
