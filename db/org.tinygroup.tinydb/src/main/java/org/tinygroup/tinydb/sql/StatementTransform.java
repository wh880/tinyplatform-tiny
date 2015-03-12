package org.tinygroup.tinydb.sql;

import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.exception.TinyDbException;

public interface StatementTransform {
	
	public SqlAndValues toSelect(Bean bean)throws TinyDbException;
	
	public String toInsert(Bean bean) throws TinyDbException;

	public String toDelete(Bean bean) throws TinyDbException ;

	public String toUpdate(Bean bean) throws TinyDbException;

	
}
