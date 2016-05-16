package org.tinygroup.pdfindexsource;

import java.io.IOException;

import org.tinygroup.fulltext.DocumentCreator;
import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.document.DefaultDocument;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.fulltext.field.StringField;
import org.tinygroup.vfs.FileObject;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * 解析pdf的文档
 * 
 * @author yancheng11334
 * 
 */
public class PdfDocumentCreator implements DocumentCreator<FileObject> {

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isMatch(FileObject data) {
		return !data.isFolder() && data.getExtName().equals("pdf");
	}

	public Document getDocument(String type, FileObject data,
			Object... arguments) {
		DefaultDocument document = new DefaultDocument();

		// 逻辑处理
		document.addField(new StringField(FullTextHelper.getStoreId(), data
				.getAbsolutePath())); // 路径做主键
		document.addField(new StringField(FullTextHelper.getStoreType(), type));
		document.addField(new StringField(FullTextHelper.getStoreTitle(), data
				.getFileName(), true, true, true));
		try {
			document.addField(new StringField(
					FullTextHelper.getStoreAbstract(), readPdfText(data), true,
					true, true));
		} catch (Exception e) {
			throw new FullTextException(String.format("处理文件[%s]发生异常",
					data.getAbsolutePath()), e);
		}

		return document;
	}

	private String readPdfText(FileObject data) throws IOException {
		PdfReader reader = null;
		if (password != null) {
			reader = new PdfReader(data.getInputStream(),
					password.getBytes("UTF-8"));
		} else {
			reader = new PdfReader(data.getInputStream());
		}

		try {
			StringBuffer buff = new StringBuffer();
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				buff.append(PdfTextExtractor.getTextFromPage(reader, i));
			}
			return buff.toString();
		} finally {
			reader.close();
		}
	}

}
