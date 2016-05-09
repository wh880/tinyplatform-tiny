
package org.tinygroup.flow.test.newtestcase.exception.component;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;

/**
 * 辅助测试流程异常的组件
 * 
 * @author zhangliang08072
 * @version $Id: ExceptionUtilComponent.java, v 0.1 2016年4月27日 下午10:11:45 zhangliang08072 Exp $
 */
public class ExceptionUtilComponent implements ComponentInterface{
	Integer no;
	
	public void execute(Context context) {
		context.put("result", no);
	}

	/**
	 * Getter method for property <tt>no</tt>.
	 * 
	 * @return property value of no
	 */
	public Integer getNo() {
		return no;
	}

	/**
	     * Setter method for property <tt>no</tt>.
	     * 
	     * @param no value to be assigned to property no
	     */
	public void setNo(Integer no) {
		this.no = no;
	}
}
