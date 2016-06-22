package org.tinygroup.jdbctemplatedslsession;

import org.apache.commons.lang.math.RandomUtils;
import org.tinygroup.tinysqldsl.KeyGenerator;
import org.tinygroup.tinysqldsl.base.InsertContext;

public class MyIntKeyGenerator implements KeyGenerator {

	@SuppressWarnings("unchecked")
	public Integer generate(InsertContext insertContext) {
		return RandomUtils.nextInt(1000);
	}

}
