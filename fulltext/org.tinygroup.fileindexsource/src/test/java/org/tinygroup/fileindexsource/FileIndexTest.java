package org.tinygroup.fileindexsource;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.fulltext.FullText;
import org.tinygroup.fulltext.Pager;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.tinyrunner.Runner;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

public class FileIndexTest extends TestCase{

	private FullText fullText;
	
	protected void setUp() throws Exception {
		super.setUp();
		Runner.init("application.xml", null);

		BeanContainer<?> container = BeanContainerFactory.getBeanContainer(this
				.getClass().getClassLoader());
		fullText = container.getBean("memoryFullText");
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
}
