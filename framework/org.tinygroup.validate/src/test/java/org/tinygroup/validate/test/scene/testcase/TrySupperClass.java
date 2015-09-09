package org.tinygroup.validate.test.scene.testcase;

import java.lang.reflect.Field;

public class TrySupperClass {
	public static void main(String[] args) {
		trySupperClass(TrySupperClass.class);
		getFiled("name", TrySupperClass.class, true);
	}

	public static void trySupperClass(Class clazz) {
		Class superclass = clazz.getSuperclass();
		System.out.println(superclass);
		if (superclass != null) {
			trySupperClass(superclass);
		}
	}

	private static Field getFiled(String fieldName, Class clazz,
			boolean includeParentClass) {
		try {
			Field field = clazz.getField(fieldName);
			return field;
		} catch (Exception e) {
			if (clazz.getSuperclass() == null)
				throw new RuntimeException( new NoSuchFieldException(fieldName));
			return getFiled(fieldName, clazz.getSuperclass(), true);
		}
	}
}
