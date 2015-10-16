/**
 * 
 */
package org.tinygroup.container;

import junit.framework.TestCase;

/**
 * @author Administrator
 *
 */
public class OrderComparatorTest extends TestCase{

	public void testCompare(){
		OrderCompare orderComparator = new OrderCompare<String, BaseObject<String>>();
		TestObject a = new TestObject(null, 1, null, "", null);
		TestObject b = new TestObject(null, 2, null, "a", null);
		TestObject c = new TestObject(null, 2, null, "b", null);
		assertTrue(orderComparator.compare(a, b) < 0);
		assertTrue(orderComparator.compare(b, c) < 0);
	}
	
}
