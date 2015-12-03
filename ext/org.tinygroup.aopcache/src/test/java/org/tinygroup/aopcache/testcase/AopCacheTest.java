package org.tinygroup.aopcache.testcase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.tinygroup.aopcache.AnnotationUserDao;
import org.tinygroup.aopcache.User;
import org.tinygroup.aopcache.XmlUserDao;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cache.Cache;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class AopCacheTest extends TestCase {

	private static boolean inited;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (!inited) {
			AbstractTestUtil.init(null, true);
			inited = true;
		}
		Cache cache=BeanContainerFactory.getBeanContainer(
				getClass().getClassLoader()).getBean("aopCache");
		cache.clear();
	}

	public void testAopCacheWithAnnotation() {
		long startTime=System.currentTimeMillis(); //获取开始时间
		AnnotationUserDao userDao = BeanContainerFactory.getBeanContainer(
				getClass().getClassLoader()).getBean("annotationUserDao");
		User user = userDao.getUser(1);
		assertNull(user);
		User user1 = new User(1, "flank", 10, null);
		User user2 = new User(2, "xuanxuan", 11, null);
		User user3 = new User(3, "liang", 12, null);
		userDao.insertUserNoParam(user1);
		userDao.insertUser(user2);
		userDao.insertUser(user3);
		user = userDao.getUser(1);// 从缓存再获取
		assertNotNull(user);
		user = userDao.getUser(1);// 第二次查询从缓存中获取
		assertNotNull(user);
		user.setAge(20);
		userDao.updateUser(user);
		user = userDao.getUser(1);
		assertEquals(20, user.getAge());
		Collection<User> users = userDao.getUsers();// 从数据库中加载
		assertEquals(3, users.size());
		users = userDao.getUsers();// 第二次查询从缓存中获取
		assertEquals(3, users.size());
		userDao.deleteUser(1);
		users = userDao.getUsers();// 从数据库中加载
		assertEquals(2, users.size());
		users = userDao.getUsers();
		assertEquals(2, users.size());// 第二次查询从缓存中获取
		userDao.insertUser(user1);// 重新插入
		users = userDao.getUsers();// 从数据库中加载
		assertEquals(3, users.size());
		users = userDao.getUsers();// 第二次查询从缓存中获取
		assertEquals(3, users.size());
		
		List<User> cusers = new ArrayList<User>();
		User cuser1 = new User(10,"tom", 20, null);
		userDao.insertUser(cuser1);
		cusers.add(cuser1);
		User cuser = userDao.getUser(cusers);
		assertEquals(20, cuser.getAge());//从数据库获取
		cuser = userDao.getUser(cusers);
		assertEquals(20, cuser.getAge());
		long endTime=System.currentTimeMillis(); //获取结束时间  
		System.out.println("run time： "+(endTime-startTime)+"ms"); 
	}

	public void testAopCacheWithXml() {
		long startTime=System.currentTimeMillis();  
		XmlUserDao userDao = BeanContainerFactory.getBeanContainer(
				getClass().getClassLoader()).getBean("xmlUserDao");
		User user = userDao.getUser(1);
		assertNull(user);
		User user1 = new User(1, "flank", 10, null);
		User user2 = new User(2, "xuanxuan", 11, null);
		User user3 = new User(3, "liang", 12, null);
		userDao.insertUser(user1);
		userDao.insertUser(user2);
		userDao.insertUser(user3);
		user = userDao.getUser(1);// 从缓存再获取
		assertNotNull(user);
		user = userDao.getUser(1);// 第二次查询从缓存中获取
		assertNotNull(user);
		user.setAge(20);
		userDao.updateUser(user);
		user = userDao.getUser(1);//从缓存获取
		assertEquals(20, user.getAge());
		Collection<User> users = userDao.getUsers();// 从数据库中加载
		assertEquals(3, users.size());
		users = userDao.getUsers();// 第二次查询从缓存中获取
		assertEquals(3, users.size());
		userDao.deleteUser(1);
		users = userDao.getUsers();// 从数据库中加载
		assertEquals(2, users.size());
		users = userDao.getUsers();
		assertEquals(2, users.size());// 第二次查询从缓存中获取
		userDao.insertUser(user1);// 重新插入
		users = userDao.getUsers();// 从数据库中加载
		assertEquals(3, users.size());
		users = userDao.getUsers();// 第二次查询从缓存中获取
		assertEquals(3, users.size());
		
		List<User> cusers = new ArrayList<User>();
		User cuser1 = new User(10,"tom", 20, null);
		userDao.insertUser(cuser1);
		cusers.add(cuser1);
		/*User cuser = userDao.getUser(cusers);
		assertEquals(20, cuser.getAge());//从数据库获取
		cuser = userDao.getUser(cusers);
		assertEquals(20, cuser.getAge());*/
		long endTime=System.currentTimeMillis(); //获取结束时间  
		System.out.println("run time： "+(endTime-startTime)+"ms"); 
	}
}
