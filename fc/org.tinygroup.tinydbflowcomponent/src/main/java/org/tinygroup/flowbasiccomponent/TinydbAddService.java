package org.tinygroup.flowbasiccomponent;

import org.tinygroup.context.Context;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.operator.DBOperator;

/**
 * tinydb的增删改查服务组件之添加服务
 * @author renhui
 *
 */
public class TinydbAddService extends AbstractTinydbService {
	

	public void tinyService(Context context,DBOperator operator){
		Bean paramBean=context.get(beanType);
		if(paramBean!=null){
			Bean insertBean=operator.insert(paramBean);
			context.put(resultKey, insertBean);
		}else{
			logger.logMessage(LogLevel.WARN, "新增服务时,从上下文中找不到bean对象，其beantype:[{}]",beanType);
		}
	}

}
