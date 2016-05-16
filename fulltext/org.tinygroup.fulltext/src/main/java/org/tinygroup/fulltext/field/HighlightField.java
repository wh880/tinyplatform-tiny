package org.tinygroup.fulltext.field;

/**
 * 渲染字段
 * @author yancheng11334
 *
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public interface HighlightField<T> extends Field{

	/**
	 * 得到原始值
	 * @return
	 */
	public T getSourceValue();
	
	/**
	 * 得到渲染后的值
	 * @param arguments
	 * @return
	 */
	public T getRenderValue(Object... arguments);
}
