package org.tinygroup.context2object.test.convert;

import java.math.BigDecimal;
import java.util.List;

public class BigDecimalBean {
	BigDecimal[] array;
	List<BigDecimal> list;
	List<BigDecimalObject> objects;
	public BigDecimal[] getArray() {
		return array;
	}
	public void setArray(BigDecimal[] array) {
		this.array = array;
	}
	public List<BigDecimal> getList() {
		return list;
	}
	public void setList(List<BigDecimal> list) {
		this.list = list;
	}
	public List<BigDecimalObject> getObjects() {
		return objects;
	}
	public void setObjects(List<BigDecimalObject> objects) {
		this.objects = objects;
	}
	
}
