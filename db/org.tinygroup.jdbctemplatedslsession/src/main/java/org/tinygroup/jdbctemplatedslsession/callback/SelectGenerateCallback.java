package org.tinygroup.jdbctemplatedslsession.callback;

import org.tinygroup.tinysqldsl.Select;

public interface SelectGenerateCallback<T> {

	Select generate(T t);
	
}
