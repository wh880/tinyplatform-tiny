package org.tinygroup.database.sequence.impl;

import java.sql.Connection;

import org.tinygroup.database.config.sequence.Sequence;
import org.tinygroup.database.sequence.SequenceSqlProcessor;

public abstract class AbstractSequenceSqlProcessor implements
		SequenceSqlProcessor {


	public String getDropSql(Sequence sequence) {
		return "DROP SEQUENCE "+sequence.getName();
	}
	
	public String checkSequenceExist(Sequence sequence, Connection connection) {
		
		try {
			
		} finally{
			
		}
		
		
		return null;
	}

}
