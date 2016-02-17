package org.tinygroup.jdbctemplatedslsession;

import java.util.UUID;

import org.tinygroup.tinysqldsl.KeyGenerator;
import org.tinygroup.tinysqldsl.base.InsertContext;

public class MyKeyGenerator implements KeyGenerator {

	@SuppressWarnings("unchecked")
	public String generate(InsertContext insertContext) {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
