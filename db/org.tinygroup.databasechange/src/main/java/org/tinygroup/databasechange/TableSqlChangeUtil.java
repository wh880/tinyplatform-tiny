/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
package org.tinygroup.databasechange;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.databasebuinstaller.DatabaseInstallerProcessor;
import org.tinygroup.tinytestutil.AbstractTestUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 表格结果变更信息记录的工具类
 * 
 * @author renhui
 * 
 */
public class TableSqlChangeUtil {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.exit(0);
		}
		String fileName = args[0];
		AbstractTestUtil.init(null, true);// 启动框架
		DatabaseInstallerProcessor processor = BeanContainerFactory
				.getBeanContainer(TableSqlChangeUtil.class.getClassLoader())
				.getBean("databaseInstallerProcessor");
		StringBuilder builder = new StringBuilder();
		try {
			Map<Class, List<String>> processSqls = processor.getChangeSqls();
			for (Class clazz : processSqls.keySet()) {
				builder.append("//-----").append(clazz.getSimpleName())
						.append("-----").append("\n\r");
				List<String> sqls = processSqls.get(clazz);
				for (String sql : sqls) {
					builder.append(sql).append(";").append("\n\r");
				}
				builder.append("//-----").append(clazz.getSimpleName())
						.append("-----").append("\n\r");
				builder.append("\n\r");
			}
			StreamUtil.writeText(builder, new FileWriter(new File(fileName)), true);
		} catch (Exception e) {
			e.printStackTrace(new PrintWriter(new FileWriter(new File(fileName)),true));
		}
	}
}
