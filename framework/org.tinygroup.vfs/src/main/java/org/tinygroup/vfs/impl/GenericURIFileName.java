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
package org.tinygroup.vfs.impl;

import org.tinygroup.vfs.util.UriParser;

/**
 * A file name that represents a 'generic' URI, as per RFC 2396. Consists of a
 * scheme, userinfo (typically username and password), hostname, port, and path.
 * 
 * @author <a href="http://commons.apache.org/vfs/team-list.html">Commons VFS
 *         team</a>
 */
public class GenericURIFileName {

	private static final char[] USERNAME_RESERVED = { ':', '@', '/' };
	private static final char[] PASSWORD_RESERVED = { '@', '/', '?' };
	private final String userName;
	private final String hostName;
	private final int defaultPort;
	private final String password;
	private final int port;
	private final String path;
	private final String scheme;

	public GenericURIFileName(final String scheme, final String hostName,
			final int port, final int defaultPort, final String userName,
			final String password, final String path) {
		this.scheme = scheme;
		this.path = path;
		this.hostName = hostName;
		this.defaultPort = defaultPort;
		this.password = password;
		this.userName = userName;
		if (port > 0) {
			this.port = port;
		} else {
			this.port = getDefaultPort();
		}
	}

	/**
	 * Returns the user name part of this name.
	 * 
	 * @return The user name.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns the password part of this name.
	 * 
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns the host name part of this name.
	 * 
	 * @return The host name.
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Returns the port part of this name.
	 * 
	 * @return The port number.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Returns the default port for this file name.
	 * 
	 * @return The default port number.
	 */
	public int getDefaultPort() {
		return defaultPort;
	}

	public String getPath() {
		return path;
	}

	public String getScheme() {
		return scheme;
	}

	/**
	 * Builds the root URI for this file name.
	 */
	protected void appendRootUri(final StringBuilder buffer, boolean addPassword) {
		buffer.append(getScheme());
		buffer.append("://");
		appendCredentials(buffer, addPassword);
		buffer.append(hostName);
		if (port != getDefaultPort()) {
			buffer.append(':');
			buffer.append(port);
		}
	}

	/**
	 * append the user credentials
	 */
	protected void appendCredentials(StringBuilder buffer, boolean addPassword) {
		if (userName != null && userName.length() != 0) {
			UriParser.appendEncoded(buffer, userName, USERNAME_RESERVED);
			if (password != null && password.length() != 0) {
				buffer.append(':');
				if (addPassword) {
					UriParser
							.appendEncoded(buffer, password, PASSWORD_RESERVED);
				} else {
					buffer.append("***");
				}
			}
			buffer.append('@');
		}
	}

}
