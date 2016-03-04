package org.tinygroup.httpvisitor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.tinygroup.httpvisitor.builder.HttpFactory;
import org.tinygroup.vfs.VFS;

public class NewTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

        String url = "www.abc.com";
		
		//get及post等八种HTTP操作
		Response r1 = HttpFactory.get(url).execute();
		Response r2 = HttpFactory.put(url).execute();
		Response r3 = HttpFactory.post(url).execute();
		
		//响应内容接口
		int status = r1.getStatusLine().getStatusCode();
		Header header = r1.getHeader("length");
		Cookie cookie = r1.getCookie("abc");
		
		String text = r1.text();  //字符串
		byte[] bytes = r1.bytes(); //字符数组
		InputStream stream = r1.stream(); //流
		
		//按指定编码读取
		text = r1.charset("utf-8").text();
		
		//URL参数
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("k1", "v1");
		maps.put("k2", "v2");
		r1 = HttpFactory.get(url).params(maps).param("num", 10L).execute();
		
		//请求头操作
		Map<String, String> headMaps = new HashMap<String, String>();
		headMaps.put("h1", "v1");
		headMaps.put("h2", "v2");
		r2 = HttpFactory.post(url).header("h0", "100").headers(headMaps).execute();
		//cookie操作
		Map<String, Cookie> cookieMaps = new HashMap<String, Cookie>();
		r1 = HttpFactory.get(url).cookie("/", "c1", "1000").cookies(cookieMaps).execute();
		
		//多段上传
		r1 = HttpFactory.post(url).multipart("context", "goodbye").multipart("file1", VFS.resolveFile("/abc.jpg")).execute();
		
		//设置agent
		r1 = HttpFactory.get(url).userAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").execute();
		
		//设置超时时间
		r1 = HttpFactory.get(url).timeout(100).execute();
		
		//设置代理
		r1 = HttpFactory.get(url).proxy("192.168.32.88", 8000).execute();
		
		//关闭对象
		r1.close();
		r2.close();
		r3.close();
	}

}
