package org.tinygroup.httpclient31;

import java.io.IOException;

import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.builder.HttpFactory;

public class HttpDeleteTest extends ServerTestCase {
	
	public void testDelete() throws IOException{
		Response response = HttpFactory.delete("http://127.0.0.1:8080").execute();
		assertEquals(200, response.getStatusLine().getStatusCode());
		assertEquals("hello world", response.text());
		response.close();
	}
}
