package org.tinygroup.aopcache.testcase;

import junit.framework.TestCase;
import org.tinygroup.aopcache.AnnotationUserDao;
import org.tinygroup.aopcache.User;
import org.tinygroup.aopcache.XmlUserDao;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cache.Cache;
import org.tinygroup.tinyrunner.Runner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by wangwy11342 on 2016/4/5.
 */
public class AnnotationAopCacheTest  extends TestCase {
    private static final String FIRST_GROUP = "singleGroup";
    private static final String SECOND_GROUP = "multiGroup";
    private static boolean initialized;//是否已初始化

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        if (!initialized) {
            Runner.init("application.xml",new ArrayList<String>());
            initialized = true;
        }
        Cache cache = BeanContainerFactory.getBeanContainer(
                getClass().getClassLoader()).getBean("aopCache");
        cache.clear();
    }

    public void testAopCacheWithAnnotation() {
        long startTime = System.currentTimeMillis(); //获取开始时间
        AnnotationUserDao userDao = BeanContainerFactory.getBeanContainer(
                getClass().getClassLoader()).getBean("annotationUserDao");
        //获取cache用于测试
        Cache cache = BeanContainerFactory.getBeanContainer(getClass().getClassLoader()).getBean("aopCache");
        cache.clear();
        User user = userDao.getUser(1);
        assertNull(user);
        User user1 = new User(1, "flank", 10, null);
        User user2 = new User(2, "xuanxuan", 11, null);
        User user3 = new User(3, "liang", 12, null);
        User user4 = new User(4, "zhangch", 18, null);

		/*操作前均为空*/
        assertNull(cache.get(FIRST_GROUP, "1"));
        assertNull(cache.get(FIRST_GROUP, "2"));
        assertNull(cache.get(FIRST_GROUP, "3"));
        assertNull(cache.get(FIRST_GROUP, "4"));

        userDao.insertUser(user1);
        assertEquals(cache.get(FIRST_GROUP, "1"), user1);
        userDao.insertUserTwoCache(user2);
        assertEquals(cache.get(FIRST_GROUP, "2"), user2);
        assertEquals(cache.get(FIRST_GROUP, "xuanxuan"), user2);
        userDao.insertUser(user3);
        assertEquals(cache.get(FIRST_GROUP, "3"), user3);
        userDao.insertUserNoParam(user4);
        assertNotNull(cache.get(FIRST_GROUP, "100"));
        assertNull(cache.get(FIRST_GROUP, "4"));

        cache.remove(FIRST_GROUP, "3");//删除user3缓存
        assertNull(cache.get(FIRST_GROUP, "3"));
        User zuser = userDao.getUser(user3);
        assertEquals(cache.get(FIRST_GROUP, "3"), user3);
        assertEquals(cache.get(FIRST_GROUP, "liang"), user3);

        User testuser = (User) cache.get(FIRST_GROUP, "100");
        assertNotSame(testuser.getId(), 4);//未配置参数的情况下缓存的是结果集
        //插入测试用例结束

        user = userDao.getUser(1);// 从缓存再获取
        assertNotNull(user);
        user = userDao.getUser(1);// 第二次查询从缓存中获取
        assertNotNull(user);
        assertEquals(cache.get(FIRST_GROUP, String.valueOf(user.getId())), user);//判断缓存中是否存在user
        testuser = (User) cache.get(FIRST_GROUP, String.valueOf(user.getId()));
        user.setAge(20);
        userDao.updateUser(user);
        assertEquals(testuser.getAge(), 20);//update后cache变为20
        user = userDao.getUser(1);
        assertEquals(20, user.getAge());

        assertNull(cache.get(SECOND_GROUP, "users"));//get前为null
        Collection<User> users = userDao.getUsers();// 从数据库中加载
        assertEquals(4, users.size());
        System.out.println(cache.get(SECOND_GROUP, "users"));
        assertEquals(((List<User>) cache.get(SECOND_GROUP, "users")).size(), 4);//get后为users

        users = userDao.getUsers();// 第二次查询从缓存中获取
        assertEquals(4, users.size());
        userDao.deleteUser(1);
        assertNull(cache.get(SECOND_GROUP, "users"));//
        users = userDao.getUsers();// 从数据库中加载
        assertEquals(cache.get(SECOND_GROUP, "users"), users);//get后为users

        assertEquals(3, users.size());
        users = userDao.getUsers();
        assertEquals(cache.get(SECOND_GROUP, "users"), users);//继续get仍然存在

        assertEquals(3, users.size());// 第二次查询从缓存中获取
        userDao.insertUser(user1);// 重新插入
        assertNull(cache.get(SECOND_GROUP, "users"));//重新插入后为null
        users = userDao.getUsers();// 从数据库中加载
        assertEquals(cache.get(SECOND_GROUP, "users"), users);//get后为users
        assertEquals(4, users.size());
        users = userDao.getUsers();// 第二次查询从缓存中获取
        assertEquals(4, users.size());

        List<User> cusers = new ArrayList<User>();
        User cuser1 = new User(10, "tom", 20, null);
        assertNull(cache.get(FIRST_GROUP, "10"));

        userDao.insertUser(cuser1);
        assertEquals(cache.get(FIRST_GROUP, "10"), cuser1);
        cusers.add(cuser1);
        User cuser = userDao.getUser(cusers);
        assertEquals(20, cuser.getAge());//从数据库获取
        cuser = userDao.getUser(cusers);
        assertEquals(20, cuser.getAge());

        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("run time： " + (endTime - startTime) + "ms");
    }

    /**
     * 测试merge属性为true时是否会合并缓存中的user
     */
    public void testUpdateMerge(){
        long startTime = System.currentTimeMillis();
        AnnotationUserDao userDao = BeanContainerFactory.getBeanContainer(
                getClass().getClassLoader()).getBean("annotationUserDao");
        //获取cache用于测试
        Cache cache = BeanContainerFactory.getBeanContainer(getClass().getClassLoader()).getBean("aopCache");
        //清空缓存
        cache.clear();

        Date date = new Date();
        User user = new User(1, "zhangch", 18, date);
        userDao.insertUser(user);

        User user2 = new User();
        user2.setId(1);
        user2.setName("zhangch2");
        userDao.updateUserMerge(user2);
        User cacheUser = (User) cache.get(FIRST_GROUP, String.valueOf(user2.getId()));


        assertEquals(cacheUser.getName(),"zhangch2");
        assertEquals(cacheUser.getBirth(),date);
        assertEquals(cacheUser.getAge(),0);
        assertEquals(cacheUser.getId(),1);
    }

    /**
     * 测试merge为false时是否覆盖
     */
    public void testUpdate(){
        long startTime = System.currentTimeMillis();
        AnnotationUserDao userDao = BeanContainerFactory.getBeanContainer(
                getClass().getClassLoader()).getBean("annotationUserDao");
        //获取cache用于测试
        Cache cache = BeanContainerFactory.getBeanContainer(getClass().getClassLoader()).getBean("aopCache");
        //清空缓存
        cache.clear();

        Date date = new Date();
        User user = new User(1, "zhangch", 18, date);
        userDao.insertUser(user);

        User user2 = new User();
        user2.setId(1);
        user2.setName("zhangch2");
        userDao.updateUser(user2);
        User cacheUser = (User) cache.get(FIRST_GROUP, String.valueOf(user2.getId()));


        assertEquals(cacheUser.getName(),"zhangch2");
        assertEquals(cacheUser.getBirth(),null);
        assertEquals(cacheUser.getAge(),0);
        assertEquals(cacheUser.getId(),1);
    }
}
