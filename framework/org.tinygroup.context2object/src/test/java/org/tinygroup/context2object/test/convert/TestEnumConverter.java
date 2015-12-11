package org.tinygroup.context2object.test.convert;

import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.testcase.BastTestCast;

public class TestEnumConverter extends BastTestCast{
	public void testEnumArray() {
		Context context = new ContextImpl();
		String[] names = { "MON", "FRI",  };
		context.put("enumObject", names);
		EnumObject[] parts = (EnumObject[]) generator.getObjectArray("enumObject", EnumObject.class.getName(),this.getClass().getClassLoader(), context);
		
		assertEquals(2, parts.length);
		assertEquals(parts[0], EnumObject.MON);
		assertEquals(parts[1], EnumObject.FRI);
		
	}
	
	
	
	public void testEnumList() {
		Context context = new ContextImpl();
		String[] names = { "MON", "FRI",  };
		context.put("enumObject", names);
		List<EnumObject> parts = (List<EnumObject>) generator.getObjectCollection("enumObject", List.class.getName(),EnumObject.class.getName(),this.getClass().getClassLoader(), context);
		
		assertEquals(2, parts.size());
		assertEquals(parts.get(0), EnumObject.MON);
		assertEquals(parts.get(1), EnumObject.FRI);
		
	}
	public void testBeanEnum() {
		Context context = new ContextImpl();
		String[] names = { "MON", "FRI",  };
		context.put("enumBean.array", names);
		context.put("enumBean.list", names);
		EnumBean parts = (EnumBean) generator.getObject("enumBean", null,EnumBean.class.getName(),this.getClass().getClassLoader(), context);
		
		assertEquals(2, parts.getArray().length);
		assertEquals(parts.getArray()[0], EnumObject.MON);
		assertEquals(parts.getArray()[1], EnumObject.FRI);
		
		assertEquals(2, parts.getList().size());
		assertEquals(parts.getList().get(0), EnumObject.MON);
		assertEquals(parts.getList().get(1), EnumObject.FRI);
		
	}
}
