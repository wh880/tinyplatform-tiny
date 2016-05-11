package org.tinygroup.chinese.fileProcessor;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.vfs.FileObject;

public class ChineseWordFileProcessor extends AbstractFileProcessor {
	protected static final String EXT_FILE_NAME = ".chinese.word.xml";

	public void process() {
		for (FileObject file : fileObjects) {
			ChineseContainer.loadWord(file, "utf-8");
		}
	}

	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(EXT_FILE_NAME);
	}

}
