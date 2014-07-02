package org.tinygroup.ini.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;

import org.tinygroup.ini.IniOperator;
import org.tinygroup.ini.ValuePair;

public class IniOperatorDefaultFileTest extends TestCase {

	public void test() {
		String dir = System.getProperty("user.dir");
		String file = dir + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "test.ini";
		IniOperator operator = new IniOperatorDefault();

		try {
			operator.read(new FileInputStream(new File(file)), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

		String name = operator.get("Section1", "name");
		assertEquals("a", name);
		int age = operator.get(int.class, "Section1", "age");
		assertEquals(11, age);
		int left = operator.get(int.class, "Section2", "left");
		assertEquals(1, left);
		operator.set("Section1", new ValuePair("name", "b"));
		
		String file2 = dir + File.separator + "src" + File.separator + "test"
		+ File.separator + "resources" + File.separator + "test2.ini";
		try {
			operator.write(new FileOutputStream(new File(file2)), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
		IniOperator operator2 = new IniOperatorDefault();
		try {
			operator2.read(new FileInputStream(new File(file2)), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		String name2 = operator2.get("Section1", "name");
		assertEquals("b", name2);
	}
}
