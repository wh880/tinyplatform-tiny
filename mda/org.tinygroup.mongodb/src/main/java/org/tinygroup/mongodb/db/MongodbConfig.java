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
package org.tinygroup.mongodb.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongodbConfig {

	public static final String HOST;
	public static final int PORT;
	public static final String DB_NAME;
	public static final String USER_NAME;
	public static final String PASSWORD;

	static {
		Properties prop = null;
		try {
			prop = new Properties();
			InputStream in = MongodbConfig.class
					.getResourceAsStream("/mongodb.config");

			prop.load(in);
		} catch (IOException e) {
			throw new RuntimeException("加载配置文件失败/mongodb.config！");
		}

		HOST = prop.getProperty("host");
		PORT = Integer.valueOf(prop.getProperty("port"));
		DB_NAME = prop.getProperty("dbname");
		USER_NAME = prop.getProperty("username");
		PASSWORD = prop.getProperty("password");
	}

}
