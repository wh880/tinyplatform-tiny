package org.tinygroup.fulltext.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.fulltext.DocumentListCreator;
import org.tinygroup.fulltext.FullText;
import org.tinygroup.fulltext.IndexOperator;
import org.tinygroup.fulltext.Pager;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.fulltext.field.Field;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractFullText implements FullText {

	/**
	 * 批量文档处理器列表
	 */
	private List<DocumentListCreator> documentListCreators;
	
	/**
	 * 具体的索引管理器
	 */
	private IndexOperator indexOperator;

	public List<DocumentListCreator> getDocumentListCreators() {
		return documentListCreators;
	}

	public void setDocumentListCreators(
			List<DocumentListCreator> documentListCreators) {
		this.documentListCreators = documentListCreators;
	}

	public IndexOperator getIndexOperator() {
		return indexOperator;
	}

	public void setIndexOperator(IndexOperator indexOperator) {
		this.indexOperator = indexOperator;
	}

	/**
	 * 检查类型
	 * 
	 * @param type
	 */
	protected void checkType(String type) {
		if (StringUtil.isEmpty(type)) {
			throw new FullTextException("类型参数不能为空!");
		}
	}

	/**
	 * 检查索引数据
	 * 
	 * @param data
	 */
	protected <T> void checkData(T data) {
		if (data == null) {
			throw new FullTextException("索引数据不能为空!");
		}
	}
	
	protected <T> boolean matchData(DocumentListCreator creator,T data){
		try{
			return creator.isMatch(data);	
		}catch(ClassCastException e){
			//忽略类型不配
			return false;
		}
	}

	public <T> void createIndex(String type, T data, Object... arguments) {
		checkType(type);
		checkData(data);
		for (DocumentListCreator creator : this.documentListCreators) {
			if (matchData(creator,data)) {
				List<Document> docs = creator.getDocument(type, data, arguments);
				this.indexOperator.createIndex(docs);
				return;
			}
		}
		throw new FullTextException("没有找到合适的索引处理器创建索引!");
	}

	public <T> void deleteIndex(String type, T data, Object... arguments) {
		checkType(type);
		checkData(data);
		
		for (DocumentListCreator creator : this.documentListCreators) {
			if (matchData(creator,data)) {
				List<Document> docs = creator.getDocument(type, data, arguments);
				List<Field> docIds = new ArrayList<Field>();
				for(Document doc:docs){
					docIds.add(doc.getId());
				}
				this.indexOperator.deleteIndex(docIds);
				return;
			}
		}
		throw new FullTextException("没有找到合适的索引处理器删除索引!");
	}
	
	public Pager<Document> search(String searchCondition) {
		return search(searchCondition, 0 , 10);
	}
	
	public Pager<Document> search(String searchCondition,int start,int limit) {
		return indexOperator.search(searchCondition, start, limit);
	}

}
