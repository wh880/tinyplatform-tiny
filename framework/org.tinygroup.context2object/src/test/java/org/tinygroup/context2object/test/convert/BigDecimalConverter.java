package org.tinygroup.context2object.test.convert;

import java.math.BigDecimal;

import org.tinygroup.context2object.TypeConverter;

public class BigDecimalConverter implements TypeConverter<String, BigDecimal>{

	public Class<String> getSourceType() {
		// TODO Auto-generated method stub
		return String.class;
	}

	public Class<BigDecimal> getDestinationType() {
		// TODO Auto-generated method stub
		return BigDecimal.class;
	}

	public BigDecimal getObject(String value) {
		// TODO Auto-generated method stub
		return new BigDecimal(value);
	}
	
}
