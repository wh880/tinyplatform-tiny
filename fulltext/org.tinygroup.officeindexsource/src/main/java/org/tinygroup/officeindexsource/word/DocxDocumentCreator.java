package org.tinygroup.officeindexsource.word;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.tinygroup.fulltext.DocumentCreator;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.vfs.FileObject;

/**
 * 对应office2007的word
 * @author yancheng11334
 *
 */
public class DocxDocumentCreator extends AbstractWordIndexSource implements DocumentCreator<FileObject>{

	public boolean isMatch(FileObject data) {
		return !data.isFolder() && data.getExtName().equals("docx");
	}

	public Document getDocument(String type, FileObject data,
			Object... arguments) {
		return getDocument(type,data);
	}

	protected String readWordText(InputStream input) throws IOException {
		XWPFDocument doc = new XWPFDocument(input);
		XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
		try{
			return extractor.getText().trim();
		}finally{
			extractor.close();
		}
		
	}

}
