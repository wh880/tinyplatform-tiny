package org.tinygroup.httpclient451;

import java.io.IOException;

import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.builder.HttpFactory;

public class HttpTraceTest extends ServerTestCase {
	
	public void testTrace() throws IOException{
		Response response = HttpFactory.trace("http://127.0.0.1:8080").execute();
		assertEquals(200, response.getStatusLine().getStatusCode());
		assertEquals("hello world", response.text());
		response.close();
	}
}
