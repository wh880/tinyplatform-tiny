package org.tinygroup.officeindexsource.word;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.tinygroup.fulltext.DocumentCreator;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.vfs.FileObject;

/**
 * 对应office2003的word
 * @author yancheng11334
 *
 */
public class DocDocumentCreator extends AbstractWordIndexSource implements DocumentCreator<FileObject>{

	public boolean isMatch(FileObject data) {
		return !data.isFolder() && data.getExtName().equals("doc");
	}

	public Document getDocument(String type, FileObject data,
			Object... arguments) {
		return getDocument(type,data);
	}

	protected String readWordText(InputStream input) throws IOException {
		HWPFDocument doc = new HWPFDocument(input);
		return doc.getDocumentText().trim();
	}

}
