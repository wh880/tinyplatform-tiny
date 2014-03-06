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
package org.tinygroup.convert.xsdjava;

import java.util.List;

import org.tinygroup.convert.Converter;

public class SchemaToClass implements Converter<List<String>, Void> {
	private String baseFolder;
	private String packageName;
	private String xjbFolder;

	public SchemaToClass(String baseFolder, String xjbFolder, String packageName) {
		this.baseFolder = baseFolder;
		this.packageName = packageName;
		this.xjbFolder = xjbFolder;
	}

	public Void convert(List<String> xsdFileNames) {
		String[] args = new String[9];
		args[0] = "-d";
		args[1] = baseFolder;
		args[2] = "-p";
		args[3] = packageName;
		args[4] = "-verbose";
		args[5] = "-b";
		args[6] = xjbFolder;
		args[7] = "-extension";
		for (int i = 0; i < xsdFileNames.size(); i++) {
			args[8] = xsdFileNames.get(i);
			try {
				XJCFacade.main(args);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

}
