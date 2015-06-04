package org.tinygroup.springmvc.extension;

import java.util.List;

/**
 * 文件后缀名解析接口
 * @author renhui
 *
 * @param <T>
 */
public interface FileExtensionResolver<T> {
    /**
     * 是否支持
     * @param t
     * @return
     */
	boolean isSupport(T t);
    /**
     * 获取对象的文件后缀列表
     * @param t
     * @return
     */
	List<String> resolveFileExtensions(T t);

}
