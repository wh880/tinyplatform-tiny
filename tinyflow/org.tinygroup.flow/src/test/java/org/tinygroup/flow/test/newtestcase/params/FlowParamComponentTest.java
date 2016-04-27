package org.tinygroup.flow.test.newtestcase.params;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.test.newtestcase.AbstractFlowTestcase;
 
/**
 * 
 * @description：流程及组件参数传递测试
 * @author: qiuqn
 * @version: 2016年4月15日 上午10:40:02
 */
public class FlowParamComponentTest extends AbstractFlowTestcase {

	/**
	 * 
	 * @description：
	 * 	参数el传递的类型为el表达式，参数值为 elflow,此时组件中参数el实际值为上下文中的key为elflow的值,如果上下文中不存在这个key，则值为null
	 * @author: qiuqn
	 * @version: 2016年4月13日上午9:55:51
	 */
	public void testflowParam1(){
		Context context = new ContextImpl();
		context.put("elflow", "a=1");
		context.put("a", "李白");
		flowExecutor.execute("flowParamTest1", "begin", context);
		assertEquals(1, Integer.valueOf(context.get("a").toString()).intValue());
	}
	
	/**
	 * 
	 * @description：
	 * 	参数el传递的类型为el表达式，参数值为 ${elflow},此时组件中参数el实际值为null， 不支持这种写法
	 * @author: qiuqn
	 * @version: 2016年4月13日上午9:55:51
	 */
	public void testflowParam2(){
		Context context = new ContextImpl();
		context.put("elflow", "a=2");
		context.put("a", "李白");
		context.put("el", "");
		flowExecutor.execute("flowParamTest2", "begin", context);
		assertEquals(null, context.get("el"));
	}

	/**
	 * 
	 * @description：
	 * 	参数el传递的类型为el表达式，参数值为  'a=3'(两个单引号，表示这是一个字符串),此时组件中参数el实际值为 a=3
	 * @author: qiuqn
	 * @version: 2016年4月13日上午9:55:51
	 */
	public void testflowParam3(){
		Context context = new ContextImpl();
		context.put("a", "李白");
		flowExecutor.execute("flowParamTest3", "begin", context);
		assertEquals(3, Integer.valueOf(context.get("a").toString()).intValue());
	}
	
	/**
	 * 
	 * @description：
	 * 	参数el传递的类型为el表达式，参数值为  b='a=4'(el表达式，这里是讲字符串“a=4”赋值给b)
	 * 	此时组件中参数el实际值为 a=4(参数值为el表达式则取el表达式执行后的值)
	 * @author: qiuqn
	 * @version: 2016年4月13日上午9:55:51
	 */
	public void testflowParam4(){
		Context context = new ContextImpl();
		context.put("b", "张三");
		context.put("a", "李白");
		context.put("el", "");
		flowExecutor.execute("flowParamTest4", "begin", context);
		assertEquals("a=4", context.get("b").toString());
		assertEquals("a=4", context.get("el").toString());
		assertEquals(4, Integer.valueOf(context.get("a").toString()).intValue());
	}
	
	/**
	 * 
	 * @description：
	 *	参数str传递的类型为java.lang.String，参数值为 b='testStr' ，此时组件中参数str实际值也为 b='testStr'
	 *	(流程参数的类型非el类型，但是参数值为el表达式时，会将该el表达式当做单纯的字符串)
	 * @author: qiuqn
	 * @version: 2016年4月13日上午9:55:51
	 */
	public void testflowParam5(){
		Context context = new ContextImpl();
		context.put("strflow", "testStr");
		context.put("str", "");
		flowExecutor.execute("flowParamTest5", "begin", context);
		assertEquals("b='testStr'", context.get("str").toString());
	}
	
	/**
	 * 
	 * @description：
	 *	参数str传递的类型为java.lang.String，参数值为 ${strflow}，此时组件中参数str实际值为上下文中的key为strflow的值
	 * @author: qiuqn
	 * @version: 2016年4月13日上午9:55:51
	 */
	public void testflowParam6(){
		Context context = new ContextImpl();
		context.put("strflow", "杜甫");
		context.put("str", "");
		flowExecutor.execute("flowParamTest6", "begin", context);
		assertEquals("杜甫", context.get("str").toString());
	}
	
	/**
	 * 
	 * @description：
	 * @author: qiuqn
	 * @version: 2016年4月13日上午9:55:51
	 */
	public void testflowParam7(){
		Context context = new ContextImpl();
		context.put("str", 1);
		context.put("str2", false);
		context.put("str3", 'A');
		flowExecutor.execute("flowParamTest7", "begin", context);
		assertEquals(new Boolean(false), Boolean.valueOf(context.get("obool").toString()));
		assertEquals(false, Boolean.valueOf(context.get("sbool").toString()).booleanValue());
		assertEquals(new Character('A'), context.get("ochar"));
		assertEquals('A', Character.valueOf(context.get("schar").toString().toCharArray()[0]).charValue());
		assertEquals(1, Integer.valueOf(context.get("sint").toString()).intValue());
		assertEquals(new Integer(1), Integer.valueOf(context.get("oint").toString()));
		assertEquals(new Double(1), Double.valueOf(context.get("odouble").toString()));
		assertEquals((double)1, Double.valueOf(context.get("sdouble").toString()).doubleValue());
		assertEquals(new Short("1"), Short.valueOf(context.get("oshort").toString()));
		assertEquals((short) 1, Short.valueOf(context.get("sshort").toString()).shortValue());
		assertEquals(new Long(1), Long.valueOf(context.get("olong").toString()));
		assertEquals((long)1, Long.valueOf(context.get("slong").toString()).longValue());
		assertEquals(new Float(1), Float.valueOf(context.get("ofloat").toString()));
		assertEquals((float)1, Float.valueOf(context.get("sfloat").toString()).floatValue());
	}
}
