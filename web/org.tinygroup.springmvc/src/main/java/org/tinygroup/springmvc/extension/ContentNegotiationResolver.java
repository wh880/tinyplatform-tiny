package org.tinygroup.springmvc.extension;

import org.tinygroup.springmvc.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 根据请求得到希望响应的内容类型
 * @author renhui
 */
public interface ContentNegotiationResolver {
    /**
     * 根据request获取相关的mediatype列表
     * @param request
     * @return
     */
    List<MediaType> getContentTypes(HttpServletRequest request);

}
