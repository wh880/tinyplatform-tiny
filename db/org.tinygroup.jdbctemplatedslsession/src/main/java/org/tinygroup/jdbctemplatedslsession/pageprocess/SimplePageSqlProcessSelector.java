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
package org.tinygroup.jdbctemplatedslsession.pageprocess;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.jdbctemplatedslsession.PageSqlMatchProcess;
import org.tinygroup.jdbctemplatedslsession.PageSqlProcessSelector;

/**
 * 简单的分页处理选择器实现
 * 
 * @author renhui
 * 
 */
public class SimplePageSqlProcessSelector implements PageSqlProcessSelector {

	private List<PageSqlMatchProcess> processes = new ArrayList<PageSqlMatchProcess>();

	public SimplePageSqlProcessSelector() {
		super();
		processes.add(new MysqlPageSqlMatchProcess());
		processes.add(new OraclePageSqlMatchProcess());
		processes.add(new SqlServerPageSqlMatchProcess());
		processes.add(new DerbyPageSqlMatchProcess());
		processes.add(new DB2PageSqlMatchProcess());
	}

	public PageSqlMatchProcess pageSqlProcessSelect(String dbType) {
		for (PageSqlMatchProcess process : processes) {
			if (process.isMatch(dbType)) {
				return process;
			}
		}
		throw new RuntimeException(String.format(
				"根据数据库类型:%s,获取不到相应的PageSqlMatchProcess分页处理器", dbType));
	}

	public List<PageSqlMatchProcess> getProcesses() {
		return processes;
	}

	public void setProcesses(List<PageSqlMatchProcess> processes) {
		this.processes = processes;
	}
	
}
