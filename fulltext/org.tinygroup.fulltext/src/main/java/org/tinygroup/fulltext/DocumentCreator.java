package org.tinygroup.fulltext;

import org.tinygroup.fulltext.document.Document;


/**
 * 文档生成器，支持转换单节点数据,如txt文件、数据库的单条Result
 * @author yancheng11334
 */
public interface DocumentCreator<T> {

	/**
	 * 判断本文档生成器能否处理此数据
	 * @param data
	 * @return
	 */
	boolean  isMatch(T data);
	
	/**
	 * 将数据转换成索引文档
	 * @param type
	 * @param data
	 * @param arguments
	 * @return
	 */
	Document getDocument(String type, T data, Object... arguments);
}
