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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileUtils;
import org.tinygroup.vfs.VFS;

import junit.framework.TestCase;

public class FtpFileObjectTest extends TestCase {

	public void testFtpFileObject() throws Exception {
		// 创建ftp服务器根路径对应的绝对路径
		String ftpRootPath = System.getProperty("user.dir");
		if (ftpRootPath.endsWith("/") || ftpRootPath.endsWith("\\")) {
			ftpRootPath = ftpRootPath + "ftpServer/";
		} else {
			ftpRootPath = ftpRootPath + "/ftpServer/";
		}
		ftpRootPath = ftpRootPath.replace("\\", "/");
		mkdirs(ftpRootPath);

		// 获取ftp服务器对象，并启动服务器
		FtpServer server = null;
		try {
			server = getFTPServer(ftpRootPath);
			server.start();
			System.out.println("ftp服务服务器启动成功,服务器根路径：" + ftpRootPath);
		} catch (FtpException e) {
			e.printStackTrace();
			return;
		}

		// 从服务器上读取名称含中文，英文，数字，空格的文件夹
		String folderName = "aaa 111 文件夹";
		mkdirs(ftpRootPath + folderName);
		FileObject fileObject = VFS.resolveFile("ftp://:@127.0.0.1:21/"
				+ folderName);
		FileUtils.printFileObject(fileObject);
		assertEquals("ftp://127.0.0.1:21/" + folderName,
				fileObject.getAbsolutePath());
		assertEquals(folderName, fileObject.getFileName());
		assertEquals("", fileObject.getExtName());
		assertEquals(0, fileObject.getSize());

		// 从服务器上读取子文件夹中的文件，子文件夹和文件名称都含中文，英文，数字，空格的文件夹
		String fileName = "bbb 222 文件.txt";
		createNewFile(ftpRootPath + folderName + "/" + fileName);
		fileObject = VFS.resolveFile("ftp://:@127.0.0.1:21/" + folderName + "/"
				+ fileName);
		FileUtils.printFileObject(fileObject);
		assertEquals("ftp://127.0.0.1:21/" + folderName + "/" + fileName,
				fileObject.getAbsolutePath());
		assertEquals(fileName, fileObject.getFileName());
		assertEquals("txt", fileObject.getExtName());

		// 测试写入
		write(fileObject.getOutputStream(), "是的发生的dddd1211");

		// 关闭ftp服务器，并删除对应的文件和文件夹
		server.stop();
		deletefile(ftpRootPath);
	}

	/*********************************************************************************************/

	private void mkdirs(String absolutePath) {
		File file = new File(absolutePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	private void createNewFile(String absolutePath) {
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

	private void write(OutputStream os, String content) {
		if (os != null) {
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(os);
				pw.write("写入测试开始，sss 222 写入测试结束".toString().toCharArray());
				pw.flush();
				byte[] b = content.getBytes();
				for (byte temp : b) {
					os.write(temp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (pw != null) {
					pw.close();
				}
			}
		}
	}

	private FtpServer getFTPServer(String ftpRootPath) throws FtpException {
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
	public boolean deletefile(String delpath) throws Exception {
		try {
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
						System.out
								.println(delfile.getAbsolutePath() + "删除文件成功");
					} else if (delfile.isDirectory()) {
						deletefile(delpath + "\\" + filelist[i]);
					}
				}
				System.out.println(file.getAbsolutePath() + "删除成功");
				file.delete();
			}

		} catch (FileNotFoundException e) {
			System.out.println("deletefile() Exception:" + e.getMessage());
		}
		return true;
	}

}
