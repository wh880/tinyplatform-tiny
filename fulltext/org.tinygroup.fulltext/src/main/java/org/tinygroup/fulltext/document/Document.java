package org.tinygroup.fulltext.document;

import java.util.List;

import org.tinygroup.fulltext.field.Field;



/**
 * 文档接口
 * @author yancheng11334
 *
 */
@SuppressWarnings("rawtypes")
public interface Document extends Iterable<Field>{
	
	/**
	 * 获得主键字段
	 * @return
	 */
	public Field  getId();
	
	/**
	 * 获得分类字段
	 * @return
	 */
	public Field  getType();
	
	/**
	 * 获得标题字段
	 * @return
	 */
	public Field  getTitle();
	
	/**
	 * 获得摘要字段
	 * @return
	 */
	public Field  getAbstract();
	
	/**
	 * 获得匹配的字段
	 * @param name
	 * @return
	 */
	public Field  getField(String name);
	
	/**
	 * 获得一组匹配的字段组
	 * @param name
	 * @return
	 */
	public List<Field>  getFields(String name);
}
