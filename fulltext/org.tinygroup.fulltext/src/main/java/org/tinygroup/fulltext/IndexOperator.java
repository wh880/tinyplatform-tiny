package org.tinygroup.fulltext;

import java.util.List;

import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.field.Field;

/**
 * 底层操作接口(屏蔽上层的数据来源)
 * @author yancheng11334
 *
 */
public interface IndexOperator {

	/**
	 * 创建索引接口
	 * @param docs
	 */
	void createIndex(List<Document> docs);
	
	/**
	 * 删除索引接口
	 * @param docIds
	 */
	@SuppressWarnings("rawtypes")
	void deleteIndex(List<Field> docIds);
	
	/**
	 * 查询带分页的索引
	 * @param searchCondition
	 * @param start
	 * @param limit
	 * @return
	 */
	public Pager<Document> search(String searchCondition,int start,int limit);
}
