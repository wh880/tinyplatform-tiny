package org.tiny.seg.test;

import org.tinygroup.chinese.WordParserManager;
import org.tinygroup.chinese.parsermanager.WordParserManagerImpl;

import junit.framework.TestCase;

public class WordParserManagerTest extends TestCase{
	WordParserManager ww = new WordParserManagerImpl();
	public void setUp(){
		try {
			super.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ww.addWordString("中华");
		ww.addWordString("人民");
		ww.addWordString("国");
		ww.addWordString("共和");
		ww.addWordString("共和国");
		ww.addWordString("中华人民");
		ww.addWordString("中华人民共和国");
		ww.addWordString("苹果6");
	}
	public void testManager(){
		
	}
}
