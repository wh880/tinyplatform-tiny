package org.tinygroup.httpclient31;

import java.io.IOException;

import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.builder.HttpFactory;

public class HttpHeadTest extends ServerTestCase {

	public void testHead() throws IOException {
		Response response = HttpFactory.head("http://127.0.0.1:8080").execute();
		assertEquals(200, response.getStatusLine().getStatusCode());
		assertEquals(null, response.text());
		response.close();
	}
}
