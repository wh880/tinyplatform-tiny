package org.tinygroup.fulltext.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.tinygroup.fulltext.DocumentListCreator;
import org.tinygroup.fulltext.document.Document;

/**
 * 支持集合类型的对象操作
 * @author yancheng11334
 *
 */
public class CollectionDocumentCreator implements DocumentListCreator<Collection<Document>>{

	public boolean isMatch(Collection<Document> data) {
		return !(data==null || data.isEmpty());
	}

	public List<Document> getDocument(String type, Collection<Document> data,
			Object... arguments) {
		return new ArrayList<Document>(data);
	}


}
