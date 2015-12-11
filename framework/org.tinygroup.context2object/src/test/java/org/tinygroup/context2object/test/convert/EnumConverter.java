package org.tinygroup.context2object.test.convert;

import org.tinygroup.context2object.TypeConverter;

public class EnumConverter implements TypeConverter<String, EnumObject> {

	public Class<String> getSourceType() {

		return String.class;
	}

	public Class<EnumObject> getDestinationType() {
		// TODO Auto-generated method stub
		return EnumObject.class;
	}

	public EnumObject getObject(String value) {
		if (value == "MON")
			return EnumObject.MON;
		else
			return EnumObject.FRI;
	}

}
