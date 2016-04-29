
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
public class SimpleFlowForeachComponent implements ComponentInterface{
	
	public void execute(Context context) {
		
		//此部分用于测试遍历
		Integer listscount = context.get("listscount");
		Integer listsum = context.get("listsum");
		if(listsum==null){
			listsum=0;
		}
		List<Integer> lists = context.get("lists");
		listsum+=lists.get(listscount);
		listscount++;
		context.put("listscount",listscount);
		context.put("listsum",listsum);
		
	}
}
