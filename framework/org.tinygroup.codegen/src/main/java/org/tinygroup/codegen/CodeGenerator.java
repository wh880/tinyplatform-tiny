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
package org.tinygroup.codegen;

import org.tinygroup.context.Context;

import java.io.IOException;
import java.util.List;

/**
 * 代码生成器
 * @author yancheng11334
 *
 */
public interface CodeGenerator {

	public static final String JAVA_ROOT="JAVA_ROOT";
	public static final String JAVA_TEST_ROOT="JAVA_TEST_ROOT";
	public static final String JAVA_RES_ROOT="JAVA_RES_ROOT";
	public static final String JAVA_TEST_RES_ROOT="JAVA_TEST_RES_ROOT";
	public static final String CODE_META_DATA="CODE_META_DATA";
	public static final String TEMPLATE_FILE="TEMPLATE_FILE";
	public static final String XSTEAM_PACKAGE_NAME="codegen";
	public static final String ABSOLUTE_PATH="ABSOLUTE_PATH";
	
	/**
	 * 代码生成处理方法
	 * @param context 上下文
	 * @throws IOException 
	 */
	public  List<String> generate(Context context) throws IOException;
}
