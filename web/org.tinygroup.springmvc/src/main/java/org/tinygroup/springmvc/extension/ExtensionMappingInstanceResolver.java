package org.tinygroup.springmvc.extension;

import javax.servlet.http.HttpServletRequest;

/**
 * ExtensionMappingInstance对象的解析器
 */
public interface ExtensionMappingInstanceResolver extends ContentNegotiationResolver{
    /**
     * 通过request获取url的扩展名对应的MVC组件抽象
     * @param request
     * @return
     */
	ExtensionMappingInstance get(HttpServletRequest request);

}
