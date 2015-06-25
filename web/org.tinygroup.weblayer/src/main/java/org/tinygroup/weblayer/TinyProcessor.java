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
package org.tinygroup.weblayer;

import org.tinygroup.commons.order.Ordered;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * WebContext处理器，用于根据WebContext进行相关处理
 *
 * @author luoguo
 */
public interface TinyProcessor extends Ordered{

    String TINY_PROCESSOR = "tiny-processor";

    /**
     * 判断url是否匹配，如果匹配，返回True
     *
     * @param urlString
     * @return
     */
    boolean isMatch(String urlString);

    /**
     * 处理
     *
     * @param context
     */
    void process(String urlString, WebContext context)throws ServletException, IOException;

    /**
     * 初始化操作
     * @param tinyProcessorConfig
     * @throws ServletException
     */
    void init(TinyProcessorConfig tinyProcessorConfig)throws ServletException;

    /**
     * tinyprocessor的销毁操作
     */
    void destroy();

    void setProcessorName(String processorName);
    
    String getProcessorName();
}
