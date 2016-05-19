package org.tinygroup.fileindexsource.impl;

import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.fulltext.DocumentCreator;
import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.document.DefaultDocument;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.fulltext.field.StringField;
import org.tinygroup.vfs.FileObject;

/**
 * txt文本内容
 * @author yancheng11334
 *
 */
public class TextDocumentCreator implements DocumentCreator<FileObject> {
	
	private String encode = "utf-8";
	
	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public boolean isMatch(FileObject data) {
		return !data.isFolder() && data.getExtName().equals("txt");
	}

	public Document getDocument(String type, FileObject data, Object... arguments) {
		DefaultDocument document = new DefaultDocument();
		
		//逻辑处理
		document.addField(new StringField(FullTextHelper.getStoreId(),data.getAbsolutePath())); //路径做主键
		document.addField(new StringField(FullTextHelper.getStoreType(),type));
		document.addField(new StringField(FullTextHelper.getStoreTitle(),data.getFileName(),true,true,true));
		try {
			document.addField(new StringField(FullTextHelper.getStoreAbstract(),FileUtil.readStreamContent(data.getInputStream(), encode),true,true,true));
		} catch (Exception e) {
			throw new FullTextException(String.format("处理文件[%s]发生异常", data.getAbsolutePath()),e);
		}
		
		return document;
	}

}
