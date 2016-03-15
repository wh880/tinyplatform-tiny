package org.tinygroup.mockservice.test;

import java.util.HashMap;
import java.util.Map;

public class Obj1 {
	Map<String,String> map = new HashMap<String, String>();
	String value;
	String name;
	int length;
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public Obj1(String value, String name, int length) {
		super();
		this.value = value;
		this.name = name;
		this.length = length;
	}
	public Obj1() {
	}
	
}
