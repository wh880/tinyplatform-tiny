package org.tinygroup.webservicetest;

public class UserImpl implements User{
	private String name;
	private String age;

	public UserImpl(String name,String age){
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}

	public String getAge() {
		return age;
	}

}
