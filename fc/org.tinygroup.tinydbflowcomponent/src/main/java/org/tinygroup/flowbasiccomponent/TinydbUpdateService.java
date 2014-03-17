package org.tinygroup.flowbasiccomponent;

import org.tinygroup.context.Context;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.operator.DBOperator;

/**
 * 修改操作服务
 * @author renhui
 *
 */
public class TinydbUpdateService extends AbstractTinydbService {

	public void tinyService(Context context, DBOperator operator) {

		Bean updateBean=context.get(beanType);
		if(updateBean!=null){
			int record=operator.update(updateBean);
			context.put(resultKey, record);
		}else{
			logger.logMessage(LogLevel.WARN, "修改服务时,从上下文中找不到bean对象，其beantype:[{}]",beanType);
		}
		
	}

}
