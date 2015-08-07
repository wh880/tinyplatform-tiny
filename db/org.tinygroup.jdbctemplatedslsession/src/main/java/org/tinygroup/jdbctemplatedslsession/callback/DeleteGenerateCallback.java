package org.tinygroup.jdbctemplatedslsession.callback;

import org.tinygroup.tinysqldsl.Delete;

public interface DeleteGenerateCallback<T> {
	
	public Delete generate(T t);

}
