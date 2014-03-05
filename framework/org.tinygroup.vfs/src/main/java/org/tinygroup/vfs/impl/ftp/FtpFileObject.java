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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.tinygroup.exception.TinySysRuntimeException;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.SchemaProvider;
import org.tinygroup.vfs.impl.GenericURIFileName;
import org.tinygroup.vfs.impl.URLFileObject;

/**
 * 
 * 功能说明: ftp资源
 * <p>
 * 
 * 开发人员: renhui <br>
 * 开发时间: 2013-8-29 <br>
 * <br>
 */
public class FtpFileObject extends URLFileObject {

	private static final int DEFAULT_PORT = 21;
	private static final int DEFAULT_CLIENT_BUFFER_SIZE = 100000;
	private static final String DEFAULT_CLIENT_CONTROL_ENCODING = "utf-8";
	private static final String DEFAULT_WORKING_DIRECTORY = "/";
	private GenericURIFileName uriFileName;
	private String resource;
	FTPClient client;
	private FTPFile ftpFile;

	public FtpFileObject(SchemaProvider schemaProvider, String resource)
			throws TinySysRuntimeException {
		super(schemaProvider, resource);
		this.resource = resource;
		init();
	}

	public FtpFileObject(FtpFileObject parent, String resource)
			throws IOException {
		super(parent.getSchemaProvider(), resource);
		this.resource = resource;
		setParent(parent);
		init();
	}

	private void init() throws TinySysRuntimeException {
		uriFileName = new HostFileNameParser(DEFAULT_PORT).parseUri(resource); // 初始uriFileName
		getAbsolutePath(); // 初始absolutePath
		initFTPClient(); // 初始client
		initFTPFile();// 初始ftpFile
	}

	private void initFTPClient() throws TinySysRuntimeException {
		FtpFileObject parent = (FtpFileObject) getParent();
		if (parent != null) {
			client = parent.client;
		} else {
			try {
				client = FtpClientFactory.createConnection(
						uriFileName.getHostName(), uriFileName.getPort(),
						uriFileName.getUserName(), uriFileName.getPassword(),
						DEFAULT_WORKING_DIRECTORY);
				client.setBufferSize(DEFAULT_CLIENT_BUFFER_SIZE);
				client.setControlEncoding(DEFAULT_CLIENT_CONTROL_ENCODING);
			} catch (IOException e) {
				e.printStackTrace();
				throw new TinySysRuntimeException("获取ftp客户端失败！");
			}
		}

	}

	private void initFTPFile() throws TinySysRuntimeException {
		FTPFile[] ftpFiles = null;
		String pathname = uriFileName.getPath();

		try {
			ftpFiles = client.listFiles(recode(pathname));
		} catch (IOException e) {
			e.printStackTrace();
			throw new TinySysRuntimeException("获取文件失败！");
		}

		// 路径为文件
		if (ftpFiles != null && ftpFiles.length == 1) {
			ftpFile = ftpFiles[0];
			if (ftpFile.isFile() && pathname.endsWith(ftpFile.getName())) {
				return;
			}
		}

		// 获取上级路径
		int index = pathname.lastIndexOf("/");
		if (index == pathname.length() - 1) {
			pathname = pathname.substring(0, index);
			index = pathname.lastIndexOf("/");
		}
		String parentPathname = pathname.substring(0, index + 1);
		String subFilename = pathname.substring(index + 1);
		FTPFileFilterByName filter = new FTPFileFilterByName(subFilename); // 过滤器
		try {
			client.enterLocalPassiveMode();
			ftpFiles = client.listFiles(recode(parentPathname), filter);
			if (ftpFiles == null || ftpFiles.length == 0) {
				throw new TinySysRuntimeException("没有找到对应的文件或文件夹！");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new TinySysRuntimeException("获取文件失败！");
		}
		ftpFile = ftpFiles[0];
	}

	public String getFileName() {
		if (fileName == null) {
			fileName = ftpFile.getName();
		}
		return fileName;
	}

	public String getPath() {
		if (path == null) {
			// if (uriFileName != null) {
			// path = uriFileName.getPath();
			// } else {
			// path = super.getPath();
			// }
			path = uriFileName.getPath();
		}
		return path;
	}

	public String getAbsolutePath() {
		if (absolutePath == null) {
			absolutePath = "ftp://" + uriFileName.getHostName() + ":"
					+ uriFileName.getPort() + uriFileName.getPath();
		}
		return absolutePath;
	}

	public boolean isFolder() {
		if (isFolder == null) {
			if (ftpFile.isFile()) {
				isFolder = false;
			} else {
				isFolder = true;
			}
		}
		return isFolder;
	}

	public InputStream getInputStream() {
		if (isFolder()) {
			return null;
		}

		InputStream is = null;
		try {
			String remote = getPath();
			FTPClient newClient = FtpClientFactory.createConnection(
					uriFileName.getHostName(), uriFileName.getPort(),
					uriFileName.getUserName(), uriFileName.getPassword(), "/");
			newClient.setBufferSize(DEFAULT_CLIENT_BUFFER_SIZE);
			is = newClient.retrieveFileStream(new String(remote
					.getBytes("UTF-8"), "iso-8859-1"));
			// if (is != null) {
			// is.close();
			// newClient.completePendingCommand();
			// }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return is;
	}

	public OutputStream getOutputStream() {
		if (isFolder()) {
			return null;
		}

		OutputStream os = null;
		try {
			String remote = getPath();
			FTPClient newClient = FtpClientFactory.createConnection(
					uriFileName.getHostName(), uriFileName.getPort(),
					uriFileName.getUserName(), uriFileName.getPassword(), "/");
			newClient.setBufferSize(DEFAULT_CLIENT_BUFFER_SIZE);
			os = newClient.appendFileStream(new String(
					remote.getBytes("UTF-8"), "iso-8859-1"));
			// if (os != null) {
			// os.close();
			// newClient.completePendingCommand();
			// }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return os;
	}

	@SuppressWarnings({ "restriction" })
	public long getLastModifiedTime() {
		try {
			sun.net.www.protocol.ftp.FtpURLConnection con = (sun.net.www.protocol.ftp.FtpURLConnection) url
					.openConnection();
			lastModified = con.getLastModified();
			return lastModified;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public long getSize() {
		if (fileSize > 0) {
			return fileSize;
		} else if (isFolder()) {
			return 0;
		}

		fileSize = ftpFile.getSize();
		return fileSize;
	}

	public List<FileObject> getChildren() {
		if (!isFolder()) {
			return null;
		}
		if (children == null) {
			try {
				String relPath = getPath();
				// FTPFile[] files = listFilesInDirectory(relPath);
				FTPFile[] files = client.listFiles(recode(relPath));
				if (files == null || files.length == 0) {
					return null;
				}
				children = new ArrayList<FileObject>();
				String childResource = null;
				for (int i = 0; i < files.length; i++) {
					FTPFile ftpFile = files[i];
					String filename = ftpFile.getName();
					if (filename == null || filename.startsWith(".")) {
						continue;
					}
					if (resource.endsWith("/")) {
						childResource = resource + filename;
					} else {
						childResource = resource + "/" + filename;
					}
					FtpFileObject fileObject = new FtpFileObject(this,
							childResource);
					children.add(fileObject);
				}
			} catch (IOException e) {
				throw new TinySysRuntimeException(e);
			}
		}
		return children;
	}

	public void close() {
		if (client.isConnected()) {
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// private String getResource(String filename) {
	// if (isFolder()) {
	// if (resource.endsWith("/")) {
	// return resource + filename;
	// } else {
	// return resource + "/" + filename;
	// }
	// } else {
	// int index = resource.lastIndexOf("/");
	// return resource.substring(0, index) + filename;
	// }
	// }

	private String recode(String str) {
		try {
			return new String(str.getBytes("UTF-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}

}
