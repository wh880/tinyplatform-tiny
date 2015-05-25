package org.tinygroup.context2object.test.bean;

public class BeanField {
	String name;
	BeanNoField field;
	public String getName() {
		return name;
	}
	public void setNameNoExist(String name) {
		this.name = name;
	}
	public String getNameNoExist() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BeanNoField getField() {
		return field;
	}
	public void setField(BeanNoField field) {
		this.field = field;
	}
	public BeanNoField getFieldNoExist() {
		return field;
	}
	public void setFieldNoExist(BeanNoField field) {
		this.field = field;
	}
	
}