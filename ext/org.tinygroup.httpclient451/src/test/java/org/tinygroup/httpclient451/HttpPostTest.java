package org.tinygroup.httpclient451;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.builder.HttpFactory;

public class HttpPostTest extends ServerTestCase {
	
    public void testPost() throws IOException{
    	Response response = HttpFactory.post("http://127.0.0.1:8080").execute();
		assertEquals(200, response.getStatusLine().getStatusCode());
		assertEquals("hello world", response.text());
		response.close();
		
		//POST的参数
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("p2", "56");
		maps.put("p3", 789);
		response = HttpFactory.post("http://127.0.0.1:8080/param.do?p0=123")
				.param("p1", 4).params(maps).execute();
		assertEquals("123456789", response.text());
		response.close();
		
		//上传文件
		File file = new File("src/test/resources//abc.txt");
		response = HttpFactory.post("http://127.0.0.1:8080/file.do").data(file).execute();
		assertEquals("白日依山尽，黄河入海流。欲穷千里目，更上一层楼。", response.text());
		response.close();
		
		//测试文本报文
		String s = "阿百川发打发打发的,abc";
		response = HttpFactory.post("http://127.0.0.1:8080/text.do").charset("utf-8").data(s).execute();
		assertEquals(s, response.text());
		response.close();
	}
}
