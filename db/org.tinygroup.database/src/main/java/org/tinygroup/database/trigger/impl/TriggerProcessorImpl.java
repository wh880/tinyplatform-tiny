package org.tinygroup.database.trigger.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.database.config.SqlBody;
import org.tinygroup.database.config.trigger.Trigger;
import org.tinygroup.database.config.trigger.Triggers;
import org.tinygroup.database.trigger.TriggerProcessor;

public class TriggerProcessorImpl implements TriggerProcessor {
	
	private Map<String, Trigger> triggerMap=new HashMap<String, Trigger>();

	public void addTriggers(Triggers triggers) {
		for(Trigger trigger:triggers.getTriggers()){
			triggerMap.put(trigger.getName(), trigger);
		}
	}

	public void removeTriggers(Triggers triggers) {
		for(Trigger trigger:triggers.getTriggers()){
			triggerMap.remove(trigger.getName());
		}
	}

	public Trigger getTrigger(String triggerName) {
         return triggerMap.get(triggerName);
	}

	public String getCreateSql(String triggerName, String language) {
		Trigger trigger=getTrigger(triggerName);
		if(trigger==null){
			throw new RuntimeException(String.format("trigger[name:%s]不存在,",triggerName));
		}
		return getCreateSql(trigger,language);
	}

	private String getCreateSql(Trigger trigger, String language) {
		List<SqlBody> sqls=trigger.getTriggerSqls(); 
		for (SqlBody sqlBody : sqls) {
			if(sqlBody.getDialectTypeName().equals(language)){
				return sqlBody.getContent();
			}
		}
		throw new RuntimeException(String.format("[language:%s]对应的trigger不存在,",language));
	}

	public String getDropSql(String triggerName, String language) {
		Trigger trigger=getTrigger(triggerName);
		if(trigger==null){
			throw new RuntimeException(String.format("trigger[name:%s]不存在,",triggerName));
		}
		return getDropSql(trigger,language);
	}

	private String getDropSql(Trigger trigger, String language) {
		return "DROP TRIGGER "+trigger.getName();
	}

	public List<String> getCreateSql(String language) {
		List<String> sqls=new ArrayList<String>();
		for (Trigger trigger : triggerMap.values()) {
			sqls.add(getCreateSql(trigger, language));
		}
		return sqls;
	}

	public List<String> getDropSql(String language) {
		List<String> sqls=new ArrayList<String>();
		for (Trigger trigger : triggerMap.values()) {
			sqls.add(getDropSql(trigger, language));
		}
		return sqls;
	}

}
