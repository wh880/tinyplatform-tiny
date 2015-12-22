/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;



public class TestChildObject extends BaseTestCast2 {

//	public void testSimpleField(){
//		Context context = new ContextImpl();
//		context.put("child", "name");
//		context.put("pro", "1");
//		ChildObject parentObj = (ChildObject) generator.getObject("abc", null,
//				ChildObject.class.getName(), this.getClass().getClassLoader(),
//				context);
//		assertEquals("name",parentObj.getChild());
//		assertEquals( "1",parentObj.getPro());
//		assertEquals( Integer.valueOf(1),parentObj.getSimpleObject().getLength());
//		assertEquals( Boolean.TRUE,parentObj.getSimpleObject().getFlag());
//	}
//	
//	public void testCollectionField(){
//		Context context = new ContextImpl();
//		String[] nameLists = new String[]{"a","b","c"};
//		String[] nameArrays = new String[]{"q","w","e"};
//		EnumObject[] enumObjects = new EnumObject[]{EnumObject.MON ,EnumObject.SUN ,EnumObject.FRI};
//		context.put("simpleObjectList.name", nameLists);
//		context.put("simpleObjectArray.name", nameArrays);
//		context.put("enumObjectArray", enumObjects);
//		ChildObject object = (ChildObject) generator.getObject(null, null,
//				ChildObject.class.getName(), this.getClass().getClassLoader(),
//				context);
//	
//		assertNotNull(object);
//		assertTrue(object.getSimpleObjectList().size() == 4);
//		assertEquals("a", object.getSimpleObjectList().get(0).getName());
//		assertEquals("b", object.getSimpleObjectList().get(1).getName());
//		assertEquals("c", object.getSimpleObjectList().get(2).getName());
//		
//		assertEquals("q", object.getSimpleObjectArray()[0].getName());
//		assertEquals("w", object.getSimpleObjectArray()[1].getName());
//		assertEquals("e", object.getSimpleObjectArray()[2].getName());
//		
//		assertEquals(EnumObject.MON, object.getEnumObjectArray()[0]);
//		assertEquals(EnumObject.SUN, object.getEnumObjectArray()[1]);
//		assertEquals(EnumObject.FRI, object.getEnumObjectArray()[2]);
//	}
	
}
