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
package org.tinygroup.convert.objectxml.xstream;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.convert.Converter;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

import com.thoughtworks.xstream.XStream;

public class XmlToObject<T> implements Converter<String, List<T>> {
	XStream xstream;
	private String rootNodeName;

	public XmlToObject(Class<T> rootClass, String rootNodeName) {
		xstream = new XStream();
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(rootClass);
		this.rootNodeName = rootNodeName;
	}

	@SuppressWarnings("unchecked")
	public List<T> convert(String inputData) {
		XmlNode root = new XmlStringParser().parse(inputData).getRoot();
		checkRootNodeName(root);
		List<T> list = new ArrayList<T>();
		List<XmlNode> nodeList = root.getSubNodes();
		for (XmlNode node : nodeList) {
			list.add((T) xstream.fromXML(node.toString()));
		}
		return list;
	}

	private void checkRootNodeName(XmlNode root) {
		if (root.getNodeName() == null
				|| !root.getNodeName().equals(rootNodeName)) {
			throw new RuntimeException("根节点名称[" + root.getRoot().getNodeName()
					+ "]与期望的根节点名称[" + rootNodeName + "]不一致！");
		}
	}
}
