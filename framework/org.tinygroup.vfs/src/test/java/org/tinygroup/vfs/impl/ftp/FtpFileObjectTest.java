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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileUtils;
import org.tinygroup.vfs.VFS;

import junit.framework.TestCase;

public class FtpFileObjectTest extends TestCase {

	private static String FTP_PATH;

	private static String FTP_ROOT_PATH;

	static {
		String temp = System.getenv("TEMP");
		FTP_PATH = temp;
		if (!FTP_PATH.endsWith("/") && !FTP_PATH.endsWith("\\")) {
			FTP_PATH = FTP_PATH + "/ftpserver/";
		}
		FTP_ROOT_PATH = FTP_PATH + "root/";
		String root = FTP_ROOT_PATH;
		FTP_ROOT_PATH = FTP_ROOT_PATH.replace("\\", "/");

		// 在ftp服务器根目录下添加文件和文件夹
		initData();

		// 启动ftp服务
		try {
			starFTPServer();
			System.out.println("ftp服务服务器启动成功,服务器根路径：" + root);
		} catch (FtpException e) {
			e.printStackTrace();
		}
	}

	private static void initData() {
		// 新建文件夹: "test"
		mkdirs("test");

		// 新建文件夹: "test/999 中文 空格 aaa 文件夹_ 测试"
		mkdirs("/test/999 中文 空格 aaa 文件夹_ 测试");

		// 新建文件:test/测 试sd _999 s收到.txt
		createNewFile("\\test/测  试sd _999 s收到.txt");

		// 新建文件:test/test.txt
		createNewFile("test/test.txt");

		// 新建文件:test/999 中文 空格 aaa 文件夹_ 测试/测 试sd _999 s收到.txt
		createNewFile("test/999 中文 空格 aaa 文件夹_ 测试/123 中文 空格测试 abc.txt");

		// 新建文件:999 中文 空格 aaa 文件夹_ 测试/测 试sd _999 s收到.txt
		createNewFile("test/999 中文 空格 aaa 文件夹_ 测试/1122啊啊爸爸.txt");
	}

	private static void mkdirs(String path) {
		if (path.startsWith("/") || path.startsWith("\\")) {
			path = path.substring(1, path.length());
		}

		File file = new File(FTP_ROOT_PATH + path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	private static void createNewFile(String path) {
		if (path.startsWith("/") || path.startsWith("\\")) {
			path = path.substring(1, path.length());
		}

		File file = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			file = new File(FTP_ROOT_PATH + path);
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

	private static void starFTPServer() throws FtpException {
		File file = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;

		// 新建users.properties文件
		String usersProperties = FTP_PATH + "users.properties";
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
					.append(FTP_ROOT_PATH).append("\n");
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
		server.start();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFtpFileObject() {
		String resource = "ftp://:@127.0.0.1:21/test/999 中文 空格 aaa 文件夹_ 测试/123 中文 空格测试 abc.txt";
		FileObject fileObject = VFS.resolveFile(resource);
		if (fileObject != null) {
			FileUtils.printFileObject(fileObject);
			write(fileObject.getOutputStream(), "是的发生的dddd1211");
			// print(fileObject.getInputStream(), "d:\\temp.txt");
		} else {
			System.out.println("文件【" + resource + "】不存在！");
		}
		System.out.println("testFtpFileObject完成");
	}

	public void testFtpFileObject2() {
		String resource = "ftp://:@127.0.0.1:21/test";
		FileObject fileObject = VFS.resolveFile(resource);
		if (fileObject != null) {
			FileUtils.printFileObject(fileObject);
		} else {
			System.out.println("文件【" + resource + "】不存在！");
		}
		System.out.println("testFtpFileObject2完成");
	}

	public static void main(String[] args) throws FtpException {

		// String resource = "ftp://:@127.0.0.1:21/logs/aaa/bbb/ccc/ddd";
		// new FtpFileObjectTest().starFTPServer();
		// FileObject fileObject = VFS.resolveFile(resource);

		// String resource = "ftp://ftpuser:123456@127.0.0.1:21/logs/";
		// FileObject fileObject = VFS.resolveFile(resource);

		// FileUtils.printFileObject(fileObject);
		// System.out.println("完成");

		java.util.Map m = System.getenv();
		for (java.util.Iterator it = m.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String value = (String) m.get(key);
			System.out.println(key + ":" + value);
		}
	}

	private void write(OutputStream os, String content) {
		if (os != null) {
			// PrintWriter pw = null;
			try {
				// pw = new PrintWriter(os);
				// pw.write("aaaaaaaaa".toString().toCharArray());
				// pw.flush();
				byte[] b = content.getBytes();
				// for (byte temp : b) {
				os.write(b);
				// }
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
				// if (pw != null) {
				// pw.close();
				// }
			}
		}
	}

	private void print(InputStream is, String abstractFileName) {
		FileImageOutputStream fos = null;
		try {
			fos = new FileImageOutputStream(new File(abstractFileName));
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				fos.write(bytes, 0, c);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
