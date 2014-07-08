package org.tinygroup.database.sequence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.tinygroup.database.config.sequence.Sequence;
import org.tinygroup.database.config.sequence.Sequences;


/**
 * sequence处理器
 * @author renhui
 *
 */
public interface SequenceProcessor {

	public void addSequences(Sequences sequences);
	
	public void removeSequences(Sequences sequences);
	
	public Sequence getSequence(String sequenceName);
	
	public String getCreateSql(String sequenceName,String language);
	
	public String getDropSql(String sequenceName,String language);
	
	public List<String> getCreateSql(String language);
	
	public List<String> getDropSql(String language);
	
	public List<Sequence> getSequences(String language);
	
	public  boolean checkSequenceExist(String language,Sequence sequence,Connection connection)throws SQLException;
}
