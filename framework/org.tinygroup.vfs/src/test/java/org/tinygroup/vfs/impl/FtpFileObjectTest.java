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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileUtils;
import org.tinygroup.vfs.VFS;

public class FtpFileObjectTest {

	private static String rootDir;

	static {
		File file = new File("ftpServer");
		if (!file.exists()) {
			file.mkdirs();
		}
		rootDir = file.getAbsolutePath(); // ftp服务器根路径
		if (!rootDir.endsWith("/") && !rootDir.endsWith("\\")) {
			rootDir = rootDir + "/";
		}
		rootDir = rootDir.replaceAll("\\\\", "/");
	}

	public static void main(String[] args) throws Exception {
		// 获取ftp服务器对象，并启动服务器
		FtpServer server = null;
		try {
			server = getFTPServer(rootDir);
			server.start();
			System.out.println("ftp服务器启动成功,服务器根路径：" + rootDir);
		} catch (FtpException e) {
			deletefile(rootDir); // 清理文件，文件夹
			throw new RuntimeException("ftp服务器启动失败", e);
		}

		mkdirs(rootDir + "aaa 111 文件夹"); // 新建文件夹
		createNewFile(rootDir + "aaa 111 文件夹/bbb 222 文件.txt"); // 新建文件

		// 文件夹操作
		String resource = "ftp://anonymous:anonymous@127.0.0.1:21/aaa 111 文件夹";
		FileObject fileObject = VFS.resolveFile(resource);
		FileUtils.printFileObject(fileObject);

		// 文件操作
		resource = "ftp://anonymous:anonymous@127.0.0.1:21/aaa 111 文件夹/bbb 222 文件.txt";
		fileObject = VFS.resolveFile(resource);
		FileUtils.printFileObject(fileObject);

		server.stop(); // 关闭ftp服务器
		deletefile(rootDir); // 清理文件，文件夹
	}

	/*********************************************************************************************/

	private static void mkdirs(String absolutePath) {
		File file = new File(absolutePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	private static void createNewFile(String absolutePath) {
		File file = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			file = new File(absolutePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			StringBuilder content = new StringBuilder();
			content.append("测  试sd _999 s收到.txt");
			pw.write(content.toString().toCharArray());
			pw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
	}

	private static FtpServer getFTPServer(String ftpRootPath)
			throws FtpException {
		File file = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;

		// 新建users.properties文件
		String usersProperties = ftpRootPath + "users.properties";
		try {
			file = new File(usersProperties);
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			// 配置users.properties文件
			StringBuilder content = new StringBuilder();
			content.append("ftpserver.user.anonymous.homedirectory=")
					.append(ftpRootPath).append("\n");
			// content.append("ftpserver.user.anonymous.userpassword=\n");
			// content.append("ftpserver.user.anonymous.enableflag=true\n");
			content.append("ftpserver.user.anonymous.writepermission=true\n");
			// content.append("ftpserver.user.anonymous.maxloginnumber=20\n");
			// content.append("ftpserver.user.anonymous.maxloginperip=2\n");
			// content.append("ftpserver.user.anonymous.idletime=300\n");
			// content.append("ftpserver.user.anonymous.uploadrate=4800\n");
			// content.append("ftpserver.user.anonymous.downloadrate=4800\n");
			pw.write(content.toString().toCharArray());
			pw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}

		FtpServerFactory serverFactory = new FtpServerFactory();
		ListenerFactory factory = new ListenerFactory();

		// set the port of the listener
		factory.setPort(21);

		// replace the default listener
		serverFactory.addListener("default", factory.createListener());

		// 设置用户配置信息
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		userManagerFactory.setFile(file);
		serverFactory.setUserManager(userManagerFactory.createUserManager());

		// 注册FTP事件监听
		// Map<String, Ftplet> ftplets = new LinkedHashMap<String, Ftplet>();
		// ftplets.put(FtpletNotification.class.getName(),
		// new FtpletNotification());
		// serverFactory.setFtplets(ftplets);

		// start the server
		org.apache.ftpserver.FtpServer server = serverFactory.createServer();

		return server;
	}

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	private static void deletefile(String delpath) {
		File file = new File(delpath);
		// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
		if (!file.isDirectory()) {
			file.delete();
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File delfile = new File(delpath + "\\" + filelist[i]);
				if (!delfile.isDirectory()) {
					delfile.delete();
					System.out.println(delfile.getAbsolutePath() + "删除文件成功");
				} else if (delfile.isDirectory()) {
					deletefile(delpath + "\\" + filelist[i]);
				}
			}
			System.out.println(file.getAbsolutePath() + "删除成功");
			file.delete();
		}
	}

}
