package org.tinygroup.fulltext.impl;

import java.util.List;

import org.tinygroup.fulltext.DocumentCreator;

@SuppressWarnings("rawtypes")
public class AbstractCreator {
	
	private List<DocumentCreator> documentCreators;

	public List<DocumentCreator> getDocumentCreators() {
		return documentCreators;
	}

	public void setDocumentCreators(List<DocumentCreator> documentCreators) {
		this.documentCreators = documentCreators;
	}

}
