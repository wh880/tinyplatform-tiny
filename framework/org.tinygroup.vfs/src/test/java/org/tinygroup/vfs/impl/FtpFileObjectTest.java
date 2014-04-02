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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import junit.framework.TestCase;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

public class FtpFileObjectTest extends TestCase {

	private static final String ROOT_DIR; // ftp服务器根路径
	private static FtpServer ftpServer; // ftp服务器

	static {
		File file = new File("ftpServer");
		if (!file.exists()) {
			file.mkdirs();
		}
		String temp = file.getAbsolutePath();
		if (!temp.endsWith("/") && !temp.endsWith("\\")) {
			temp = temp + "/";
		}
		temp = temp.replaceAll("\\\\", "/");
		ROOT_DIR = temp;
	}

	protected void setUp() throws Exception {
		super.setUp();
		try {
			ftpServer = getFTPServer(ROOT_DIR);
			ftpServer.start();
			System.out.println("ftp服务器启动成功,服务器根路径：" + ROOT_DIR);
		} catch (FtpException e) {
			deletefile(ROOT_DIR); // 清理文件，文件夹
			throw new RuntimeException("ftp服务器启动失败", e);
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		ftpServer.stop();
		deletefile(ROOT_DIR);
	}

	public void test() throws Exception {
		fileTest();
		folderTest();
		folderTest2();
		folderTest3();
	}

	private void fileTest() throws Exception {
		String fileName = "文件  11 aa.txt";
		createNewFile(ROOT_DIR + fileName); // 新建文件
		String resource = "ftp://anonymous:@127.0.0.1:21/" + fileName;
		FileObject fileObject = VFS.resolveFile(resource);

		assertNotNull(fileObject.getSchemaProvider());
		assertEquals(resource, fileObject.getURL().toString());
		assertEquals("/文件  11 aa.txt", fileObject.getAbsolutePath());
		assertEquals("/文件  11 aa.txt", fileObject.getPath());
		assertEquals("文件  11 aa.txt", fileObject.getFileName());
		assertEquals("txt", fileObject.getExtName());
		assertTrue(fileObject.isExist());
		assertTrue(!fileObject.isFolder());
		assertTrue(!fileObject.isInPackage());
		assertTrue(fileObject.getLastModifiedTime() > 0);
		assertTrue(fileObject.getSize() > 0);
		InputStream is = fileObject.getInputStream();
		OutputStream os = fileObject.getOutputStream();
		assertTrue(is != null || os != null);
		if (is != null) {
			is.close();
		}
		if (os != null) {
			os.close();
		}
		assertNull(fileObject.getParent());
		assertNull(fileObject.getChildren());

		deletefile(ROOT_DIR + fileName);
	}

	public void folderTest() throws Exception {
		String dirName = "目录 11 aaa";
		mkdirs(ROOT_DIR + dirName); // 新建文件夹
		String resource = "ftp://anonymous:@127.0.0.1:21/" + dirName;
		FileObject fileObject = VFS.resolveFile(resource);

		assertNotNull(fileObject.getSchemaProvider());
		assertEquals(resource, fileObject.getURL().toString());
		assertEquals("/目录 11 aaa", fileObject.getAbsolutePath());
		assertEquals("", fileObject.getPath());
		assertEquals("目录 11 aaa", fileObject.getFileName());
		assertEquals("", fileObject.getExtName());
		assertTrue(fileObject.isExist());
		assertTrue(fileObject.isFolder());
		assertTrue(!fileObject.isInPackage());
		assertTrue(fileObject.getLastModifiedTime() > 0);
		assertTrue(fileObject.getSize() == 0);
		assertNull(fileObject.getInputStream());
		assertNull(fileObject.getOutputStream());
		assertNull(fileObject.getParent());
		assertNotNull(fileObject.getChildren());
		assertEquals(0, fileObject.getChildren().size());
		assertNull(fileObject.getChild(""));

		deletefile(ROOT_DIR + dirName);
	}

	public void folderTest2() throws Exception {
		String dirName = "目录 22 aaa/目录 12 bbb/目录 123 ccc";
		mkdirs(ROOT_DIR + dirName); // 新建文件夹
		String resource = "ftp://anonymous:@127.0.0.1:21/" + dirName;
		FileObject fileObject = VFS.resolveFile(resource);

		assertNotNull(fileObject.getSchemaProvider());
		assertEquals(resource, fileObject.getURL().toString());
		assertEquals("/目录 22 aaa/目录 12 bbb/目录 123 ccc",
				fileObject.getAbsolutePath());
		assertEquals("", fileObject.getPath());
		assertEquals("目录 123 ccc", fileObject.getFileName());
		assertEquals("", fileObject.getExtName());
		assertTrue(fileObject.isExist());
		assertTrue(fileObject.isFolder());
		assertTrue(!fileObject.isInPackage());
		assertTrue(fileObject.getLastModifiedTime() > 0);
		assertTrue(fileObject.getSize() == 0);
		assertNull(fileObject.getInputStream());
		assertNull(fileObject.getOutputStream());
		assertNull(fileObject.getParent());
		assertNotNull(fileObject.getChildren());
		assertEquals(0, fileObject.getChildren().size());
		assertNull(fileObject.getChild(""));

		deletefile(ROOT_DIR + dirName);
	}

	public void folderTest3() throws Exception {
		String dirName = "目录 33 aaa/目录 erw 123/目录 wer1 2sd";
		mkdirs(ROOT_DIR + dirName); // 新建文件夹
		mkdirs(ROOT_DIR + dirName + "/目录 123 abc"); // 新建子文件夹
		createNewFile(ROOT_DIR + dirName + "/子文件 sd 12sdf.txt"); // 新建子文件
		String resource = "ftp://anonymous:@127.0.0.1:21/" + dirName;
		FileObject fileObject = VFS.resolveFile(resource);

		assertNotNull(fileObject.getSchemaProvider());
		assertEquals(resource, fileObject.getURL().toString());
		assertEquals("/目录 33 aaa/目录 erw 123/目录 wer1 2sd",
				fileObject.getAbsolutePath());
		assertEquals("", fileObject.getPath());
		assertEquals("目录 wer1 2sd", fileObject.getFileName());
		assertEquals("", fileObject.getExtName());
		assertTrue(fileObject.isExist());
		assertTrue(fileObject.isFolder());
		assertTrue(!fileObject.isInPackage());
		assertTrue(fileObject.getLastModifiedTime() > 0);
		assertTrue(fileObject.getSize() == 0);
		assertNull(fileObject.getInputStream());
		assertNull(fileObject.getOutputStream());
		assertNull(fileObject.getParent());
		assertNotNull(fileObject.getChildren());
		assertEquals(2, fileObject.getChildren().size());
		assertNotNull(fileObject.getChild("目录 123 abc"));
		assertNotNull(fileObject.getChild("子文件 sd 12sdf.txt"));

		List<FileObject> childen = fileObject.getChildren();
		for (FileObject fileObject2 : childen) {
			if (fileObject2.isFolder()) {
				assertNotNull(fileObject2.getSchemaProvider());
				assertNull(fileObject2.getURL());
				assertEquals("/目录 33 aaa/目录 erw 123/目录 wer1 2sd/目录 123 abc",
						fileObject2.getAbsolutePath());
				assertEquals("/目录 123 abc", fileObject2.getPath());
				assertEquals("目录 123 abc", fileObject2.getFileName());
				assertEquals("", fileObject2.getExtName());
				assertTrue(fileObject2.isExist());
				assertTrue(fileObject2.isFolder());
				assertTrue(!fileObject2.isInPackage());
				assertTrue(fileObject2.getLastModifiedTime() > 0);
				assertTrue(fileObject2.getSize() == 0);
				assertNull(fileObject2.getInputStream());
				assertNull(fileObject2.getOutputStream());
				assertNotNull(fileObject2.getParent());
				assertNotNull(fileObject2.getChildren());
				assertEquals(0, fileObject2.getChildren().size());
				assertNull(fileObject2.getChild(""));
			} else {
				assertNull(fileObject2.getURL());
				assertNotNull(fileObject2.getSchemaProvider());
				assertEquals(
						"/目录 33 aaa/目录 erw 123/目录 wer1 2sd/子文件 sd 12sdf.txt",
						fileObject2.getAbsolutePath());
				assertEquals("/子文件 sd 12sdf.txt", fileObject2.getPath());
				assertEquals("子文件 sd 12sdf.txt", fileObject2.getFileName());
				assertEquals("txt", fileObject2.getExtName());
				assertTrue(fileObject2.isExist());
				assertTrue(!fileObject2.isFolder());
				assertTrue(!fileObject2.isInPackage());
				assertTrue(fileObject2.getLastModifiedTime() > 0);
				assertTrue(fileObject2.getSize() > 0);
				InputStream is = fileObject2.getInputStream();
				OutputStream os = fileObject2.getOutputStream();
				assertTrue(is != null || os != null);
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
				assertNotNull(fileObject2.getParent());
				assertNull(fileObject2.getChildren());
			}
		}

		deletefile(ROOT_DIR + dirName);
	}

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
			content.append("ftpserver.user.anonymous.userpassword=\n");
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
	private void deletefile(String delpath) {
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
