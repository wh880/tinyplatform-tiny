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
package org.tinygroup.context2object.test.testcase.generator2;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.ObjectGenerator;
import org.tinygroup.context2object.User;
import org.tinygroup.context2object.impl.ClassNameObjectGenerator;
import org.tinygroup.context2object.impl.DetectDateTypeConverter;
import org.tinygroup.context2object.test.generator2.testcase.BaseTestCast2;
import org.tinygroup.context2object.test.testcase.ListCreator;
import org.tinygroup.tinytestutil.AbstractTestUtil;

/**
 * 功能说明：<br/>
 * DetectDateTypeConverter 测试类
 * </br>
 * 开发人员：zhengkk(zhengkk@strongit.com.cn)<br/>
 * 开发时间：2015年3月5日<br/>
 */
public class testDetectDateTypeConverter extends BaseTestCast2 {
    protected ObjectGenerator generator = new ClassNameObjectGenerator();

    protected void setUp() {
        generator.addTypeCreator(new ListCreator());
        generator.addTypeConverter(new DetectDateTypeConverter());
        AbstractTestUtil.init(null, true);
    }

    public void testGetObjectWithDateCn() throws ParseException {
        Context context = new ContextImpl();
        context.put("birthday", "1999-03-03");
        User user = (User) generator.getObject(null,null,User.class.getName(),this.getClass().getClassLoader(), context);
        assertEquals(user.getBirthday(),new SimpleDateFormat("yyyy-MM-dd").parse("1999-03-03"));
    }
    
    public void testGetObjectWithDateListCn() throws ParseException {
        Context context = new ContextImpl();
        String[] days = new String[]{"1999-03-03","1999-03-04"};
        context.put("updateDay", days);
        User user = (User) generator.getObject(null,null,User.class.getName(),this.getClass().getClassLoader(), context);
//        assertEquals(user.getBirthday(),new SimpleDateFormat("yyyy-MM-dd").parse("1999-03-03"));
        assertEquals(user.getUpdateDay().size(), 2);
        assertEquals(user.getUpdateDay().get(0), new SimpleDateFormat("yyyy-MM-dd").parse("1999-03-03"));
        assertEquals(user.getUpdateDay().get(1), new SimpleDateFormat("yyyy-MM-dd").parse("1999-03-04"));
    }
    
    public void testGetObjectWithDateZh() throws ParseException {
        Context context = new ContextImpl();
        context.put("birthday", "1999-03-03 12:06:52");
        User user = (User) generator.getObject(null,null,User.class.getName(),this.getClass().getClassLoader(), context);
        assertEquals(user.getBirthday(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1999-03-03 12:06:52"));
    }
    public void testGetObjectWithDateCnTime() throws ParseException {
        Context context = new ContextImpl();
        context.put("birthday", "1999年03月03日");
        User user = (User) generator.getObject(null,null,User.class.getName(),this.getClass().getClassLoader(), context);
        assertEquals(user.getBirthday(),new SimpleDateFormat("yyyy年MM月dd日").parse("1999年03月03日"));
    }
    public void testGetObjectWithDateZhTime() throws ParseException {
        Context context = new ContextImpl();
        context.put("birthday", "1999年03月03日  12:06:52");
        User user = (User) generator.getObject(null,null,User.class.getName(),this.getClass().getClassLoader(), context);
        assertEquals(user.getBirthday(),new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").parse("1999年03月03日 12:06:52"));
    }

}
