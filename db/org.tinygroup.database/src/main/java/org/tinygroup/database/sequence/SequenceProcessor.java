/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.database.sequence;

import org.tinygroup.database.config.sequence.Sequence;
import org.tinygroup.database.config.sequence.Sequences;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


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
