package org.tinygroup.service.test.serviceinterfacetest.service;

import java.io.Serializable;

public class User implements Serializable{
	
	private String name;
	
	private int age;
	
	private boolean sex;
	
	public User() {
		super();
	}

	public User(String name, int age, boolean sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
}
