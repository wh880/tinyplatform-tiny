package org.tinygroup.databasebuinstaller.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		sqls.addAll(processor.getCreateSql(language));
		return sqls;
	}

}
