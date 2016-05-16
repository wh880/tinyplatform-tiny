package org.tinygroup.fulltext.field;

/**
 * 存储字段
 * @author yancheng11334
 *
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public interface StoreField<T> extends Field{

	/**
	 * 该字段是否需要索引
	 * @return
	 */
	public boolean isIndexed();
	
	/**
	 * 该字段是否需要存储
	 * @return
	 */
	public boolean isStored();
	
	/**
	 * 该字段是否需要分词
	 * @return
	 */
	public boolean isTokenized();
}
