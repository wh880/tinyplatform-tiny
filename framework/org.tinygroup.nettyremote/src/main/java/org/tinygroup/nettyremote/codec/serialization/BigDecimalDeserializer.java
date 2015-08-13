package org.tinygroup.nettyremote.codec.serialization;

import java.math.BigDecimal;

import com.caucho.hessian.io.AbstractStringValueDeserializer;

public class BigDecimalDeserializer extends AbstractStringValueDeserializer {

	@Override
	public Class getType() {
		return BigDecimal.class;
	}

	@Override
	protected Object create(String value) {
		if (null != value) {
			return new BigDecimal(value);
		} else {
			return null;
		}
	}
}