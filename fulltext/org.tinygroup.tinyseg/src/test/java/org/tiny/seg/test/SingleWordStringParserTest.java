package org.tiny.seg.test;

import java.io.IOException;
import java.util.Collection;

import junit.framework.TestCase;

import org.tinygroup.chinese.WordParserManager;
import org.tinygroup.chinese.WordParserMode;
import org.tinygroup.chinese.WordParserType;
import org.tinygroup.chinese.parsermanager.WordParserManagerImpl;
import org.tinygroup.chinese.single.SingleToken;
import org.tinygroup.chinese.single.SingleWordStringParser;

public class SingleWordStringParserTest extends TestCase {
	SingleWordStringParser swsp = new SingleWordStringParser();
	WordParserManager ww = new WordParserManagerImpl();

	public void setUp() {

		try {
			super.setUp();
		} catch (Exception e) {
		}
		ww.addWordString("中华");
		ww.addWordString("人民");
		ww.addWordString("国");
		ww.addWordString("共和");
		ww.addWordString("共和国");
		ww.addWordString("中华人民");
		ww.addWordString("中华人民共和国");
		ww.addWordString("苹果6");
		ww.addWordString("成立");
	}

	public void testParse() throws IOException {
		Collection<SingleToken> collentions = null;
		swsp.parse(ww, "中华人民共和国成立拉", WordParserType.ASC, WordParserMode.ALL);
		collentions = swsp.tokens();
		System.out.println( collentions );
		assertEquals(5, collentions.size());
		
		
		swsp.parse(ww, "中华人民共和国成立拉", WordParserType.ASC, WordParserMode.MAX);
		collentions = swsp.tokens();
		System.out.println( collentions );
		assertEquals(3, collentions.size());
		
		swsp.parse(ww, "中华人民共和国成立拉", WordParserType.ASC, WordParserMode.MIN);
		collentions = swsp.tokens();
		System.out.println( collentions );
		assertEquals(6, collentions.size());
		
		swsp.parse(ww, "中华人民共和国苹果6销售区", WordParserType.ASC, WordParserMode.ALL);
		System.out.println( swsp.tokens());
		
	}
}
