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
package org.tinygroup.vfs.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.SocketException;
import java.util.Properties;
import java.util.TimeZone;

//import open.mis.data.DownloadStatus;
//import open.mis.data.UploadStatus;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileEntryParser;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.parser.DefaultFTPFileEntryParserFactory;
import org.apache.commons.net.ftp.parser.FTPFileEntryParserFactory;

/** */
/**
 * 支持断点续传的FTP实用类
 * 
 * @author BenZhou http://www.bt285.cn
 * @version 0.1 实现基本断点上传下载
 * @version 0.2 实现上传下载进度汇报
 * @version 0.3 实现中文目录创建及中文文件创建，添加对于中文的支持
 */
public class FtpUtil {
	public FTPClient ftpClient = new FTPClient();

	public FtpUtil() {
		// 设置将过程中使用到的命令输出到控制台
		this.ftpClient.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out)));
	}

	/** */
	/**
	 * 连接到FTP服务器
	 * 
	 * @param hostname
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否连接成功
	 * @throws IOException
	 */
	public boolean connect(String hostname, int port, String username,
			String password) throws IOException {
		ftpClient.connect(hostname, port);
		ftpClient.setControlEncoding("GBK");
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login("ftpuser", "123456")) {
				return true;
			}
		}
		// disconnect();
		return false;
	}

	/** */
	/**
	 * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
	 * 
	 * @param remote
	 *            远程文件路径
	 * @param local
	 *            本地文件路径
	 * @return 上传的状态
	 * @throws IOException
	 */
	public void download(String remote, String local) throws IOException {
		// 设置被动模式
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		// ftpClient.changeWorkingDirectory("/logs");

		FTPFile[] files = ftpClient.listFiles();
		files = ftpClient.listDirectories();
		files = ftpClient.listFiles("*");

		// 检查远程文件是否存在
		files = ftpClient.listFiles(new String(remote.getBytes("GBK"),
				"iso-8859-1"));
		if (files.length != 1) {
			System.out.println("远程文件不存在");
		}

		long lRemoteSize = files[0].getSize();
		File f = new File(local);
		// 本地存在文件，进行断点下载
		if (f.exists()) {
			long localSize = f.length();
			// 判断本地文件大小是否大于远程文件大小
			if (localSize >= lRemoteSize) {
				System.out.println("本地文件大于远程文件，下载中止");
			}

			// 进行断点续传，并记录状态
			FileOutputStream out = new FileOutputStream(f, true);
			ftpClient.setRestartOffset(localSize);
			InputStream in = ftpClient.retrieveFileStream(new String(remote
					.getBytes("GBK"), "iso-8859-1"));
			byte[] bytes = new byte[1024];
			long step = lRemoteSize / 100;
			long process = localSize / step;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10 == 0)
						System.out.println("下载进度：" + process);
					// TODO 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			boolean isDo = ftpClient.completePendingCommand();
			if (isDo) {
			} else {
			}
		} else {
			OutputStream out = new FileOutputStream(f);
			InputStream in = ftpClient.retrieveFileStream(new String(remote
					.getBytes("GBK"), "iso-8859-1"));
			byte[] bytes = new byte[1024];
			long step = lRemoteSize / 100;
			long process = 0;
			long localSize = 0L;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10 == 0)
						System.out.println("下载进度：" + process);
					// TODO 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			boolean upNewStatus = ftpClient.completePendingCommand();
			if (upNewStatus) {
			} else {
			}
		}
	}

	/** */
	/**
	 * 上传文件到FTP服务器，支持断点续传
	 * 
	 * @param local
	 *            本地文件名称，绝对路径
	 * @param remote
	 *            远程文件路径，使用/home/directory1/subdirectory/file.ext或是
	 *            http://www.guihua.org /subdirectory/file.ext
	 *            按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
	 * @return 上传结果
	 * @throws IOException
	 */
	public void upload(String local, String remote) throws IOException {
		// 设置PassiveMode传输
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setControlEncoding("GBK");
		// 对远程目录的处理
		String remoteFileName = remote;
		if (remote.contains("/")) {
			remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
			// 创建服务器远程目录结构，创建失败直接返回
			// if (CreateDirecroty(remote, ftpClient) ==
			// UploadStatus.Create_Directory_Fail) {
			// return UploadStatus.Create_Directory_Fail;
			// }
		}

		// 检查远程是否存在文件
		FTPFile[] files = ftpClient.listFiles(new String(remoteFileName
				.getBytes("GBK"), "iso-8859-1"));
		if (files.length == 1) {
			long remoteSize = files[0].getSize();
			File f = new File(local);
			long localSize = f.length();
			if (remoteSize == localSize) {
				// return UploadStatus.File_Exits;
			} else if (remoteSize > localSize) {
				// return UploadStatus.Remote_Bigger_Local;
			}

			// 尝试移动文件内读取指针,实现断点续传
			// result = uploadFile(remoteFileName, f, ftpClient, remoteSize);

			// 如果断点续传没有成功，则删除服务器上文件，重新上传
			// if (result == UploadStatus.Upload_From_Break_Failed) {
			// if (!ftpClient.deleteFile(remoteFileName)) {
			// return UploadStatus.Delete_Remote_Faild;
			// }
			// result = uploadFile(remoteFileName, f, ftpClient, 0);
			// }
			// } else {
			// result = uploadFile(remoteFileName, new File(local), ftpClient,
			// 0);
		}
		// return result;
	}

	/** */
	/**
	 * 断开与远程服务器的连接
	 * 
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	/** */
	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径
	 * @param ftpClient
	 *            FTPClient对象
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	public void CreateDirecroty(String remote, FTPClient ftpClient)
			throws IOException {
		// UploadStatus status = UploadStatus.Create_Directory_Success;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equalsIgnoreCase("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory
						.getBytes("GBK"), "iso-8859-1"))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end)
						.getBytes("GBK"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						System.out.println("创建目录失败");
						// return UploadStatus.Create_Directory_Fail;
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);

				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
	}

	/** */
	/**
	 * 上传文件到服务器,新上传和断点续传
	 * 
	 * @param remoteFile
	 *            远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile
	 *            本地文件File句柄，绝对路径
	 * @param processStep
	 *            需要显示的处理进度步进值
	 * @param ftpClient
	 *            FTPClient引用
	 * @return
	 * @throws IOException
	 */
	public void uploadFile(String remoteFile, File localFile,
			FTPClient ftpClient, long remoteSize) throws IOException {
		// 显示进度的上传
		long step = localFile.length() / 100;
		long process = 0;
		long localreadbytes = 0L;
		RandomAccessFile raf = new RandomAccessFile(localFile, "r");
		OutputStream out = ftpClient.appendFileStream(new String(remoteFile
				.getBytes("GBK"), "iso-8859-1"));
		// 断点续传
		if (remoteSize > 0) {
			ftpClient.setRestartOffset(remoteSize);
			process = remoteSize / step;
			raf.seek(remoteSize);
			localreadbytes = remoteSize;
		}
		byte[] bytes = new byte[1024];
		int c;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localreadbytes += c;
			if (localreadbytes / step != process) {
				process = localreadbytes / step;
				System.out.println("上传进度:" + process);
				// TODO 汇报上传状态
			}
		}
		out.flush();
		raf.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();
		if (remoteSize > 0) {
			// status = result ? UploadStatus.Upload_From_Break_Success
			// : UploadStatus.Upload_From_Break_Failed;
		} else {
			// status = result ? UploadStatus.Upload_New_File_Success
			// : UploadStatus.Upload_New_File_Failed;
		}
	}

	public static void main_(String[] args) throws Exception {
		// FtpUtil myFtp = new FtpUtil();
		// try {
		// myFtp.connect("127.0.0.1", 21, "ftpuser", "123456");
		// myFtp.ftpClient.makeDirectory(new String("电视剧".getBytes("GBK"),
		// "iso-8859-1"));
		// myFtp.ftpClient.changeWorkingDirectory(new String("电视剧"
		// .getBytes("GBK"), "iso-8859-1"));
		// myFtp.ftpClient.makeDirectory(new String("走西口".getBytes("GBK"),
		// "iso-8859-1"));
		// myFtp.upload("http://www.5a520.cn /yw.flv", "/yw.flv");
		// myFtp.upload("http://www.5a520.cn /走西口24.mp4",
		// "/央视走西口/新浪网/走西口24.mp4");
		// myFtp.download("/央视走西口/新浪网/走西口24.mp4", "E:\\走西口242.mp4");
		// myFtp.download("SampleMessages.log4j", "E:\\走西口242.mp4");
		// myFtp.disconnect();
		// } catch (IOException e) {
		// System.out.println("连接FTP出错：" + e.getMessage());
		// }

		FTPClient ftpClient = new FTPClient();

		ftpClient.setControlEncoding("utf-8");
		// FTPClientConfig ftpClientConfig = new FTPClientConfig();
		// ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		// ftpClientConfig.setServerLanguageCode("zh");
		// ftpClient.configure(ftpClientConfig);

		ftpClient.connect("127.0.0.1", 21);
		int replyCode = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(replyCode)) {
			throw new Exception("FTP服务器拒绝连接！");
		}

		if (!ftpClient.login("ftpuser", "123456")) {
			throw new Exception("登陆FTP服务器失败！");
		}
		System.out.println("服务器主机：" + ftpClient.getPassiveHost());
		System.out.println("服务器端口：" + ftpClient.getPassivePort());
		System.out.println("ftpClient.getListHiddenFiles():"
				+ ftpClient.getListHiddenFiles());
		System.out.println("当前工作目录：" + ftpClient.printWorkingDirectory());
		FTPFile[] ftpFiles = ftpClient.listFiles("/开发项目/webservice改造/cxf框架重构/");
		ftpFiles = ftpClient.listFiles();
		System.out.println("当前工作目录下文件列表：");
		for (FTPFile ftpFile : ftpFiles) {
			String fileName = ftpFile.getName();
			// fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
			// fileName = new String(fileName.getBytes("utf-8"), "GBK");
			System.out.println("：" + fileName);
		}

		boolean result = ftpClient.changeWorkingDirectory("/开发项目/民生POC/images");
		if (result) {
			System.out.println("工作目录变换成功！");
		} else {
			System.out.println("工作目录变换失败！");
		}
		ftpClient.setListHiddenFiles(true);
		System.out.println("当前工作目录：" + ftpClient.printWorkingDirectory());
		System.out.println("当前工作目录下文件列表：");
		ftpFiles = ftpClient.listFiles("/开发项目/webservice改造/cxf框架重构/");
		// for (FTPFile ftpFile : ftpFiles) {
		// String fileName = ftpFile.getName();
		// // fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
		// // fileName = new String(fileName.getBytes("utf-8"), "GBK");
		// System.out.println("：" + fileName);
		// // System.out.println("ftpFile.isFile()：" + ftpFile.isFile());
		// }
		FTPListParseEngine engine = ftpClient.initiateListParsing(null,
				"/开发项目/webservice改造/cxf框架重构/");

		String systemType = System.getProperty(FTPClient.FTP_SYSTEM_TYPE);
		FTPFileEntryParser entryParser = new DefaultFTPFileEntryParserFactory()
				.createFileEntryParser(systemType);
		FTPFile ftpfile = entryParser
				.parseFTPEntry("/开发项目/webservice改造/cxf框架重构/");
		// FTPFile ftpfile =
		// engine.parseFTPEntry("/开发项目/webservice改造/cxf框架重构/");
		FTPFile[] ftpfiles = engine.getFiles();
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8");
		ftpClient.connect("127.0.0.1", 21);
		int replyCode = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(replyCode)) {
			throw new Exception("FTP服务器拒绝连接！");
		}
		if (!ftpClient.login("ftpuser", "123456")) {
			throw new Exception("登陆FTP服务器失败！");
		}

		FTPListParseEngine engine = ftpClient.initiateListParsing(null,
				"/开发项目/webservice改造/cxf框架重构/");

		String systemType = System.getProperty(FTPClient.FTP_SYSTEM_TYPE);
		if (systemType == null) {
			systemType = ftpClient.getSystemType(); // cannot be null
		}
		FTPFileEntryParserFactory __parserFactory = new DefaultFTPFileEntryParserFactory();
		FTPFileEntryParser __entryParser = __parserFactory
				.createFileEntryParser(systemType);
		__entryParser = __parserFactory.createFileEntryParser(systemType);
		String __entryParserKey = systemType;

		// FTPFileEntryParser entryParser = new
		// DefaultFTPFileEntryParserFactory()
		// .createFileEntryParser(systemType);
		// FTPFile ftpfile = entryParser
		// .parseFTPEntry("/开发项目/webservice改造/cxf框架重构/");
		// FTPFile ftpfile =
		// engine.parseFTPEntry("/开发项目/webservice改造/cxf框架重构/");
		FTPFile[] ftpfiles = engine.getFiles();
		System.out.println();
	}
}