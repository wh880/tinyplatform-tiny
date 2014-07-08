package org.tinygroup.database.trigger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.tinygroup.database.config.trigger.Trigger;
import org.tinygroup.database.config.trigger.Triggers;

/**
 * 触发器处理
 * @author renhui
 *
 */
public interface TriggerProcessor {
	
	public void addTriggers(Triggers triggers);
	
	public void removeTriggers(Triggers triggers);
	
	public Trigger getTrigger(String triggerName);
	
	public String getCreateSql(String triggerName,String language);
	
	public String getDropSql(String triggerName,String language);
	
	public List<String> getCreateSql(String language);
	
	public List<String> getDropSql(String language);
	
	public List<Trigger> getTriggers(String language);
	
	public  boolean checkTriggerExist(String language,Trigger trigger,Connection connection)throws SQLException;
	
	
}
