package org.tinygroup.officeindexsource;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.officeindexsource.word.DocDocumentCreator;
import org.tinygroup.officeindexsource.word.DocxDocumentCreator;
import org.tinygroup.tinyrunner.Runner;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

public class WordTest extends TestCase{

	private BeanContainer<?> BeanContainer = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		Runner.init("application.xml",null);
		BeanContainer = BeanContainerFactory.getBeanContainer(this.getClass().getClassLoader());
	}
	
	public void testDoc(){
		DocDocumentCreator creator = BeanContainer.getBean("docDocumentCreator");
		FileObject fileObejct = VFS.resolveFile("src/test/resources/word/data.doc");
		Document doc = creator.getDocument("传记", fileObejct) ;
		assertEquals("传记", doc.getType().getValue());
		assertEquals("Word文档输出结果，我爱北京", doc.getAbstract().getValue());
	}
	
	public void testDocx(){
		DocxDocumentCreator creator = BeanContainer.getBean("docxDocumentCreator");
		FileObject fileObejct = VFS.resolveFile("src/test/resources/word/data.docx");
		Document doc = creator.getDocument("传记2", fileObejct) ;
		assertEquals("传记2", doc.getType().getValue());
		assertEquals("Word文档输出结果，我爱北京", doc.getAbstract().getValue());
	}
}
