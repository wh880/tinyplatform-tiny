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

/**
 * 
 * @author renhui
 *
 */
public class XmlUserDao {

	Map<Integer, User> container = new HashMap<Integer, User>();

	private String testDbOperatorLog = "";//仅用于测试日志，记录从数据库获取的测试结果
	
	public void updateUser(User user) {
//		System.out.println("update user");
		testDbOperatorLog+="update user;";
	}

	public void insertUser(User user) {
//		System.out.println("insert user");
		testDbOperatorLog+="insert user;";
		container.put(user.id, user);
	}

	public User insertUserNoParam(User user) {
		System.out.println("insert user");
		container.put(user.id, user);
		user.setId(100);
		User resultUser = new User();
		resultUser.setId(user.getId());
		resultUser.setName(user.getName());
		resultUser.setAge(user.getAge());
		resultUser.setBirth(user.getBirth());
		return resultUser;
	}

	public void deleteUser(int userId) {
//		System.out.println("delete user");
		testDbOperatorLog+="delete user;";
		container.remove(userId);
	}

	public User getUser(int userId) {
//		System.out.println("get user");
		testDbOperatorLog+="get user;";
		return container.get(userId);
	}

	public User getUser(User user) {
		System.out.println("get user by user");
		return container.get(user.id);
	}

	public List<User> getUsers() {
//		System.out.println("get users");
		testDbOperatorLog+="get users;";
		List<User> users=new ArrayList<User>();
		users.addAll(container.values());
		return users;
	}

	public String getTestDbOperatorLog() {
		return testDbOperatorLog;
	}

	
}
