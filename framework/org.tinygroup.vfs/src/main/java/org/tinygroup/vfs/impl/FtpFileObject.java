package org.tinygroup.vfs.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.tinygroup.exception.TinySysRuntimeException;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.SchemaProvider;
import org.tinygroup.vfs.impl.URLFileObject;

/**
 * Created by luoguo on 14-3-31.
 */
public class FtpFileObject extends URLFileObject {

	private String resource;
	private FTPFile ftpFile;
	private FTPClient ftpClient;

	/*************************************************************************************/
	public FtpFileObject(SchemaProvider schemaProvider, String resource) {
		super(schemaProvider, resource);
		this.resource = resource;
		connectFtpServer();
		initFTPFile();
	}

	public FtpFileObject(FtpFileObject parent, String resource)
			throws IOException {
		this(parent.getSchemaProvider(), resource);
		setParent(parent);
	}

	private void connectFtpServer() {
		ftpClient = newFtpClient();
	}

	private void initFTPFile() throws TinySysRuntimeException {
		FTPFile[] ftpFiles = null;
		String pathname = url.getPath();

		try {
			ftpFiles = ftpClient.listFiles(recode(pathname));
		} catch (IOException e) {
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
			ftpClient.enterLocalPassiveMode();
			ftpFiles = ftpClient.listFiles(recode(parentPathname), filter);
			if (ftpFiles == null || ftpFiles.length == 0) {
				throw new TinySysRuntimeException("没有找到对应的文件或文件夹！");
			}
		} catch (IOException e) {
			throw new TinySysRuntimeException("获取文件失败！");
		}
		ftpFile = ftpFiles[0];
	}

	private FTPClient newFtpClient() {
		try {
			FTPClient ftpClient = new FTPClient();
			FTPClientConfig ftpClientConfig = new FTPClientConfig();
			ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
			ftpClient.configure(ftpClientConfig);
			if (url.getPort() <= 0) {
				ftpClient.connect(url.getHost());
			} else {
				ftpClient.connect(url.getHost(), url.getPort());
			}
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				throw new RuntimeException("连接失败！");
			}
			if (url.getUserInfo() != null) {
				String userInfo[] = url.getUserInfo().split(":");
				String userName = null;
				String password = null;
				if (userInfo.length >= 1) {
					userName = userInfo[0];
				}
				if (userInfo.length >= 2) {
					password = userInfo[1];
				}
				if (!ftpClient.login(userName, password)) {
					throw new RuntimeException("登录失败：" + url.toString());
				}
				if (!ftpClient.setFileType(FTP.BINARY_FILE_TYPE)) {
					throw new RuntimeException("设置二进制类型失败");
				}
				ftpClient.setBufferSize(100000);
				ftpClient.setControlEncoding("utf-8");
			}
			return ftpClient;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*************************************************************************************/
	public String getAbsolutePath() {
		if (url != null) {// 如果是根结点
			return url.getPath();
		}
		String parentAbsolutePath = parent.getAbsolutePath();
		if (parentAbsolutePath.endsWith("/"))
			return parentAbsolutePath + ftpFile.getName();
		else
			return parentAbsolutePath + "/" + ftpFile.getName();
	}

	public String getPath() {
		if (path == null) {
			// if (uriFileName != null) {
			// path = uriFileName.getPath();
			// } else {
			// path = super.getPath();
			// }
			path = url.getPath();
		}
		return path;
	}

	public String getFileName() {
		if (fileName == null) {
			fileName = ftpFile.getName();
		}
		return fileName;
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

	public InputStream getInputStream() {
		if (isFolder()) {
			return null;
		}

		InputStream is = null;
		try {
			String remote = getPath();
			FTPClient client = newFtpClient();
			is = client.retrieveFileStream(new String(remote.getBytes("UTF-8"),
					"iso-8859-1"));
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
			FTPClient client = newFtpClient();
			os = client.appendFileStream(new String(remote.getBytes("UTF-8"),
					"iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return os;
	}

	public List<FileObject> getChildren() {
		if (!isFolder()) {
			return null;
		}
		if (children == null) {
			try {
				String relPath = getPath();
				// FTPFile[] files = listFilesInDirectory(relPath);
				FTPFile[] files = ftpClient.listFiles(recode(relPath));
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

	private String recode(String str) {
		try {
			return new String(str.getBytes("UTF-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
}
