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
package org.tinygroup.convert.textxml.simple;

import java.util.List;
import java.util.Map;

import org.tinygroup.convert.Converter;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

public class XmlToText implements Converter<String, String> {
	private String rootNodeName;
	private String rowNodeName;
	private String lineSplit;
	private String fieldSplit;
	private Map<String, String> titleMap;
	private List<String> fieldList;

	/**
	 * 文本转换为Xml
	 * 
	 * @param rootNodeName
	 *            根节点名称
	 * @param rowNodeName
	 *            行节点名称
	 * @param lineSplit
	 *            行分隔附
	 * @param fieldSplit
	 *            字段分隔符
	 */
	public XmlToText(Map<String, String> titleMap, List<String> fieldList,
			String rootNodeName, String rowNodeName, String lineSplit,
			String fieldSplit) {
		this.rootNodeName = rootNodeName;
		this.rowNodeName = rowNodeName;
		this.lineSplit = lineSplit;
		this.fieldSplit = fieldSplit;
		this.titleMap = titleMap;
		this.fieldList = fieldList;
	}

	/**
 * 
 */
	public String convert(String inputData) {
		XmlNode root = new XmlStringParser().parse(inputData).getRoot();
		checkRootNodeName(root);
		List<XmlNode> rowList = root.getSubNodes(rowNodeName);
		StringBuffer sb = new StringBuffer();
		if (rowList.size() > 0) {
			for (int i = 0; i < fieldList.size(); i++) {
				if (i > 0) {
					sb.append(fieldSplit);
				}
				sb.append(titleMap.get(fieldList.get(i)));
			}
			sb.append(lineSplit);
			for (int row = 0; row < rowList.size(); row++) {// 对所有的行进行处理
				XmlNode rowNode = rowList.get(row);
				for (int i = 0; i < fieldList.size(); i++) {
					XmlNode fieldNode = rowNode.getSubNode(fieldList.get(i));
					if (i > 0) {
						sb.append(fieldSplit);
					}
					if (fieldNode != null) {
						sb.append(fieldNode.getContent());
					}
				}
				if (row < rowList.size() - 1) {
					sb.append(lineSplit);
				}
			}

		}
		return sb.toString();
	}

	private void checkRootNodeName(XmlNode root) {
		if (root.getNodeName() == null
				|| !root.getNodeName().equals(rootNodeName)) {
			throw new RuntimeException("根节点名称[" + root.getRoot().getNodeName()
					+ "]与期望的根节点名称[" + rootNodeName + "]不一致！");
		}
	}
}
