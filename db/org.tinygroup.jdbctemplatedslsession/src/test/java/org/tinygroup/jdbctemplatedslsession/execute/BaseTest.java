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
package org.tinygroup.jdbctemplatedslsession.execute;

import junit.framework.TestCase;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.Resources;
import org.tinygroup.tinytestutil.AbstractTestUtil;
import org.tinygroup.tinytestutil.script.ScriptRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseTest extends TestCase {
	private boolean hasExecuted;
	protected DataSource dataSource;
	void init() {
		AbstractTestUtil.init(null, true);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (!hasExecuted) {
			init();
			dataSource = BeanContainerFactory.getBeanContainer(
					getClass().getClassLoader()).getBean("dataSource");
			initTable();
			hasExecuted = true;
		}
	}

	private void initTable() throws IOException, Exception {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			ScriptRunner runner = new ScriptRunner(conn, false, false);
			// 设置字符集
			Resources.setCharset(Charset.forName("utf-8"));
			// 加载sql脚本并执行
			runner.runScript(Resources.getResourceAsReader("table_derby.sql"));
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					fail(e.getMessage());
				}
			}
		}

	}

}
