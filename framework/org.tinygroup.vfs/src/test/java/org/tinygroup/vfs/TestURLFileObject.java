/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
 */
package org.tinygroup.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import junit.framework.TestCase;

import org.tinygroup.vfs.impl.HttpFileObject;
import org.tinygroup.vfs.impl.HttpsFileObject;

public class TestURLFileObject extends TestCase {

	public void testHttpFileObject() throws IOException {

		FileObject fileObject = VFS.resolveFile("http://www.baidu.com/");
		assertTrue(fileObject instanceof HttpFileObject);
		FileUtils.printFileObject(fileObject);

	}

	public void testHttpsFileObject() throws IOException {

		FileObject fileObject = VFS.resolveFile("https://www.alipay.com/");
		assertTrue(fileObject instanceof HttpsFileObject);
		FileUtils.printFileObject(fileObject);

	}

	public void urlConnetionGet() throws IOException {
	
		 String url = "http://localhost:8080/webstandard/index.page";
		 String charset = "UTF-8";
		 // ...
		 String query = String.format("name=%s",
		 URLEncoder.encode("hshhs", charset));
		
		 URLConnection connection = new URL(url + "?" +
		 query).openConnection();
		 connection.setRequestProperty("Accept-Charset", charset);
		 InputStream response = connection.getInputStream();

	}

	public void urlConnetionPost() throws IOException {

		String url = "http://192.168.84.56:8080/webstandard/index.page";
		String charset = "UTF-8";
		// ...
		String query = String.format("name,%s",
				URLEncoder.encode("hshhs", charset));

		HttpURLConnection httpConnection = (HttpURLConnection)new URL(url).openConnection();
		httpConnection.setRequestMethod("POST");
		httpConnection.setDoOutput(true); // Triggers POST.
		httpConnection.setDoInput(true);
		httpConnection.setRequestProperty("Accept-Charset", charset);
		httpConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=" + charset);
		httpConnection.setRequestProperty("User-Agent",
         "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
		OutputStream output = null;
		try {
			output = httpConnection.getOutputStream();
//			byte[]bb=new byte[3];
//			bb[1]=-1;
//			bb[0]=0;
//			bb[2]='\n';
//			output.write(bb);
			output.write(query.getBytes(charset));
		} finally {
			if (output != null)
				try {
					output.close();
				} catch (IOException logOrIgnore) {
				}
		}
		InputStream response = httpConnection.getInputStream();
	}

	// public void testFtpFileObject(){
	//
	// FileObject
	// fileObject=VFS.resolveFile("http://home.hundsun.com/WebSite.aspx");
	// assertTrue(fileObject instanceof FtpFileObject);
	// FileUtils.printFileObject(fileObject);
	//
	// }

}
