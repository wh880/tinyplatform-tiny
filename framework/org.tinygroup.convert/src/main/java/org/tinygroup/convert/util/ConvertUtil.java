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
package org.tinygroup.convert.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.tinygroup.convert.text.config.Text;
import org.tinygroup.convert.text.config.TextCell;
import org.tinygroup.convert.text.config.TextRow;

public class ConvertUtil {
	public static String getAttributeValue(Object object, String property) {
		try {
			Object value = BeanUtils.getProperty(object, property);
			if (value == null) {
				return "";
			}
			return value.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setAttributeValue(Object object, String property,
			Object value) {
		try {
			BeanUtils.setProperty(object, property, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> textToObjectList(Class<T> clazz, Text text) {
		List<T> objects = new ArrayList<T>();
		List<TextRow> rows = text.getRows();
		if (rows.size() < 2)
			return null;
		TextRow titleRow = rows.get(0);
		for (int i = 1; i < rows.size(); i++) {
			TextRow dataRow = rows.get(i);
			objects.add(textToObject(clazz, titleRow, dataRow));
		}
		return objects;
	}

	public static <T> T textToObject(Class<T> clazz, TextRow titleRow,
			TextRow dataRow) {
		try {
			T object = clazz.newInstance();
			List<TextCell> titleCells = titleRow.getCells();
			List<TextCell> dataCells = dataRow.getCells();
			for (int i = 0; i < titleCells.size(); i++) {
				setAttributeValue(object, titleCells.get(i).getValue(),
						dataCells.get(i).getValue());
			}
			return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static int getBytesLength(String s) {
		try {
			return s.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> getStringArrayByBytesLength(String string,
			List<Integer> filedBytesPosList) {
		List<String> fieldList = new ArrayList<String>();
		try {
			byte[] byteArray = string.getBytes("GBK");
			int start = 0;
			for (int length : filedBytesPosList) {
				int end = start+length;
				byte[] subArray = getSubArray(byteArray, start, end);
				String data = new String(subArray,"GBK"); 
				fieldList.add(data.trim());
				start = end;
			}
			return fieldList;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static byte[] getSubArray(byte[] byteArray,int start,int end){
		byte[] subArray = new byte[end-start];
		for(int i = start,j=0;i<end;i++,j++){
			subArray[j] = byteArray[i];
		}
		return subArray;
	}
}
