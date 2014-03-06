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
package org.tinygroup.weblayer;

import org.tinygroup.commons.order.Ordered;
import org.tinygroup.weblayer.webcontextfactory.WebContextFactory;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * WebContext过滤器，用于根据WebContext进行相关处理
 * 
 * @author luoguo
 * 
 */
public interface TinyFilter extends Ordered, WebContextFactory<WebContext>{
	
	String TINY_FILTER="tiny-filter";
	String TINY_WRAPPER_FILTER = "tiny-wrapper-filter";
	
	int BASIC_FILTER_PRECEDENCE=HIGHEST_PRECEDENCE;//BasicTinyFilter优先级最高
	int PARSER_FILTER_PRECEDENCE=HIGHEST_PRECEDENCE+100;
	int BUFFERED_FILTER_PRECEDENCE=HIGHEST_PRECEDENCE+200;
	int LAZY_COMMIT_FILTER_PRECEDENCE=HIGHEST_PRECEDENCE+300;
	int SESSION_FILTER_PRECEDENCE=HIGHEST_PRECEDENCE+400;
	int SETLOCALE_FILTER_PRECEDENCE=HIGHEST_PRECEDENCE+500;
	int REWRITE_FILTER_PRECEDENCE=HIGHEST_PRECEDENCE+600;
	
	
	/**
	 * 前置操作
	 * 
	 * @param context
	 */
	void preProcess(WebContext context);

	/**
	 * 后置操作
	 * 
	 * @param context
	 */
	void postProcess(WebContext context);
	/**
	 * 初始化tinyFilter
	 */
	void initTinyFilter();
	
	/**
	 * 销毁tinyFilter
	 */
	void destoryTinyFilter();
    /**
     * 是否匹配
     * @param url
     * @return
     */
	boolean isMatch(String url);
	
	public void setConfiguration(XmlNode xmlNode);
}
