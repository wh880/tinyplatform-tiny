package org.tinygroup.fulltext;

import java.util.List;

import org.tinygroup.fulltext.document.Document;


/**
 * 批量文档生成器，一般包含多个子文档生成器
 * @author yancheng11334
 *
 * @param <T>
 */
public interface DocumentListCreator<T>{

	 /**
	  * 判断本批量文档生成器能否处理此数据
	  * @param data
	  * @return
	  */
     boolean isMatch(T data);
	 
     /**
      * 将数据转换成索引文档列表
      * @param type
      * @param data
      * @param arguments
      * @return
      */
	 List<Document> getDocument(String type, T data, Object... arguments);
}
