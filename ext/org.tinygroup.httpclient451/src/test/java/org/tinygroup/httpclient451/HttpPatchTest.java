package org.tinygroup.httpclient451;

import org.tinygroup.httpvisitor.builder.HttpFactory;

public class HttpPatchTest extends ServerTestCase {
	
	public void testPatch(){
	    try{
	    	HttpFactory.patch("http://127.0.0.1:"+MockUtil.HTTP_PORT).execute();
	    }catch(Exception e){
	    	assertEquals("本HttpVisitor实现不支持PATCH操作!", e.getMessage());
	    }
		
	}
}
