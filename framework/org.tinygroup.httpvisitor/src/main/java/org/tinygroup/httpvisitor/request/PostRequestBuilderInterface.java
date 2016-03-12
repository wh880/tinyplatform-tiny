package org.tinygroup.httpvisitor.request;

import org.tinygroup.httpvisitor.BodyElement;

/**
 * POST独有的HTTP链式构造器接口
 * @author yancheng11334
 *
 * @param <T>
 */
public interface PostRequestBuilderInterface<T>  extends BodyRequestBuilderInterface<T>{
	
	/**
	 * 多段提交
	 * @param elements
	 * @return
	 */
	T multipart(BodyElement... elements);
	
}
