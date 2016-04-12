/**
 * Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
 * <p>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.aopcache;

import org.tinygroup.aopcache.annotation.CacheGet;
import org.tinygroup.aopcache.annotation.CachePut;
import org.tinygroup.aopcache.annotation.CacheRemove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author renhui
 *
 */
public class AnnotationUserDao {
    public Map<Integer, User> container = new HashMap<Integer, User>();


    @CachePut(keys = "${user.id}", parameterNames = "user", group = "singleGroup", removeKeys = "users", removeGroups = "multiGroup")
    public void updateUser(User user) {
        System.out.println("update user");
    }

    @CachePut(keys = "${user.id}",merge=true, parameterNames = "user", group = "singleGroup", removeKeys = "users", removeGroups = "multiGroup")
    public void updateUserMerge(User user) {
		System.out.println("update user merge");
    }

    @CachePut(keys = "${user.id}", parameterNames = "user", group = "singleGroup", removeKeys = "users", removeGroups = "multiGroup")
    public void insertUser(User user) {
        System.out.println("insert user");
        container.put(user.id, user);
    }

    @CachePut(keys = {"${user.id}", "${user.name}"}, parameterNames = {"user", "user"}, group = "singleGroup", removeKeys = {"users", "users"}, removeGroups = {"multiGroup", "multiGroup"})
    public void insertUserTwoCache(User user) {
        System.out.println("insert user two cache");
        container.put(user.id, user);
    }

    @CachePut(keys = "${user.id}", parameterNames = "user", group = "singleGroup", removeKeys = "users", removeGroups = "multiGroup")
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

    @CacheRemove(group = "singleGroup", removeKeys = {"${userId}", "users"}, removeGroups = {"multiGroup", "multiGroup"})
    public void deleteUser(int userId) {
        System.out.println("delete user");
        container.remove(userId);
    }

    @CacheGet(key = "${userId}", group = "singleGroup")
    public User getUser(int userId) {
        System.out.println("get user");
        return container.get(userId);
    }

    //可配置多个key,用逗号分隔.第一次get时这些key会依次放入缓存,对应同一个value.当然也支持单个key
    @CacheGet(key = "${user?.name},${user?.id}", group = "singleGroup")
    public User getUser(User user) {
        System.out.println("get user by user");
        return container.get(user.id);
    }

    @CacheGet(key = "users", group = "multiGroup")
    public List<User> getUsers() {
        System.out.println("get users");
        List<User> users = new ArrayList<User>();
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

    public void clearContainer(){
        container.clear();
    }

}
