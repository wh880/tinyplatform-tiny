package org.tinygroup.databasebuinstaller.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.database.config.trigger.Trigger;
import org.tinygroup.database.trigger.TriggerProcessor;

public class TriggerInstallProcessor extends AbstractInstallProcessor {
	
	private TriggerProcessor processor;
	
	public TriggerProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(TriggerProcessor processor) {
		this.processor = processor;
	}

	protected List<String> getDealSqls(Connection con) throws SQLException {
		List<String> sqls=new ArrayList<String>();
		List<Trigger> triggers=processor.getTriggers(language);
		for (Trigger trigger : triggers) {
			if(!processor.checkTriggerExist(language, trigger, con)){
				sqls.add(processor.getCreateSql(trigger.getName(), language));
			}
		}
		return sqls;
	}

}
