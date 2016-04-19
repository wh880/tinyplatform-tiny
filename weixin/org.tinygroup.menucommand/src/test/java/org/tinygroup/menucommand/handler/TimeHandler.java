package org.tinygroup.menucommand.handler;

import org.tinygroup.context.Context;
import org.tinygroup.menucommand.config.MenuCommand;

/**
 * 时间转换有两种做法：<br/>
 * 1. 在Handler处理，page渲染显示<br/>
 * 2. 直接利用模板函数formatDate在页面处理<br/>
 * 测试例子采用方案二
 * @author yancheng11334
 *
 */
public class TimeHandler extends MenuCommandHandler{

	@Override
	protected void execute(String command, MenuCommand menuCommand,
			Context context) {
		// TODO Auto-generated method stub
		
	}

}
