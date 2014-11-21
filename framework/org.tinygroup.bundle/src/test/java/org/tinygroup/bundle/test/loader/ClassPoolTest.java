package org.tinygroup.bundle.test.loader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import javassist.ClassClassPath;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import junit.framework.TestCase;

import org.tinygroup.bundle.loader.TinyClassLoader;

public class ClassPoolTest extends TestCase {
	public void testPath() {
		ClassPool pool = new ClassPool(false);
		String filePath = System.getProperty("user.dir") + File.separator
				+ "testJar" + File.separator + "*";
		File file = new File(System.getProperty("user.dir") + File.separator
				+ "testJar" + File.separator
				+ "org.tinygroup.bundlejar-1.2.0-SNAPSHOT.jar");
		
		try {
			pool.appendClassPath(filePath);
		} catch (NotFoundException e2) {
			e2.printStackTrace();
			assertTrue(false);
		}
		ClassLoader loader = null;
		try {
			loader = new TinyClassLoader(new URL[] { file.toURI().toURL() });
			System.out.println(file.getPath());
		} catch (MalformedURLException e1) {
			assertTrue(false);
		}
//		for(URL url:loader.getURLs()){
//			System.out.println(url.toString());
//		}
		
		Class<?> clazz = null;
		try {
			clazz = loader
					.loadClass("org.tinygroup.bundlejar.BundleTestObject");
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
			assertTrue(false);
		}
		pool.insertClassPath(new ClassClassPath(clazz));
		Method[] methods = clazz.getMethods();
		Method m = null;
		for (Method method : methods) {
			if (method.getName().equals("setName")) {
				m = method;
			}
		}
		if (m == null) {
			assertTrue(false);
		}
		try {
			CtClass cc = pool.get(clazz.getName());
			CtMethod cm = cc.getDeclaredMethod(m.getName(),
					getParaClasses(pool, cc, m));

			// 使用javaassist的反射方法获取方法的参数名
			MethodInfo methodInfo = cm.getMethodInfo();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
					.getAttribute(LocalVariableAttribute.tag);
			String[] paramNames = new String[cm.getParameterTypes().length];
			int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
			for (int i = 0; i < paramNames.length; i++) {
				paramNames[i] = attr.variableName(i + pos);
			}
			assertEquals("name", paramNames[0]);
		} catch (NotFoundException e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	private static CtClass[] getParaClasses(ClassPool pool, CtClass cc,
			Method method) throws NotFoundException {
		CtClass[] paType = null;
		Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes != null && parameterTypes.length > 0) {
			paType = new CtClass[parameterTypes.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				paType[i] = pool.get(parameterTypes[i].getName());
			}
		}
		return paType;
	}
}
