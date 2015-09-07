/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.databasebuinstaller.impl;

import org.tinygroup.database.config.sequence.Sequence;
import org.tinygroup.database.sequence.SequenceProcessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SequenceInstallProcessor extends AbstractInstallProcessor {
	
	private SequenceProcessor processor;

	public SequenceProcessor getProcessor() {
		return processor;
	}
	public void setProcessor(SequenceProcessor processor) {
		this.processor = processor;
	}
	
	private List<String> getSqls(String language, Connection con,boolean isFull) throws SQLException {
		List<String> sqls=new ArrayList<String>();
		List<Sequence> sequences=processor.getSequences(language);
		for (Sequence sequence : sequences) {
			//全量或者不存在
			if(isFull || !processor.checkSequenceExist(language, sequence, con)){
				sqls.add(processor.getCreateSql(sequence.getName(), language));
			}
		}
		return sqls;
	}
	
	public List<String> getDealSqls(String language, Connection con) throws SQLException {
		return getUpdateSqls(language, con);
	}
	public List<String> getFullSqls(String language, Connection con)
			throws SQLException {
		return getSqls(language, con,true);
	}
	public List<String> getUpdateSqls(String language, Connection con)
			throws SQLException {
		return getSqls(language, con,false);
	}

}
