package org.tinygroup.officeindexsource.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.tinygroup.fulltext.DocumentListCreator;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.vfs.FileObject;

/**
 * 对应office2007的excel
 * @author yancheng11334
 *
 */
public class XlsxDocumentListCreator extends AbstractExcelIndexSource implements DocumentListCreator<FileObject> {

	public boolean isMatch(FileObject data) {
		return !data.isFolder() && data.getExtName().equals("xlsx");
	}

	public List<Document> getDocument(String type, FileObject data,
			Object... arguments) {
		return this.getDocument(type, data.getAbsolutePath(),
				data.getInputStream());
	}

	protected Workbook createWorkbook(InputStream input) throws IOException {
		return new XSSFWorkbook(input);
	}

}
