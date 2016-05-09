package org.tinygroup.httpclient31;

import java.io.IOException;

import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.builder.HttpFactory;

/**
 * 客户端配置的一些测试
 * @author yancheng11334
 *
 */
public class ClientTest extends ServerTestCase {
	
	public void testHttps() throws IOException {
		Response response = HttpFactory
				.get("https://127.0.0.1:"+MockUtil.HTTPS_PORT+"/ssl").execute();
		assertEquals(200, response.getStatusLine().getStatusCode());
		assertEquals("hello world", response.text());
		response.close();
	}
	
//	public void testProxy() {
//		Response response = HttpFactory
//				.get("http://www.youtobe.com").proxy("1.193.162.91", 8000).execute();
//		System.out.println(response.text());
//	}
}
