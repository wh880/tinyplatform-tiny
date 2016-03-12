package org.tinygroup.httpvisitor.request;

import java.io.File;
import java.io.InputStream;

import org.tinygroup.httpvisitor.BodyElement;


/**
 * 包含Body的HTTP链式构造器接口
 * @author yancheng11334
 *
 * @param <T>
 */
public interface BodyRequestBuilderInterface<T> extends HttpRequestBuilderInterface<T>{

	 /**
     * Set http body data for Post/Put request
     *
     * @param data the data to post
     */
    T data(byte[] data);

    /**
     * Set http data from inputStream for Post/Put request
     */
    T data(InputStream in);

    /**
     * Set http data with text.
     * The text string will be encoded, default using utf-8, set charset with charset(Charset charset) method
     */
    T data(String body);
    
    /**
     * Set http data from file for Post/Put request
     */
    T data(File file);
    
    /**
     * Set http data from warpper object for Post/Put request
     */
    T data(BodyElement element);
}
