package org.tinygroup.lucene472;

import java.io.File;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.fulltext.FullText;
import org.tinygroup.fulltext.Pager;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.document.HighlightDocument;
import org.tinygroup.tinyrunner.Runner;

public class LuceneTest extends TestCase{

	private FullText fullText;
	
	protected void setUp() throws Exception {
		super.setUp();
		Runner.init("application.xml",null);
		fullText = BeanContainerFactory.getBeanContainer(this.getClass().getClassLoader()).getBean("luceneFullText");
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		//清理索引目录
		FileUtil.delete(new File("index"));
	}
	
	public void testScene1(){
		File sourceDir = new File("src/test/resources/file/");
		String type = "Tiny数据";
		
		//删除索引
		fullText.deleteIndex(type,sourceDir);
		
		//创建索引
		fullText.createIndex(type,sourceDir);
		
		
		Pager<Document> pager = fullText.search("上海 北京");
		String perfix = "<font color='red'>";
		String suffix = "</font>";
		
		//匹配两条记录
		assertEquals(2, pager.getTotalCount());
				
		for(Document doc:pager.getRecords()){
			HighlightDocument newDoc = new HighlightDocument(doc,perfix,suffix);
			System.out.println(newDoc.getId());
			System.out.println(newDoc.getAbstract());
		}
		
		//测试分页
		pager = fullText.search("我们",0,10);
		for(Document doc:pager.getRecords()){
			HighlightDocument newDoc = new HighlightDocument(doc,perfix,suffix);
			System.out.println(newDoc.getId());
		}
		assertEquals(4, pager.getTotalCount());
		assertEquals(4, pager.getRecords().size());
		assertEquals(1, pager.getCurrentPage());
		assertEquals(1, pager.getTotalPages());
		
		pager = fullText.search("我们",0,2);
		assertEquals(4, pager.getTotalCount());
		assertEquals(2, pager.getRecords().size());
		assertEquals(1, pager.getCurrentPage());
		assertEquals(2, pager.getTotalPages());
		
		pager = fullText.search("我们",2,2);
		assertEquals(4, pager.getTotalCount());
		assertEquals(2, pager.getRecords().size());
		assertEquals(2, pager.getCurrentPage());
		assertEquals(2, pager.getTotalPages());
		
		pager = fullText.search("我们",4,2);
		assertEquals(4, pager.getTotalCount());
		assertEquals(0, pager.getRecords().size());
		assertEquals(2, pager.getCurrentPage());
		assertEquals(2, pager.getTotalPages());
	}
}
