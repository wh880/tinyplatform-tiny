package org.tinygroup.context2object.test.testcase.other;

import org.tinygroup.context2object.test.bean.SmallCat;

import junit.framework.TestCase;

public class TestLoadArray extends TestCase{
	public void testArray(){
		try {
			this.getClass().getClassLoader().loadClass(SmallCat[].class.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
