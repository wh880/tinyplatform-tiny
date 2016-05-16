package org.tinygroup.officeindexsource;

import java.util.List;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.officeindexsource.excel.XlsDocumentListCreator;
import org.tinygroup.officeindexsource.excel.XlsxDocumentListCreator;
import org.tinygroup.tinyrunner.Runner;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

/**
 * 测试Excel文件
 * @author yancheng11334
 *
 */
public class ExcelTest extends TestCase{

	private BeanContainer<?> BeanContainer = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		Runner.init("application.xml",null);
		BeanContainer = BeanContainerFactory.getBeanContainer(this.getClass().getClassLoader());
	}
	
	public void testXls(){
		XlsDocumentListCreator creator = BeanContainer.getBean("xlsDocumentListCreator");
		FileObject fileObejct = VFS.resolveFile("src/test/resources/excel/data.xls");
		List<Document>  docs = creator.getDocument("社会文学", fileObejct);
		assertEquals(3, docs.size());
		
		Document doc = docs.get(1);
		assertEquals("S002", doc.getId().getValue());
		assertEquals("中国百年展元明清建筑展示", doc.getTitle().getValue());
		assertEquals("社会文学", doc.getType().getValue());
		assertEquals("论南方园林的建筑演变历史", doc.getAbstract().getValue());
	}
	
	public void testXlsx(){
		XlsxDocumentListCreator creator = BeanContainer.getBean("xlsxDocumentListCreator");
		FileObject fileObejct = VFS.resolveFile("src/test/resources/excel/data.xlsx");
		List<Document>  docs = creator.getDocument("历史文学", fileObejct);
		assertEquals(3, docs.size());
		
		Document doc = docs.get(1);
		assertEquals("S002", doc.getId().getValue());
		assertEquals("中国百年展元明清建筑展示", doc.getTitle().getValue());
		assertEquals("历史文学", doc.getType().getValue());
		assertEquals("论南方园林的建筑演变历史", doc.getAbstract().getValue());
	}
}
