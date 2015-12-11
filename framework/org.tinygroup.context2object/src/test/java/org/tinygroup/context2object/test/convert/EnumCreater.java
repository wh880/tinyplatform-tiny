package org.tinygroup.context2object.test.convert;

import org.tinygroup.context2object.TypeCreator;

public class EnumCreater implements TypeCreator<EnumObject> {

	public Class<EnumObject> getType() {
		return EnumObject.class;
	}

	public EnumObject getInstance() {
		// TODO Auto-generated method stub
		return EnumObject.MON;
	}

	

}
