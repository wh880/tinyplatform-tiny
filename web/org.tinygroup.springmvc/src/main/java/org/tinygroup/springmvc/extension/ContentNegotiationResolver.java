/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
