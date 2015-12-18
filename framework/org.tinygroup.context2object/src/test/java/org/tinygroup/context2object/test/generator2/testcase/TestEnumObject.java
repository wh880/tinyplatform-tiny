/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.convert.EnumObject;
import org.tinygroup.context2object.test.generator2.config.EnumSimpleObject;

/**
 * @author Administrator
 *
 */
public class TestEnumObject extends BaseTestCast2 {

	public void testEnumSimple(){
		Context context = new ContextImpl();
		context.put("enumObject", EnumObject.MON);
		context.put("name", "a");
		
		EnumSimpleObject obj = (EnumSimpleObject) generator.getObject(null, null, EnumSimpleObject.class.getName(), this.getClass().getClassLoader(), context);
		assertEquals(EnumObject.MON, obj.getEnumObject());
		assertEquals("a", obj.getName());
	}
	
	public void testEnumSimple1(){
		Context context = new ContextImpl();
		context.put("enumSimpleObject.enumObject", EnumObject.MON);
		context.put("enumSimpleObject.name", "a");
		
		EnumSimpleObject obj = (EnumSimpleObject) generator.getObject(null, null, EnumSimpleObject.class.getName(), this.getClass().getClassLoader(), context);
		assertEquals(EnumObject.MON, obj.getEnumObject());
		assertEquals("a", obj.getName());
	}
	
	public void testEnumSimple2(){
		Context context = new ContextImpl();
		context.put("abc.enumObject", EnumObject.MON);
		context.put("abc.name", "a");
		
		EnumSimpleObject obj = (EnumSimpleObject) generator.getObject("abc", null, EnumSimpleObject.class.getName(), this.getClass().getClassLoader(), context);
		assertEquals(EnumObject.MON, obj.getEnumObject());
		assertEquals("a", obj.getName());
	}
	
}
