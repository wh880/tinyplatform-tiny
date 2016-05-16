package org.tiny.seg.test;

import org.tinygroup.chinese.ParserException;
import org.tinygroup.chinese.WordParserManager;
import org.tinygroup.chinese.fileProcessor.ChineseContainer;

public class TestChinesePinYin extends BaseChineseTestCase {
	public void testGetWordSpell() {
		WordParserManager manager = ChineseContainer.getWordParserManager("");
		try {
			String[] s = manager.getWordSpell("重庆");
			assertEquals("qing4", s[1]);
			assertEquals("chong2", s[0]);
		} catch (ParserException e) {
			assertTrue(false);
		}
	}

	public void testGetWordSpellShort() {
		WordParserManager manager = ChineseContainer.getWordParserManager("");
		try {
			String s = manager.getWordSpellShort("重庆");
			assertEquals("cq", s);
		} catch (ParserException e) {
			assertTrue(false);
		}
	}

	public void testGetCharacterSpell() {
		WordParserManager manager = ChineseContainer.getWordParserManager("");
		Character ch = "庆".charAt(0);
		String s = manager.getCharacterSpell(ch);
		assertEquals("qing4", s);
	}
	
	public void testGetCharacterSpellWithIndex() {
		WordParserManager manager = ChineseContainer.getWordParserManager("");
		Character ch = "重".charAt(0);
		String s = manager.getCharacterSpell(ch, 1);
		assertEquals("chong2", s);
	}
	
	
}
