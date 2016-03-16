package org.tinygroup.servicehttpchannel.test;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.httpvisit.HttpVisitor;
import org.tinygroup.httpvisit.impl.HttpVisitorImpl;

public class TestHttp {
	public static void main(String[] args) {
		HttpVisitor visitor = new HttpVisitorImpl();
		visitor.init();
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("name", "a");

		System.out.println(visitor.postUrl(
				"http://localhost:8080/mockserviceserver/echo.servicejson?name=a", para));
	}
}
