package org.tinygroup.fulltext;

import org.tinygroup.fulltext.document.Document;

/**
 * 全文检索的接口
 * @author yancheng11334
 *
 */
public interface FullText {
	
	/**
	 * application.xml的全局配置参数名:<br>
	 * 参数值:全文检索接口的bean名称
	 */
	public static final String FULLTEXT_BEAN_NAME = "FULLTEXT_BEAN_NAME";
	
	/**
	 * 创建索引
	 * @param type  索引项:相当于分类，便于查询
	 * @param data
	 * @param arguments
	 */
	public <T> void createIndex(String type,T data,Object... arguments);
	
	/**
	 * 删除索引
	 * @param type 索引项:相当于分类，便于查询
	 * @param data
	 * @param arguments
	 */
	public <T> void deleteIndex(String type,T data,Object... arguments);
	

    /**
     * 查询索引
     * @param searchCondition
     * @return
     */
	public Pager<Document> search(String searchCondition);
	
	/**
	 * 查询带分页的索引
	 * @param searchCondition
	 * @param start
	 * @param limit
	 * @return
	 */
	public Pager<Document> search(String searchCondition,int start,int limit);
}
