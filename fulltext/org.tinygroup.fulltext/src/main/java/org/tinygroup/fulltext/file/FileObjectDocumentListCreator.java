package org.tinygroup.fulltext.file;

import org.tinygroup.fulltext.DocumentListCreator;
import org.tinygroup.vfs.FileObject;

/**
 * 抽象的文件目录处理器
 * @author yancheng11334
 *
 */
public class FileObjectDocumentListCreator extends AbstractFileObjectCreator implements DocumentListCreator<FileObject>{

	public boolean isMatch(FileObject data) {
		return data instanceof FileObject;
	}

}
