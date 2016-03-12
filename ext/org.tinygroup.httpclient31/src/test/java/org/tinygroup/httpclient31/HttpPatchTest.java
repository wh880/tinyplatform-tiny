package org.tinygroup.httpclient31;

import org.tinygroup.httpvisitor.builder.HttpFactory;

public class HttpPatchTest extends ServerTestCase {
	
	public void testPatch(){
	    try{
	    	HttpFactory.patch("http://127.0.0.1:8080").execute();
	    }catch(Exception e){
	    	assertEquals("本HttpVisitor实现不支持PATCH操作!", e.getMessage());
	    }
		
	}
}
