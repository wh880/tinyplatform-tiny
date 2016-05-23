package org.tinygroup.fileindexsource;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.fileindexsource.impl.DefaultFileObjectFilter;
import org.tinygroup.fulltext.DocumentCreator;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.impl.AbstractCreator;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectFilter;
import org.tinygroup.vfs.FileObjectProcessor;

public abstract class AbstractFileObjectCreator extends AbstractCreator {

	@SuppressWarnings({"rawtypes","unchecked"})
	public List<Document> getDocument(final String type, final FileObject data,
			final Object... arguments) {

		FileObjectFilter filter = new DefaultFileObjectFilter();

		if (arguments != null && arguments.length >= 1) {
			// 设置用户的规则
			filter = (FileObjectFilter) arguments[0];
		}
		final List<Document> docs = new ArrayList<Document>();
		data.foreach(filter, new FileObjectProcessor() {

			public void process(FileObject fileObject) {
				
				for (DocumentCreator creator :getDocumentCreators() ) {
					if (creator.isMatch(fileObject)) {
						Document doc = creator.getDocument(type, fileObject, arguments);
						docs.add(doc);
					}
				}
			}
		});
		return docs;
	}
}
