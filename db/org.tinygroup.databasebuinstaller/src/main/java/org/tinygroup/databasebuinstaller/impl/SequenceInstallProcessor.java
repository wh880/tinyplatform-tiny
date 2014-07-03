package org.tinygroup.databasebuinstaller.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.database.sequence.SequenceProcessor;

public class SequenceInstallProcessor extends AbstractInstallProcessor {
	
	private SequenceProcessor processor;

	public SequenceProcessor getProcessor() {
		return processor;
	}
	public void setProcessor(SequenceProcessor processor) {
		this.processor = processor;
	}
	protected List<String> getDealSqls(Connection con) throws SQLException {
		List<String> sqls=new ArrayList<String>();
		sqls.addAll(processor.getCreateSql(language));
		return sqls;
	}

}
