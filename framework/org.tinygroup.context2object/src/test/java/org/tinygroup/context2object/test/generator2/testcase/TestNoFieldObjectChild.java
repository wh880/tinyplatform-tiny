/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.NoFieldObject;
import org.tinygroup.context2object.test.generator2.config.NoFieldObjectChild;

/**
 * @author ywj
 *
 */
public class TestNoFieldObjectChild extends BaseTestCast2 {

	public void testSimpleProperty(){
		Context context = new ContextImpl();
		context.put("bean.name", "name");
		context.put("bean.age", "11");
		NoFieldObjectChild bean = (NoFieldObjectChild) generator.getObject("bean", null,
				NoFieldObjectChild.class.getName(), this.getClass().getClassLoader(),
				context);
		assertEquals("name",bean.getName());
		assertEquals(Integer.valueOf(11), bean.getAge());
	}
	
}
