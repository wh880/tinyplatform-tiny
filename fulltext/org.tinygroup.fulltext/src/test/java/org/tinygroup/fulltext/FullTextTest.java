package org.tinygroup.fulltext;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.Resources;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.tinyrunner.Runner;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

public class FullTextTest extends TestCase {

	private FullText fullText;
	
	private final static String driver="org.apache.derby.jdbc.EmbeddedDriver";
	private final static String url="jdbc:derby:TESTDB;create=true";
	private final static String user="opensource";
	private final static String password="opensource";

	protected void setUp() throws Exception {
		super.setUp();
		Runner.init("application.xml", null);

		BeanContainer<?> container = BeanContainerFactory.getBeanContainer(this
				.getClass().getClassLoader());
		fullText = container.getBean("memoryFullText");

		Class.forName(driver);

		initDerby();
	}
	
	private Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, user, password);
	}

	private void initDerby() throws Exception {
		Connection conn = getConnection();
		Statement stmt = null;
		try {
			ScriptRunner runner = new ScriptRunner(conn, false, false);
			// 设置字符集
			Resources.setCharset(Charset.forName("utf-8"));
			// 加载sql脚本并执行
			runner.runScript(Resources.getResourceAsReader("table_derby.sql"));
			
			//执行清理
			stmt = conn.createStatement();
			stmt.execute("delete from ARTICLE");
			
			//执行插入
			stmt.execute("insert into ARTICLE (id,title,content,author,create_date) values('10001','山海关','一二三四五六七八九','白玉','20061225')");
			stmt.execute("insert into ARTICLE (id,title,content,author,create_date) values('10002','随机数据','abcdefdfdfdfdfdf','黑箱','20160522')");
			stmt.execute("insert into ARTICLE (id,title,content,author,create_date) values('10003','测试数据','我爱我家','Mr.R','20061125')");
			
			conn.commit();
		} finally {
			if(stmt!=null){
			   stmt.close();	
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void testBase() throws Exception {
		assertNotNull(fullText);
		FileObject f = VFS.resolveFile("src/test/resources/file/");
		assertNotNull(f);

		// 创建索引
		fullText.createIndex("text", f);

		// 执行查询
		Pager<Document> page = fullText.search("黄", 0, 2);
		assertNotNull(page);
		assertEquals(2, page.getTotalCount());
		assertEquals(2, page.getRecords().size());

		// 删除索引
		fullText.deleteIndex("text", f);

		// 执行查询
		page = fullText.search("黄", 0, 2);
		assertNotNull(page);
		assertEquals(0, page.getTotalCount());
		assertEquals(0, page.getRecords().size());
	}

	public void testFile() throws Exception {
		assertNotNull(fullText);

		FileObject f = VFS.resolveFile("src/test/resources/file/");
		assertNotNull(f);

		// 创建索引
		fullText.createIndex("file", f);

		Pager<Document> page = fullText.search("中华");
		assertEquals(1, page.getTotalCount());

		// 测试一般的txt文件
		Document doc = page.getRecords().get(0);
		assertEquals("file", doc.getType().getValue());
		assertEquals("789.txt", doc.getTitle().getValue());
		assertEquals("中华人民共和国", doc.getAbstract().getValue());

		// 测试ini文件
		page = fullText.search("人间和平");
		assertEquals(1, page.getTotalCount());

		doc = page.getRecords().get(0);
		assertEquals("政治", doc.getType().getValue());
		assertEquals("人间和平，天下太平", doc.getTitle().getValue());
		assertEquals("辛苦遭逢起一经", doc.getAbstract().getValue());

		// 删除索引
		fullText.deleteIndex("file", f);
	}

	public void testDB() throws Exception {
		assertNotNull(fullText);

		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from ARTICLE");
			
			// 创建索引
			fullText.createIndex("db", rs);
			
			Pager<Document> page = fullText.search("数据");
			assertEquals(2, page.getTotalCount());
			
			Document doc = page.getRecords().get(0);
			assertEquals("10002", doc.getId().getValue());
			assertEquals("db", doc.getType().getValue());
			assertEquals("随机数据", doc.getTitle().getValue());
			assertEquals("abcdefdfdfdfdfdf黑箱", doc.getAbstract().getValue());  //sql_abstract.page定义摘要内容是content和author的拼接
		} finally {
			if(rs!=null){
			   rs.close();	
			}
			if(stmt!=null){
			   stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
}
