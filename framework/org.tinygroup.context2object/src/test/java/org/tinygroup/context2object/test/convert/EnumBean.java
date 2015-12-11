package org.tinygroup.context2object.test.convert;

import java.util.List;

public class EnumBean {
	EnumObject[] array;
	List<EnumObject> list;
	List<EnumBeanSimple> simpleList;
	EnumBeanSimple[] simpleArray;

	public EnumObject[] getArray() {
		return array;
	}

	public void setArray(EnumObject[] array) {
		this.array = array;
	}

	public List<EnumObject> getList() {
		return list;
	}

	public void setList(List<EnumObject> list) {
		this.list = list;
	}

	public List<EnumBeanSimple> getSimpleList() {
		return simpleList;
	}

	public void setSimpleList(List<EnumBeanSimple> simpleList) {
		this.simpleList = simpleList;
	}

	public EnumBeanSimple[] getSimpleArray() {
		return simpleArray;
	}

	public void setSimpleArray(EnumBeanSimple[] simpleArray) {
		this.simpleArray = simpleArray;
	}
	
}
