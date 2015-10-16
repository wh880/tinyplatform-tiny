/**
 * 
 */
package org.tinygroup.container;

import junit.framework.TestCase;

/**
 * @author Administrator
 *
 */
public class NameComparatorTest extends TestCase{

	public void testCompare(){
		NameComparator nameComparator = new NameComparator<String, BaseObject<String>>();
		TestObject a = new TestObject(null, -1, "a", null, null);
		TestObject b = new TestObject(null, -1, "b", null, null);
		assertTrue(nameComparator.compare(a, b) < 0);
	}
	
}
