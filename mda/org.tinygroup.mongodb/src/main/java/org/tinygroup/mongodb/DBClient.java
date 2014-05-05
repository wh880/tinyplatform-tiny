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
package org.tinygroup.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.tinygroup.mongodb.db.MongodbConfig;

import java.net.UnknownHostException;

public class DBClient {

	private static MongoClient CONNECTION;
	private static DB DB;

	public static void close() {
		if (CONNECTION != null) {
			CONNECTION.close();
		}
	}

	public static MongoClient getConnection() {
		if (CONNECTION == null) {
			try {
				CONNECTION = new MongoClient(MongodbConfig.HOST,
						MongodbConfig.PORT);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return CONNECTION;
	}

	public static DB getDB() {
		if (DB == null) {
			MongoClient conn = getConnection();
			if (conn != null) {
				DB = CONNECTION.getDB(MongodbConfig.DB_NAME);
			}
		}
		return DB;
	}

	public static DBCollection getCollection(String name) {
		DBCollection collection = null;
		DB db = getDB();
		if (db != null) {
			collection = db.getCollection(name);
		}
		return collection;
	}

}
