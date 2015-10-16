/**
 * 
 */
package org.tinygroup.container;

import junit.framework.TestCase;

/**
 * @author Administrator
 *
 */
public class TitleComparatorTest extends TestCase{

	public void testCompare(){
		TitleComparator titleComparator = new TitleComparator<String, BaseObject<String>>();
		TestObject a = new TestObject(null, -1, null, "a", null);
		TestObject b = new TestObject(null, -1, null, "b", null);
		assertTrue(titleComparator.compare(a, b) < 0);
	}
	
}
