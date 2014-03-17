package org.tinygroup.flowbasiccomponent;

import org.tinygroup.context.Context;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.operator.DBOperator;

/**
 * 查询操作服务
 * @author renhui
 *
 */
public class TinydbQueryService extends AbstractTinydbService {

	public void tinyService(Context context, DBOperator operator) {
		Bean queryBean=context.get(beanType);
		if(queryBean!=null){
			Bean[] beans= operator.getBeans(queryBean);
			context.put(resultKey, beans);
		}else{
			logger.logMessage(LogLevel.WARN, "查询服务时,从上下文中找不到bean对象，其beantype:[{}]",beanType);
		}

	}

}
