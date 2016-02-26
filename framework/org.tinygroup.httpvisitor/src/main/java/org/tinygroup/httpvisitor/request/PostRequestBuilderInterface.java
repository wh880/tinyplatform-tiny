package org.tinygroup.httpvisitor.request;

import org.tinygroup.vfs.FileObject;

/**
 * POST独有的HTTP链式构造器接口
 * @author yancheng11334
 *
 * @param <T>
 */
public interface PostRequestBuilderInterface<T>  extends BodyRequestBuilderInterface<T>{
	
	T multipart(String name,String content);
	
	
	T multipart(String name,FileObject file);
}
