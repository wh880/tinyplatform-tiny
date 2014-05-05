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
package org.tinygroup.convert.textxml.fixwidth;

import org.tinygroup.convert.Converter;
import org.tinygroup.convert.XmlUtils;
import org.tinygroup.convert.text.TextBaseParse;
import org.tinygroup.convert.text.config.Text;
import org.tinygroup.convert.text.config.TextCell;
import org.tinygroup.convert.text.config.TextRow;

import java.util.List;
import java.util.Map;

public class TextToXml extends TextBaseParse implements Converter<String, String> {
	private String rootNodeName;
	private String rowNodeName;
	private String lineSplit;

	/**
	 * 文本转换为Xml
	 * 
	 * @param rootNodeName
	 *            根节点名称
	 * @param rowNodeName
	 *            行节点名称
	 * @param lineSplit
	 *            行分隔附
	 */
	public TextToXml(Map<String, String> titleMap, String rootNodeName,
			String rowNodeName, String lineSplit) {
		this.rootNodeName = rootNodeName;
		this.rowNodeName = rowNodeName;
		this.lineSplit = lineSplit;
		this.titleMap = titleMap;
	}

	public String convert(String inputData) {
//		String[] lines = inputData.split(lineSplit);
//		List<Integer> fieldPosList = getFieldEndPos(lines[0]);
//		List<String> fieldNames = getField(lines[0], fieldPosList);
//		StringBuffer sb = new StringBuffer();
//		XmlUtils.appendHeader(sb, rootNodeName);
//		for (int i = 1; i < lines.length; i++) {
//			if (lines[i].length() != lines[0].length()) {
//				throw new RuntimeException("标题行长度(" + lines[0].length()
//						+ ")与第【" + i + "】行的数据长度(" + lines[i].length() + ")不相等");
//			}
//			XmlUtils.appendHeader(sb, rowNodeName);
//			List<String> values = getField(lines[i], fieldPosList);
//			for (int j = 0; j < fieldNames.size(); j++) {
//				XmlUtils.appendHeader(sb, titleMap.get(fieldNames.get(j)));
//				sb.append(values.get(j));
//				XmlUtils.appendFooter(sb, titleMap.get(fieldNames.get(j)));
//			}
//			XmlUtils.appendFooter(sb, rowNodeName);
//		}
//		XmlUtils.appendFooter(sb, rootNodeName);
		Text text = computeFixWidthText(inputData, lineSplit);
		StringBuffer sb = new StringBuffer();
		XmlUtils.appendHeader(sb, rootNodeName);
		List<TextRow> rows = text.getRows();
		List<String> nodeTags = getFisrtRowRealNames(text);
		for (int i = 1; i < rows.size(); i++) {
			TextRow row = rows.get(i);
			List<TextCell> cells = row.getCells();
			XmlUtils.appendHeader(sb, rowNodeName);
			for(int j = 0;j< nodeTags.size() ; j ++ ){
				XmlUtils.appendHeader(sb, nodeTags.get(j));
				sb.append(cells.get(j).getValue());
				XmlUtils.appendFooter(sb, nodeTags.get(j));
			}
			XmlUtils.appendFooter(sb, rowNodeName);
		}
		XmlUtils.appendFooter(sb, rootNodeName);
		
		
		return sb.toString();
	}

//	private List<String> getField(String string, List<Integer> fieldPosList) {
//		List<String> fieldList = new ArrayList<String>();
//		int start = 0;
//		for (int end : fieldPosList) {
//			fieldList.add(string.substring(start, end).trim());
//			start = end;
//		}
//
//		return fieldList;
//	}
//
//	private List<Integer> getFieldEndPos(String string) {
//		List<Integer> posList = new ArrayList<Integer>();
//		int start = 0;
//		do {
//			start = getFieldEnd(string, start);
//			posList.add(start);
//		} while (start < string.length());
//
//		return posList;
//	}
//
//	private int getFieldEnd(String string, int start) {
//		int pos = start;
//		while (pos < string.length() && string.charAt(pos) != ' ') {
//			pos++;
//		}
//		while (pos < string.length() && string.charAt(pos) == ' ') {
//			pos++;
//		}
//		return pos;
//	}

}
