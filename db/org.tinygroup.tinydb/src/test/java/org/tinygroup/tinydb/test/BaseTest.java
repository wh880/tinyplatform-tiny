/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.tinydb.test;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.BeanOperatorManager;
import org.tinygroup.tinydb.config.SchemaConfig;
import org.tinygroup.tinydb.operator.DBOperator;
import org.tinygroup.tinydb.script.Resources;
import org.tinygroup.tinydb.script.ScriptRunner;
import org.tinygroup.tinydb.test.operator.BeanStringOperator;
import org.tinygroup.tinydb.util.DataSourceFactory;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public abstract class BaseTest extends TestCase {
	protected  static BeanOperatorManager manager;
	private static DBOperator<String> operator;
	protected static String ANIMAL = "animal";
	protected static String PEOPLE = "aPeople";
	protected static String BRANCH = "aBranch";
	String mainSchema = "opensource";
	private static boolean hasExcuted = false;

	public DBOperator<String> getOperator() {
		return operator;
	}


	public void setOperator(DBOperator<String> operator) {
		BaseTest.operator = operator;
	}

	@SuppressWarnings("unchecked")
	public void setUp() {
		if (!hasExcuted) {
			Connection conn = null;
			try {
				AbstractTestUtil.init(null, true);
				conn = DataSourceFactory.getConnection("dynamicDataSource");
				ScriptRunner runner = new ScriptRunner(conn, false, false);
				// 设置字符集
				Resources.setCharset(Charset.forName("utf-8"));
				// 加载sql脚本并执行
				try {
					runner.runScript(Resources
							.getResourceAsReader("table_derby.sql"));
				} catch (Exception e) {
					// e.printStackTrace();
				}
				manager = SpringUtil.getBean("beanOperatorManager");
				registerBean();
				// people_id
				// people_name
				// people_age
				// branch
				// registerBean();
				// registerBean();
//				manager.initBeansConfiguration();
				operator = (DBOperator<String>) manager.getDbOperator(
						mainSchema, ANIMAL);
				hasExcuted = true;
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

	private void registerBean() {

		BeanStringOperator beanOperator = (BeanStringOperator) SpringUtil
				.getBean("beanStringOperator");
		beanOperator.setSchema(mainSchema);
		manager.setMainSchema(mainSchema);
		SchemaConfig config = new SchemaConfig(mainSchema,
				"beanStringOperator", "increase", "%");
		manager.registerSchemaConfig(config);
		List<String> list = new ArrayList<String>();
		list.add(mainSchema);
		manager.loadTablesFromSchemas();
		manager.initBeansConfiguration();
	}
	
}
