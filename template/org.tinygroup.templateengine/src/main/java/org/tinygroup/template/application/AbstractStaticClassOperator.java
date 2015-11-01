package org.tinygroup.template.application;

import org.tinygroup.template.StaticClassOperator;

public abstract class AbstractStaticClassOperator implements StaticClassOperator{

	private String name;
	private Class<?> clazz;
	
	public AbstractStaticClassOperator(){
		
	}
	
	public AbstractStaticClassOperator(String name,Class<?> clazz){
		this.name = name;
		this.clazz = clazz;
	}
	
	public Class<?> getStaticClass() {
		return clazz;
	}
	
	public void setStaticClass(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
