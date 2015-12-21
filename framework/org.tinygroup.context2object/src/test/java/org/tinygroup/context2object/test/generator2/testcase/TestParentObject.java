/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.ParentObject;

/**
 * @author Administrator
 *
 */
public class TestParentObject extends BaseTestCast2 {
	
	public void testParentObjectSimpleObjectNoVarName() {
		Context context = new ContextImpl();
		context.put("simpleObject.name", "name");
		context.put("simpleObject.length", "1");
		context.put("simpleObject.length2", "2");
		context.put("simpleObject.flag", "true");
		ParentObject parentObj = (ParentObject) generator.getObject(null, null,
				ParentObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertSimpleObject(parentObj);
	}

	public void testParentObjectSimpleObjectWithDefaultName() {
		Context context = new ContextImpl();
		context.put("parentObject.simpleObject.name", "name");
		context.put("parentObject.simpleObject.length", "1");
		context.put("parentObject.simpleObject.length2", "2");
		context.put("parentObject.simpleObject.flag", "true");
		ParentObject parentObj = (ParentObject) generator.getObject(null, null,
				ParentObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertSimpleObject(parentObj);
	}

	public void testParentObjectSimpleObjectWithVarName() {
		Context context = new ContextImpl();
		context.put("abc.simpleObject.name", "name");
		context.put("abc.simpleObject.length", "1");
		context.put("abc.simpleObject.length2", "2");
		context.put("abc.simpleObject.flag", "true");
		ParentObject parentObj = (ParentObject) generator.getObject("abc",
				null, ParentObject.class.getName(), this.getClass()
						.getClassLoader(), context);
		assertSimpleObject(parentObj);
	}

	private void assertSimpleObject(ParentObject parentObj) {
		assertEquals("name", parentObj.getSimpleObject().getName());
		assertEquals(2, parentObj.getSimpleObject().getLength2());
		assertEquals(Integer.valueOf(1), parentObj.getSimpleObject()
				.getLength());
		assertEquals(Boolean.TRUE, parentObj.getSimpleObject().getFlag());
	}

	public void testParentObjectSimpleObjectListNoVarName() {
		Context context = new ContextImpl();
		
		context.put("simpleObjectList.name", new String[] { "name1", "name2" });
		context.put("simpleObjectList.length2",new String[] { "1", "2" } );
		context.put("simpleObjectList.length", new String[] { "12", "22" });
		context.put("simpleObjectList.flag", new String[] { "true", "false" });
		
		context.put("simpleObjectArray.name", new String[] { "name1", "name2" });
		context.put("simpleObjectArray.length2",new String[] { "1", "2" } );
		context.put("simpleObjectArray.length", new String[] { "12", "22" });
		context.put("simpleObjectArray.flag", new String[] { "true", "false" });
		
		ParentObject parentObj = (ParentObject) generator.getObject(null, null,
				ParentObject.class.getName(), this.getClass().getClassLoader(),
				context);
		
		assertSimpleObjectList(parentObj);
	}
	
	public void testParentObjectSimpleObjectListWithVarName() {
		Context context = new ContextImpl();
		
		context.put("bean.simpleObjectList.name", new String[] { "name1", "name2" });
		context.put("bean.simpleObjectList.length2",new String[] { "1", "2" } );
		context.put("bean.simpleObjectList.length", new String[] { "12", "22" });
		context.put("bean.simpleObjectList.flag", new String[] { "true", "false" });
		
		context.put("bean.simpleObjectArray.name", new String[] { "name1", "name2" });
		context.put("bean.simpleObjectArray.length2",new String[] { "1", "2" } );
		context.put("bean.simpleObjectArray.length", new String[] { "12", "22" });
		context.put("bean.simpleObjectArray.flag", new String[] { "true", "false" });
		
		ParentObject parentObj = (ParentObject) generator.getObject("bean", null,
				ParentObject.class.getName(), this.getClass().getClassLoader(),
				context);
		
		assertSimpleObjectList(parentObj);
	}
	
	public void testParentObjectSimpleObjectListWithDefaultName() {
		Context context = new ContextImpl();
		
		context.put("parentObject.simpleObjectList.name", new String[] { "name1", "name2" });
		context.put("parentObject.simpleObjectList.length2",new String[] { "1", "2" } );
		context.put("parentObject.simpleObjectList.length", new String[] { "12", "22" });
		context.put("parentObject.simpleObjectList.flag", new String[] { "true", "false" });
		
		context.put("parentObject.simpleObjectArray.name", new String[] { "name1", "name2" });
		context.put("parentObject.simpleObjectArray.length2",new String[] { "1", "2" } );
		context.put("parentObject.simpleObjectArray.length", new String[] { "12", "22" });
		context.put("parentObject.simpleObjectArray.flag", new String[] { "true", "false" });
		
		ParentObject parentObj = (ParentObject) generator.getObject(null, null,
				ParentObject.class.getName(), this.getClass().getClassLoader(),
				context);
		
		assertSimpleObjectList(parentObj);
	}
	
	private void assertSimpleObjectList(ParentObject parentObj) {
		assertEquals(2, parentObj.getSimpleObjectList().size());

		assertEquals("name1", parentObj.getSimpleObjectList().get(0).getName());
		assertEquals(1, parentObj.getSimpleObjectList().get(0).getLength2());
		assertEquals(Integer.valueOf(12), parentObj.getSimpleObjectList().get(0)
				.getLength());
		assertEquals(Boolean.TRUE, parentObj.getSimpleObjectList().get(0)
				.getFlag());
		
		assertEquals("name2", parentObj.getSimpleObjectList().get(1).getName());
		assertEquals(2, parentObj.getSimpleObjectList().get(1).getLength2());
		assertEquals(Integer.valueOf(22), parentObj.getSimpleObjectList().get(1)
				.getLength());
		assertEquals(Boolean.FALSE, parentObj.getSimpleObjectList().get(1)
				.getFlag());
		
		
	}
	
	public void testParentObjectSimpleObjectArrayNoVarName() {
		Context context = new ContextImpl();
		
		context.put("simpleObjectArray.name", new String[] { "name1", "name2" });
		context.put("simpleObjectArray.length2",new String[] { "1", "2" } );
		context.put("simpleObjectArray.length", new String[] { "12", "22" });
		context.put("simpleObjectArray.flag", new String[] { "true", "false" });
		
		ParentObject parentObj = (ParentObject) generator.getObject(null, null,
				ParentObject.class.getName(), this.getClass().getClassLoader(),
				context);
		
		assertSimpleObjectArray(parentObj);
	}
	
	public void testParentObjectSimpleObjectArrayWithVarName() {
		Context context = new ContextImpl();
		
		context.put("bean.simpleObjectArray.name", new String[] { "name1", "name2" });
		context.put("bean.simpleObjectArray.length2",new String[] { "1", "2" } );
		context.put("bean.simpleObjectArray.length", new String[] { "12", "22" });
		context.put("bean.simpleObjectArray.flag", new String[] { "true", "false" });
		
		ParentObject parentObj = (ParentObject) generator.getObject("bean", null,
				ParentObject.class.getName(), this.getClass().getClassLoader(),
				context);
		
		assertSimpleObjectArray(parentObj);
	}
	
	public void testParentObjectSimpleObjectArrayWithDefaultName() {
		Context context = new ContextImpl();
		
		context.put("parentObject.simpleObjectArray.name", new String[] { "name1", "name2" });
		context.put("parentObject.simpleObjectArray.length2",new String[] { "1", "2" } );
		context.put("parentObject.simpleObjectArray.length", new String[] { "12", "22" });
		context.put("parentObject.simpleObjectArray.flag", new String[] { "true", "false" });
		
		ParentObject parentObj = (ParentObject) generator.getObject(null, null,
				ParentObject.class.getName(), this.getClass().getClassLoader(),
				context);
		
		assertSimpleObjectArray(parentObj);
	}
	
	private void assertSimpleObjectArray(ParentObject parentObj) {
		assertEquals(2, parentObj.getSimpleObjectArray().length);

		assertEquals("name1", parentObj.getSimpleObjectArray()[0].getName());
		assertEquals(1, parentObj.getSimpleObjectArray()[0].getLength2());
		assertEquals(Integer.valueOf(12), parentObj.getSimpleObjectArray()[0]
				.getLength());
		assertEquals(Boolean.TRUE, parentObj.getSimpleObjectArray()[0]
				.getFlag());
		
		assertEquals("name2", parentObj.getSimpleObjectArray()[1].getName());
		assertEquals(2, parentObj.getSimpleObjectArray()[1].getLength2());
		assertEquals(Integer.valueOf(22), parentObj.getSimpleObjectArray()[1]
				.getLength());
		assertEquals(Boolean.FALSE, parentObj.getSimpleObjectArray()[1]
				.getFlag());
	}
}
