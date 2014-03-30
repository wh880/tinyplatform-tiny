package org.tinygroup.ini.impl;

import junit.framework.TestCase;
import org.tinygroup.ini.IniOperator;
import org.tinygroup.ini.Sections;
import org.tinygroup.ini.ValuePair;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoguo on 14-3-29.
 */
public class IniOperatorDefaultTest extends TestCase {
    IniOperator operator;

    public void setUp() throws Exception {
        super.setUp();
        operator = new IniOperatorDefault();
        Sections sections = new Sections();
        operator.setSections(sections);
    }

    public void testGetSections() throws Exception {
        assertNotNull(operator.getSections());
    }

    public void testRead() throws Exception {
        String string = ";abc\naa=bb;ccc\r\n[ccc];ddd\naa=bb;ccdd\r\nccc=ddd;aa;bb;cc";
        operator.read(new StringBufferInputStream(string), "UTF-8");
        operator.write(System.out, "UTF-8");
    }

    public void testWrite() throws Exception {
        operator.put("aa", "abc", 123);
        operator.put("aa", "abc", 123);
        operator.put("aa", "abc1", 123);
        operator.put("aa", "abc2", 123);
        operator.write(System.out, "UTF-8");
    }

    public void testPut() throws Exception {
        operator.put("aa", "abc", 123);
        operator.put("aa", "abc", 123);
        assertEquals(operator.getSection("aa").getValuePairList().size(), 1);
        Integer value = operator.get(Integer.class, "aa", "abc");
        assertEquals(value.intValue(), 123);
    }

    public void testAdd() throws Exception {
        operator.add("aa", "abc", 123);
        operator.add("aa", "abc", 123);
        assertEquals(operator.getSections().getSection("aa").getValuePairList().size(), 2);
    }

    public void testGet() throws Exception {
        operator.put("aa", "abc", 123);
        assertEquals(operator.getSection("aa").getValuePairList().size(), 1);
        assertEquals(operator.get("aa", "abc"), "123");
    }

    public void testGetList() throws Exception {
        operator.add("aa", "abc", 123);
        operator.add("aa", "abc", 123);
        List<Integer> valueList = operator.getList(Integer.class, "aa", "abc");
        assertEquals(valueList.size(), 2);

    }

    public void testGet1() throws Exception {
        operator.add("aa", "abc", 123);
        assertEquals("456", operator.get("aa", "def", "456"));
        assertEquals("123", operator.get("aa", "abc", "456"));
    }

    public void testAdd1() throws Exception {
        List<ValuePair> valuePairs = new ArrayList<ValuePair>();
        valuePairs.add(new ValuePair("abc", "1"));
        valuePairs.add(new ValuePair("abc", "2"));
        operator.add("aa", valuePairs);
        assertEquals(operator.getSection("aa").getValuePairList().size(), 2);
    }


    public void testAdd2() throws Exception {
        operator.add("aa", "abc", "123");
        operator.add("aa", new ValuePair("abc", "123"));
        assertEquals(operator.getSection("aa").getValuePairList().size(), 2);
    }


    public void testGetValuePair() throws Exception {
        operator.add("aa", "abc", "123");
        operator.add("aa", new ValuePair("cde", "123"));
        assertEquals(operator.getValuePair("aa", "abc").getValue(), "123");
    }
}
