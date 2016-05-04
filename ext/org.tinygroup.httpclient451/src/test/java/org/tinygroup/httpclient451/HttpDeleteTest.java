package org.tinygroup.httpclient451;

import java.io.IOException;

import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.builder.HttpFactory;

public class HttpDeleteTest extends ServerTestCase {
	
	public void testDelete() throws IOException{
		Response response = HttpFactory.delete("http://127.0.0.1:"+MockUtil.HTTP_PORT).execute();
		assertEquals(200, response.getStatusLine().getStatusCode());
		assertEquals("hello world", response.text());
		response.close();
	}
}
