/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.tinyspider;

import org.tinygroup.htmlparser.node.HtmlNode;
import org.tinygroup.parser.NodeFilter;

import java.util.List;

/**
 * 监视器，用于对网页内容进行扫描，并对命中的节点进行处理
 * 
 * @author luoguo
 * 
 */
public interface Watcher {
	/**
	 * 设置节点过滤器
	 * 
	 * @param filter
	 */
	void setNodeFilter(NodeFilter<HtmlNode> filter);

	/**
	 * 获取节点过滤器
	 * 
	 * @return
	 */
	NodeFilter<HtmlNode> getNodeFilter();

	/**
	 * 添加处理器
	 * 
	 * @param processor
	 */
	void addProcessor(Processor processor);

	/**
	 * 获取处理器列表
	 * 
	 * @return
	 */
	List<Processor> getProcessorList();
}
