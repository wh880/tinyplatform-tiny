package org.tinygroup.format.test;

import junit.framework.TestCase;

import org.tinygroup.context.Context;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.format.Formater;
import org.tinygroup.format.impl.ContextFormater;
import org.tinygroup.format.impl.FormaterImpl;

public class TestFormater extends TestCase{
	
	public void testString(){
		Formater formater = new FormaterImpl();
		formater.addFormatProvider("", new ContextFormater());
		Context context = ContextFactory.getContext();
		String string = "${a}";
		context.put("a", "hello");
		string = formater.format(context, string);
		System.out.println(string);
		assertEquals( "hello",string);
	}
	
	public void testStringPost(){
		Formater formater = new FormaterImpl();
		formater.addFormatProvider("", new ContextFormater());
		Context context = ContextFactory.getContext();
		String string = "${a}a";
		context.put("a", "hello");
		string = formater.format(context, string);
		System.out.println(string);
		assertEquals( "helloa",string);
	}
	
	public void testStringPre(){
		Formater formater = new FormaterImpl();
		formater.addFormatProvider("", new ContextFormater());
		Context context = ContextFactory.getContext();
		String string = "a${a}";
		context.put("a", "hello");
		string = formater.format(context, string);
		System.out.println(string);
		assertEquals( "ahello",string);
	}
	
	public void testStringWithOther1(){
		Formater formater = new FormaterImpl();
		formater.addFormatProvider("", new ContextFormater());
		Context context = ContextFactory.getContext();
		String string = "\"\\/a${a}\"";
		context.put("a", "hello");
		string = formater.format(context, string);
		System.out.println(string);
		assertEquals( "\"\\/ahello\"",string);
	}
	
	
	public void testStringWithOther2(){
		Formater formater = new FormaterImpl();
		formater.addFormatProvider("", new ContextFormater());
		Context context = ContextFactory.getContext();
		String string = "-+=<!--CDATA---->\"\\/a${a}\"";
		context.put("a", "hello");
		string = formater.format(context, string);
		System.out.println(string);
		assertEquals( "-+=<!--CDATA---->\"\\/ahello\"",string);
	}

}
