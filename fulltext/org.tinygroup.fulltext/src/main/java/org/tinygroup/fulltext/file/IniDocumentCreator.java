package org.tinygroup.fulltext.file;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.fulltext.DocumentCreator;
import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.TemplateDocumentCreator;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.vfs.FileObject;

/**
 * 处理ini文件
 * 
 * @author yancheng11334
 * 
 */
public class IniDocumentCreator implements DocumentCreator<FileObject> {

	private String encode = "utf-8";

	private TemplateDocumentCreator templateDocumentCreator;

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public TemplateDocumentCreator getTemplateDocumentCreator() {
		return templateDocumentCreator;
	}

	public void setTemplateDocumentCreator(
			TemplateDocumentCreator templateDocumentCreator) {
		this.templateDocumentCreator = templateDocumentCreator;
	}

	public boolean isMatch(FileObject data) {
		return !data.isFolder() && data.getExtName().equals("ini");
	}

	public Document getDocument(String type, FileObject data,
			Object... arguments) {
		java.util.Properties p = new java.util.Properties();
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(data.getInputStream(), encode);
			p.load(reader);

			// 转换上下文
			Context context = new ContextImpl();
			context.put(FullTextHelper.getStoreType(), type);
			context.put(FullTextHelper.getStoreId(), data.getAbsolutePath());

			for (Entry<Object, Object> entry : p.entrySet()) {
				context.put((String) entry.getKey(), entry.getValue());
			}

			return templateDocumentCreator.execute(context);

		} catch (FullTextException e) {
			throw e;
		} catch (Exception e) {
			throw new FullTextException(String.format("处理文件[%s]发生异常",
					data.getAbsolutePath()), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new FullTextException(String.format("释放文件资源[%s]发生异常",
							data.getAbsolutePath()), e);
				}
			}
		}
	}
}
