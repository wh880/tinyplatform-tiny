package org.tinygroup.chinese.fileProcessor;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.vfs.FileObject;

public class ChineseDictFileProcessor extends AbstractFileProcessor {
	private static final String EXT_FILE_NAME = ".pinyin";

	public void process() {
		ChineseContainer.loadDict(fileObjects, "utf-8");
	}

	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(EXT_FILE_NAME);
	}

}
