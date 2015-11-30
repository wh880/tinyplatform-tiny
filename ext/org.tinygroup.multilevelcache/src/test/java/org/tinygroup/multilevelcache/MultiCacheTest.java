/**
 * 
 */
package org.tinygroup.multilevelcache;

import junit.framework.TestCase;

/**
 * @author Administrator
 *
 */
public class MultiCacheTest extends TestCase {

	MultiCache cache;
	
	MapCache cahce_f = new MapCache();
	MapCache cahce_s = new MapCache();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		cahce_f.init("yan");
		cahce_s.init("yan");
		cache = new MultiCache(cahce_f ,cahce_s);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPut(){
		cahce_f.putSafe("b", "aaa");
		assertEquals("aaa", cache.get("b"));
		cache.clear();
		
		cahce_s.putSafe("b", "aaa");
		assertEquals("aaa", cache.get("b"));
		cache.clear();
		
	}
	
	public void testGet(){
		cahce_f.put("b", "aaa");
		assertEquals("aaa", cache.get("b"));
		cahce_f.clear();
		
		cahce_s.put("b", "aaa");
		assertEquals("aaa", cache.get("b"));
		cahce_s.clear();
	}
	
	public void testGetGroup(){
		cahce_f.put("a", "b", "aaa");
		assertEquals("aaa", cache.get("a" ,"b"));
		cahce_f.clear();
		
		cahce_s.put("a", "b", "aaa");
		assertEquals("aaa", cache.get("a" ,"b"));
		cahce_s.clear();
	}
	
	public void testGetkeys(){
		cahce_f.put("a1", "aaa1");
		cahce_f.put("a2", "aaa2");
		cahce_s.put("a3", "aaa3");
		assertSame(3, cache.get(new String[]{"a1" ,"a2" ,"a3"}).length);
		cache.clear();
	}
	
	public void testGetGroupKeys(){
		cahce_f.put("a" ,"a1", "aaa1");
		cahce_s.put("a" ,"a2", "aaa2");
		cahce_s.put("a" ,"a3", "aaa3");
		assertSame(3, cache.getGroupKeys("a").size());
		cache.clear();
	}
	
	public void testCleanGroup(){
		cahce_f.put("a" ,"a1", "aaa1");
		cahce_f.put("a" ,"a2", "aaa2");
		cahce_s.put("a" ,"a3", "aaa3");
		cache.cleanGroup("a");
		assertSame(0, cache.getGroupKeys("a").size());
		cache.clear();
	}
	
	public void testRemove(){
		cahce_f.put("a1", "aaa1");
		cache.remove("a1");
		assertNull(cache.get("a1"));
	}
	
}
