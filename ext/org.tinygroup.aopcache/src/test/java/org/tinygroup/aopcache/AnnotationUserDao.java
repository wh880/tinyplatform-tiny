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
package org.tinygroup.aopcache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.aopcache.annotation.CacheGet;
import org.tinygroup.aopcache.annotation.CachePut;
import org.tinygroup.aopcache.annotation.CacheRemove;

/**
 * 
 * @author renhui
 *
 */
public class AnnotationUserDao {
	Map<Integer, User> container = new HashMap<Integer, User>();
	
	private String testDbOperatorLog = "";//仅用于测试日志，记录从数据库获取的测试结果
	
	
	public String getTestDbOperatorLog() {
		return testDbOperatorLog;
	}

	@CachePut(keys = "${user.id}", parameterNames = "user", group = "singleGroup", removeKeys = "users",removeGroups = "multiGroup")
	public void updateUser(User user) {
		testDbOperatorLog+="update user;";
//		System.out.println("update user");
	}

	@CachePut( keys = "${user.id}",parameterNames = "user", group = "singleGroup", removeKeys = "users",removeGroups = "multiGroup")
	public void insertUser(User user) {
		testDbOperatorLog+="insert user;";
//		System.out.println("insert user");
		container.put(user.id, user);
	}

	@CachePut( keys = "${user.id}",parameterNames = "", group = "singleGroup", removeKeys = "users",removeGroups = "multiGroup")
	public User insertUserNoParam(User user) {
		testDbOperatorLog+="insert user;";
//		System.out.println("insert user");
		container.put(user.id, user);
		user.setId(100);
		return user;
	}

	@CacheRemove(group = "singleGroup", removeKeys = "${userId},users", removeGroups ="multiGroup")
	public void deleteUser(int userId) {
		testDbOperatorLog+="delete user;";
//		System.out.println("delete user");
		container.remove(userId);
	}

	@CacheGet(key = "${userId}", group = "singleGroup")
	public User getUser(int userId) {
		testDbOperatorLog+="get user;";
//		System.out.println("get user");
		return container.get(userId);
	}

	@CacheGet(key = "users", group = "multiGroup")
	public List<User> getUsers() {
//		System.out.println("get users");
		testDbOperatorLog+="get users;";
		List<User> users=new ArrayList<User>();
		users.addAll(container.values());
		return users;
	}
	
	@CacheGet(key = "${userId}", group = "singleGroup")
	public User getUser(List<User> users) {
		System.out.println("get user by users");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return container.get(users.get(0).getId());
	}

}
