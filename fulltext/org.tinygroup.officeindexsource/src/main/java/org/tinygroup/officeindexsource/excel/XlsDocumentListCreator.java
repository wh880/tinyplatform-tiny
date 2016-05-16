package org.tinygroup.officeindexsource.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.tinygroup.fulltext.DocumentListCreator;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.vfs.FileObject;

/**
 * 对应office2003的excel
 * 
 * @author yancheng11334
 * 
 */
public class XlsDocumentListCreator extends AbstractExcelIndexSource implements
		DocumentListCreator<FileObject> {

	public boolean isMatch(FileObject data) {
		return !data.isFolder() && data.getExtName().equals("xls");
	}

	public List<Document> getDocument(String type, FileObject data,
			Object... arguments) {
		return this.getDocument(type, data.getAbsolutePath(),
				data.getInputStream());
	}

	protected Workbook createWorkbook(InputStream input) throws IOException {
		return new HSSFWorkbook(input);
	}

}
