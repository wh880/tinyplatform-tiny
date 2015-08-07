package org.tinygroup.jdbctemplatedslsession.callback;

import org.tinygroup.tinysqldsl.Select;

public interface SelectGenerateCallback<T> {

	public Select generate(T t);
	
}
