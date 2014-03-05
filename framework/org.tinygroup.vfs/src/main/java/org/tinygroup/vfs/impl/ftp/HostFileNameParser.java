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

import org.tinygroup.commons.cryptor.Cryptor;
import org.tinygroup.commons.cryptor.DefaultCryptor;
import org.tinygroup.exception.TinySysRuntimeException;
import org.tinygroup.vfs.impl.GenericURIFileName;
import org.tinygroup.vfs.util.UriParser;

/**
 * Implementation for any url based filesystem.<br />
 * Parses the url into user/password/host/port/path<br />
 * Does not handle a query string (after ?)
 *
 * @author <a href="http://commons.apache.org/vfs/team-list.html">Commons VFS team</a>
 * @see URLFileNameParser URLFileNameParser for the implementation which also handles the query string too
 */
public class HostFileNameParser 
{
    private final int defaultPort;

    public HostFileNameParser(final int defaultPort)
    {
        this.defaultPort = defaultPort;
    }

    public int getDefaultPort()
    {
        return defaultPort;
    }

    public boolean encodeCharacter(char ch)
    {
        return ch == '%';
    }

    public GenericURIFileName parseUri(final String filename)
    {
        // FTP URI are generic URI (as per RFC 2396)
        final StringBuilder name = new StringBuilder();

        // Extract the scheme and authority parts
        final Authority auth = extractToPath(filename, name);

        // Decode and normalise the file name
        UriParser.canonicalizePath(name, 0, name.length(), this);
        UriParser.fixSeparators(name);
        final String path = name.toString();

        return new GenericURIFileName(
            auth.scheme,
            auth.hostName,
            auth.port,
            defaultPort,
            auth.userName,
            auth.password,
            path);
    }

    /**
     * Extracts the scheme, userinfo, hostname and port components of a
     * generic URI.
     *
     * @param uri  The absolute URI to parse.
     * @param name Used to return the remainder of the URI.
     */
    protected Authority extractToPath(final String uri,
                                      final StringBuilder name)
    {
        final Authority auth = new Authority();

        // Extract the scheme
        auth.scheme = UriParser.extractScheme(uri, name);

        // Expecting "//"
        if (name.length() < 2 || name.charAt(0) != '/' || name.charAt(1) != '/')
        {
            throw new TinySysRuntimeException("vfs.provider/missing-double-slashes.error", uri);
        }
        name.delete(0, 2);

        // Extract userinfo, and split into username and password
        final String userInfo = extractUserInfo(name);
        final String userName;
        final String password;
        if (userInfo != null)
        {
            int idx = userInfo.indexOf(':');
            if (idx == -1)
            {
                userName = userInfo;
                password = null;
            }
            else
            {
                userName = userInfo.substring(0, idx);
                password = userInfo.substring(idx + 1);
            }
        }
        else
        {
            userName = null;
            password = null;
        }
        auth.userName = UriParser.decode(userName);
        auth.password = UriParser.decode(password);

        if (auth.password != null && auth.password.startsWith("{") && auth.password.endsWith("}"))
        {
            try
            {
                Cryptor cryptor = new DefaultCryptor();
                auth.password = cryptor.decrypt(auth.password.substring(1, auth.password.length() - 1));
            }
            catch (Exception ex)
            {
                throw new TinySysRuntimeException("Unable to decrypt password", ex);
            }
        }

        // Extract hostname, and normalise (lowercase)
        final String hostName = extractHostName(name);
        if (hostName == null)
        {
            throw new TinySysRuntimeException("vfs.provider/missing-hostname.error", uri);
        }
        auth.hostName = hostName.toLowerCase();

        // Extract port
        auth.port = extractPort(name, uri);

        // Expecting '/' or empty name
        if (name.length() > 0 && name.charAt(0) != '/')
        {
            throw new TinySysRuntimeException("vfs.provider/missing-hostname-path-sep.error", uri);
        }

        return auth;
    }

    /**
     * Extracts the user info from a URI.  The scheme:// part has been removed
     * already.
     */
    protected String extractUserInfo(final StringBuilder name)
    {
        final int maxlen = name.length();
        for (int pos = 0; pos < maxlen; pos++)
        {
            final char ch = name.charAt(pos);
            if (ch == '@')
            {
                // Found the end of the user info
                String userInfo = name.substring(0, pos);
                name.delete(0, pos + 1);
                return userInfo;
            }
            if (ch == '/' || ch == '?')
            {
                // Not allowed in user info
                break;
            }
        }

        // Not found
        return null;
    }

    /**
     * Extracts the hostname from a URI.  The scheme://userinfo@ part has
     * been removed.
     */
    protected String extractHostName(final StringBuilder name)
    {
        final int maxlen = name.length();
        int pos = 0;
        for (; pos < maxlen; pos++)
        {
            final char ch = name.charAt(pos);
            if (ch == '/' || ch == ';' || ch == '?' || ch == ':'
                || ch == '@' || ch == '&' || ch == '=' || ch == '+'
                || ch == '$' || ch == ',')
            {
                break;
            }
        }
        if (pos == 0)
        {
            return null;
        }

        final String hostname = name.substring(0, pos);
        name.delete(0, pos);
        return hostname;
    }

    /**
     * Extracts the port from a URI.  The scheme://userinfo@hostname
     * part has been removed.
     *
     * @return The port, or -1 if the URI does not contain a port.
     */
    protected int extractPort(final StringBuilder name, final String uri) 
    {
        if (name.length() < 1 || name.charAt(0) != ':')
        {
            return -1;
        }

        final int maxlen = name.length();
        int pos = 1;
        for (; pos < maxlen; pos++)
        {
            final char ch = name.charAt(pos);
            if (ch < '0' || ch > '9')
            {
                break;
            }
        }

        final String port = name.substring(1, pos);
        name.delete(0, pos);
        if (port.length() == 0)
        {
            throw new TinySysRuntimeException("vfs.provider/missing-port.error", uri);
        }

        return Integer.parseInt(port);
    }

    /**
     * Parsed authority info (scheme, hostname, userinfo, port)
     */
    protected static class Authority
    {
        private String scheme;
        private String hostName;
        private String userName;
        private String password;
        private int port;

        /** @since 2.0 */
        public String getScheme()
        {
            return scheme;
        }

        /** @since 2.0 */
        public void setScheme(String scheme)
        {
            this.scheme = scheme;
        }

        /** @since 2.0 */
        public String getHostName()
        {
            return hostName;
        }

        /** @since 2.0 */
        public void setHostName(String hostName)
        {
            this.hostName = hostName;
        }

        /** @since 2.0 */
        public String getUserName()
        {
            return userName;
        }

        /** @since 2.0 */
        public void setUserName(String userName)
        {
            this.userName = userName;
        }

        /** @since 2.0 */
        public String getPassword()
        {
            return password;
        }

        /** @since 2.0 */
        public void setPassword(String password)
        {
            this.password = password;
        }

        /** @since 2.0 */
        public int getPort()
        {
            return port;
        }

        /** @since 2.0 */
        public void setPort(int port)
        {
            this.port = port;
        }
    }
}
