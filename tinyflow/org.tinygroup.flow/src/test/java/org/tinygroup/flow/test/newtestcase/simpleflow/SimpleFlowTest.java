package org.tinygroup.flow.test.newtestcase.simpleflow;

import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.component.AbstractFlowComponent;

import com.google.common.collect.Lists;

/**
 * 
 * @author zhangliang08072
 * @version $Id: SimpleFlowTest.java, v 0.1 2016年4月28日 上午10:14:09 zhangliang08072 Exp $
 */
public class SimpleFlowTest extends AbstractFlowComponent {

	/**
	 * 流程的enable属性暂时没启用
	 */
//	public void testSimpleFlow0() {
//		Context context = new ContextImpl();
//		flowExecutor.execute("simpleFlowTest0", "begin", context);
//		assertEquals("马云22岁", context.get("simpleflowresult").toString());
//	}

	/**
	 * 组件的入参直接赋值
	 */
	public void testSimpleFlow1() {
		Context context = new ContextImpl();
		flowExecutor.execute("simpleFlowTest1", "begin", context);
		assertEquals("马云22岁", context.get("simpleflowresult").toString());
	}
	
	/**
	 * 组件节点参数采用${变量名}或者el取值
	 */
	public void testSimpleFlow2() {
		Context context = new ContextImpl();
		context.put("age", 33);
		context.put("name", "马化腾");
		flowExecutor.execute("simpleFlowTest2", "begin", context);
		assertEquals("马化腾33岁", context.get("simpleflowresult").toString());
	}
	
	/**
	 * 流程分支测试 a==6时
	 */
	public void testSimpleFlow3_1() {
		Context context = new ContextImpl();
		context.put("a", 6);
		flowExecutor.execute("simpleFlowTest3", "begin", context);
		assertEquals("马云6岁", context.get("simpleflowresult").toString());
	}
	
	/**
	 * 流程分支测试 a==16时
	 */
	public void testSimpleFlow3_2() {
		Context context = new ContextImpl();
		context.put("a", 16);
		flowExecutor.execute("simpleFlowTest3", "begin", context);
		assertEquals("马云16岁", context.get("simpleflowresult").toString());
	}
	
	/**
	 * 流程分支测试 a!=6&&a!=16
	 */
	public void testSimpleFlow3_3() {
		Context context = new ContextImpl();
		context.put("a", 56);
		context.put("age", 116);
		context.put("name", "马化腾");
		flowExecutor.execute("simpleFlowTest3", "begin", context);
		assertEquals("马化腾116岁", context.get("simpleflowresult").toString());
	}
	
	/**
	 * 测试循环
	 */
	public void testSimpleFlow4() {
		Context context = new ContextImpl();
		context.put("count", 0);
		context.put("sum", 1);
		
		flowExecutor.execute("simpleFlowCirculation", "begin", context);
		assertEquals(4, Integer.valueOf(context.get("sum").toString()).intValue());
	}
	
	/**
	 * 遍历List
	 */
	public void testSimpleFlow5() {
		List<Integer> lists = Lists.newArrayList();
		lists.add(1);
		lists.add(2);
		lists.add(3);
		Context context = new ContextImpl();
		context.put("lists", lists);
		context.put("listscount", 0);
		
		flowExecutor.execute("simpleFlowCirculation2", "begin", context);
		assertEquals(6, Integer.valueOf(context.get("listsum").toString()).intValue());
	}

}
