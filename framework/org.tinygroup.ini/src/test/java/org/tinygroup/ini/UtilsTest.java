package org.tinygroup.ini;

import junit.framework.TestCase;

/**
 * Created by luoguo on 14-3-30.
 */
public class UtilsTest extends TestCase {
    public void testEncode() throws Exception {
        assertEquals("aa\\nabc", Utils.encode("aa\nabc"));
    }

    public void testDecode() throws Exception {
        assertEquals("aa\nabc", Utils.decode("aa\\nabc"));
    }
}
