
package org.tinygroup.flow.test.newtestcase.simpleflow.component;

import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;

/**
 * 用于测试流程编排基础功能的组件
 * 
 * @author zhangliang08072
 * @version $Id: SimpleFlowComponent.java, v 0.1 2016年4月28日 上午9:37:55 zhangliang08072 Exp $
 */
public class SimpleFlowComponent implements ComponentInterface{
	Integer age;
	String name;
	
	public void execute(Context context) {
		//此部分是用于测试组件参数赋值
		context.put("age", age);
		context.put("name", name);
		context.put("simpleflowresult", name+age+"岁");
		
		//此部分是用于测试流程循环
		Integer count = context.get("count");
		Integer sum = context.get("sum");
		if(count!=null){
		    count++;
		    sum++;
		}
		context.put("count",count);
		context.put("sum",sum);
		
	}

	/**
	 * Getter method for property <tt>age</tt>.
	 * 
	 * @return property value of age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	     * Setter method for property <tt>age</tt>.
	     * 
	     * @param age value to be assigned to property age
	     */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * Getter method for property <tt>name</tt>.
	 * 
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	     * Setter method for property <tt>name</tt>.
	     * 
	     * @param name value to be assigned to property name
	     */
	public void setName(String name) {
		this.name = name;
	}
}
