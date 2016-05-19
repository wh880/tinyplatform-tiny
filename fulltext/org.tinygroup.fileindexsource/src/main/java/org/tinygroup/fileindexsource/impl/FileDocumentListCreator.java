package org.tinygroup.fileindexsource.impl;

import java.io.File;
import java.util.List;

import org.tinygroup.fileindexsource.AbstractFileObjectCreator;
import org.tinygroup.fulltext.DocumentListCreator;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

/**
 * 抽象的文件目录处理器
 * @author yancheng11334
 *
 */
public class FileDocumentListCreator extends AbstractFileObjectCreator implements
		DocumentListCreator<File> {

	public boolean isMatch(File data) {
		return data instanceof File;
	}

	public List<Document> getDocument(final String type, final File file,
			final Object... arguments) {
		final FileObject data = VFS.resolveFile(file.getAbsolutePath());
		return super.getDocument(type, data, arguments);
	}

}
