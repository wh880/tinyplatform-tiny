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
package org.tinygroup.entity.wijmo.menu;

import java.util.List;

public class WijmenuNode {
	String text;// 显示的文本
	String url;// url
	List<WijmenuNode> nodes;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<WijmenuNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<WijmenuNode> nodes) {
		this.nodes = nodes;
	}

	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		toString(this, sb);
		return sb.toString();
	}

	private void toString(WijmenuNode node, StringBuffer sb) {
		sb.append("<li><a href=\"");
		if (node.nodes == null || node.nodes.size() == 0) {
			sb.append("#");
		} else {
			sb.append(node.url);
		}
		sb.append("\">");
		sb.append(node.text);
		sb.append("</a>");
		if (node.nodes != null && node.nodes.size() > 0) {
			sb.append("<ul>");
			for (WijmenuNode subNode : node.nodes) {
				toString(subNode, sb);
			}
			sb.append("</ul>");
		}
		sb.append("</li>");
	}
}
