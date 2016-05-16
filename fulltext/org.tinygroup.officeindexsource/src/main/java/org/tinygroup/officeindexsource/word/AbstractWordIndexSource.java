package org.tinygroup.officeindexsource.word;

import java.io.IOException;
import java.io.InputStream;

import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.document.DefaultDocument;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.fulltext.field.StringField;
import org.tinygroup.vfs.FileObject;

public abstract class AbstractWordIndexSource {

	protected Document getDocument(String type, FileObject data) {
		DefaultDocument document = new DefaultDocument();

		// 逻辑处理
		document.addField(new StringField(FullTextHelper.getStoreId(), data
				.getAbsolutePath())); // 路径做主键
		document.addField(new StringField(FullTextHelper.getStoreType(), type));
		document.addField(new StringField(FullTextHelper.getStoreTitle(), data
				.getFileName(), true, true, true));
		try {
			document.addField(new StringField(
					FullTextHelper.getStoreAbstract(), readWordText(data.getInputStream()), true,
					true, true));
		} catch (Exception e) {
			throw new FullTextException(String.format("处理文件[%s]发生异常",
					data.getAbsolutePath()), e);
		}

		return document;
	}
	
	protected abstract String readWordText(InputStream input) throws IOException;
}
