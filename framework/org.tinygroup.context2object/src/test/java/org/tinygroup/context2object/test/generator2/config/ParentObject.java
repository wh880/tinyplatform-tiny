package org.tinygroup.context2object.test.generator2.config;

import java.util.List;

import org.tinygroup.context2object.test.convert.EnumObject;

public class ParentObject {
	String pro;
	SimpleObject simpleObject;
	SimpleObject[] simpleObjectArray;
	List<SimpleObject> simpleObjectList;
	List<EnumObject> enumObjectList;
	EnumObject[] enumObjectArray;
	EnumObject enumObject;
	List<Integer> lengths;

	public List<Integer> getLengths() {
		return lengths;
	}

	public void setLengths(List<Integer> lengths) {
		this.lengths = lengths;
	}

	public SimpleObject[] getSimpleObjectArray() {
		return simpleObjectArray;
	}

	public void setSimpleObjectArray(SimpleObject[] simpleObjectArray) {
		this.simpleObjectArray = simpleObjectArray;
	}

	public List<SimpleObject> getSimpleObjectList() {
		return simpleObjectList;
	}

	public void setSimpleObjectList(List<SimpleObject> simpleObjectList) {
		this.simpleObjectList = simpleObjectList;
	}

	public String getPro() {
		return pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	public SimpleObject getSimpleObject() {
		return simpleObject;
	}

	public void setSimpleObject(SimpleObject simpleObject) {
		this.simpleObject = simpleObject;
	}

	public List<EnumObject> getEnumObjectList() {
		return enumObjectList;
	}

	public void setEnumObjectList(List<EnumObject> enumObjectList) {
		this.enumObjectList = enumObjectList;
	}

	public EnumObject[] getEnumObjectArray() {
		return enumObjectArray;
	}

	public void setEnumObjectArray(EnumObject[] enumObjectArray) {
		this.enumObjectArray = enumObjectArray;
	}

	public EnumObject getEnumObject() {
		return enumObject;
	}

	public void setEnumObject(EnumObject enumObject) {
		this.enumObject = enumObject;
	}

}
