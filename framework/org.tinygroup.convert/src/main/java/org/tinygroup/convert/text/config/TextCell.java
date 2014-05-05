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
package org.tinygroup.convert.text.config;

import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class TextCell {
	private String value;
	private int length;
	private static Logger logger = LoggerFactory.getLogger(TextCell.class);

	public TextCell(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String toString(boolean checkLength) {
		if (checkLength) {
			return AdjustValueLength(length, value);
		}
		return value;
	}

	private String AdjustValueLength(int propertyMaxLength, String data) {
		String newData = data;
		int adjustLength = propertyMaxLength - getLength(data);// data.length();
		if (adjustLength == 0)
			return newData;
		while (adjustLength != 0) {
			newData += " ";
			adjustLength--;
		}
		return newData;
	}

	private int getLength(String s) {
		try {
			return s.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			logger.errorMessage("获取字符串{0}的gbk编码长度时出错", e,s);
		}
		return 0;
	}
}
