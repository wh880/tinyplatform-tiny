/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.bean.CatInterface;
import org.tinygroup.context2object.test.bean.People2;
import org.tinygroup.context2object.test.bean.SmallCat;


public class TestList extends BaseTestCast2 {

	public void testListObject() {
		Context context = new ContextImpl();
		String[] names = { "tomcat", "name1", "name2" };
		String[] colors = { "red", "coller", "coller2" };
		context.put("smallCat.name", names);
		context.put("smallCat.coller", colors);
		Collection<Object> parts = generator.getObjectCollection(null,List.class.getName(), SmallCat.class.getName(),this.getClass().getClassLoader(), context);
		assertEquals(3, parts.size());
		Iterator<Object> iterator = parts.iterator();
		String catNames = "";
		String catColors = "";
		while(iterator.hasNext()){
			SmallCat cat = (SmallCat) iterator.next();
			catNames = catNames+cat.getName();
			catColors = catColors+cat.getColler();
		}
		System.out.println(catNames);
		System.out.println(catColors);
		assertEquals(true, catNames.contains("tomcat")&&catNames.contains("name1")&&catNames.contains("name2"));
		assertEquals(true, catColors.contains("red")&&catColors.contains("coller")&&catColors.contains("coller2"));
	}

	public void testListInterface() {
		Context context = new ContextImpl();
		String[] names = { "tomcat", "name1", "name2" ,"name3"};
		String[] colors = { "red", "coller", "coller2" };
		context.put("smallCat.name", names);
		context.put("smallCat.coller", colors);
		Collection<Object> parts = generator.getObjectCollection(null,List.class.getName(), CatInterface.class.getName(),this.getClass().getClassLoader(), context);
		assertEquals(3, parts.size());
		Iterator<Object> iterator = parts.iterator();
		String catNames = "";
		while(iterator.hasNext()){
			CatInterface cat = (CatInterface) iterator.next();
			catNames = catNames+cat.getName();
					}
		System.out.println(catNames);
		assertEquals(true, catNames.contains("tomcat")&&catNames.contains("name1")&&catNames.contains("name2"));
	}
	
	
	public void testListString() {
		Context context = new ContextImpl();
		String[] names = { "tomcat", "name1", "name2" };
		context.put("a", names);
		Collection<Object> parts = generator.getObjectCollection("a",List.class.getName(), String.class.getName(),this.getClass().getClassLoader(), context);
		assertEquals(3, parts.size());
		Iterator<Object> iterator = parts.iterator();
		String strings = "";
		while(iterator.hasNext()){
			String s = (String) iterator.next();
			strings = strings+s;
					}
		System.out.println(strings);
		assertEquals(true, strings.contains("tomcat")&&strings.contains("name1")&&strings.contains("name2"));
	}
	
	public void testListBoolean() {
		Context context = new ContextImpl();
		String[] names = { "true", "true", "true" };
		context.put("a", names);
		Collection<Boolean> parts = generator.getObjectCollection("a",List.class.getName(), Boolean.class.getName(),this.getClass().getClassLoader(), context);
		assertEquals(3, parts.size());
		Iterator<Boolean> iterator = parts.iterator();
		String strings = "";
		while(iterator.hasNext()){
			Boolean s = iterator.next();
			assertEquals(Boolean.TRUE, s);
					}
		
	}
	
	public void testListStringProperty() {
		Context context = new ContextImpl();
		String[] names = { "tomcat", "name1", "name2" };
		context.put("people2.names", names);
		People2 people = (People2) generator.getObject(null,null, People2.class.getName(),this.getClass().getClassLoader(), context);
		assertEquals(3,people.getNames().size());
		assertEquals(true, people.getNames().get(0).equals("tomcat"));
		assertEquals(true, people.getNames().get(1).equals("name1"));
		assertEquals(true, people.getNames().get(2).equals("name2"));
	}
	
	
}
