package org.tinygroup.fulltext.field;


/**
 * 文档字段
 * @author yancheng11334
 *
 */
public interface Field<T> {

	public String getName();
	
	public FieldType getType();
	
	public T getValue();
	
}
