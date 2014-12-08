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
package org.tinygroup.tinydb.test;

import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.tinygroup.commons.tools.Resources;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.Configuration;
import org.tinygroup.tinydb.ConfigurationBuilder;
import org.tinygroup.tinydb.DbOperatorFactory;
import org.tinygroup.tinydb.DbOperatorFactoryBuilder;
import org.tinygroup.tinydb.operator.DBOperator;
import org.tinygroup.tinytestutil.script.ScriptRunner;

public abstract class BaseTest extends TestCase {
	protected static DbOperatorFactory factory;
	protected static DBOperator<String> operator;
	protected static String ANIMAL = "animal";
	protected static String PEOPLE = "aPeople";
	protected static String BRANCH = "aBranch";
	String mainSchema = "opensource";
	private static boolean hasExcuted = false;

	public DBOperator<String> getOperator() {
		return operator;
	}

	
	public void setUp() {
		if (!hasExcuted) {
			initTable();
			initFactory();
			hasExcuted= true;
		}

	}
	
	private void initTable(){
		Connection conn = null;
		try{
			Reader reader=Resources.getResourceAsReader("tinydb.xml");
			ConfigurationBuilder builder = new ConfigurationBuilder(reader);
			Configuration configuration=builder.parser();
			conn = configuration.getUseDataSource().getConnection();
			ScriptRunner runner = new ScriptRunner(conn, false, false);
			// 设置字符集
			Resources.setCharset(Charset.forName("utf-8"));
			// 加载sql脚本并执行
			runner.runScript(Resources
						.getResourceAsReader("table_derby.sql"));
		}catch (Exception e) {
			fail(e.getMessage());
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
	
	//第一次初始化，表在执行table_derby.sql之前不存在，此时builder加载数据库信息显然是不完整的，需要等执行sql再次加载。
	@SuppressWarnings("unchecked")
	private void initFactory(){
		Connection conn = null;
		try{
			Reader reader=Resources.getResourceAsReader("tinydb.xml");
			ConfigurationBuilder builder = new ConfigurationBuilder(reader);
			Configuration configuration=builder.parser();
			factory=new DbOperatorFactoryBuilder().build(configuration);
			operator=factory.getDBOperator();
		}catch (Exception e) {
			fail(e.getMessage());
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
	
	protected Bean getBean(String id){
		return getBean(id,"testSql");
	}
	
	private Bean getBean(String id,String name){
		Bean bean = new Bean(ANIMAL);
		bean.setProperty("id",id);
		bean.setProperty("name",name);
		bean.setProperty("length","1234");
		return bean;
	}
	
	protected Bean[] getBeans(int length){
		Bean[] insertBeans = new Bean[length];
		for(int i = 0 ; i < length ; i++ ){
			insertBeans[i] = getBean(i+"");
		}
		return insertBeans;
	}
}
