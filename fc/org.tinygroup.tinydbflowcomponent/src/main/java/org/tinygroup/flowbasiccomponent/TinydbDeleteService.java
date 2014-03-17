package org.tinygroup.flowbasiccomponent;

import org.tinygroup.context.Context;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.operator.DBOperator;

/**
 * 删除操作服务
 * @author renhui
 *
 */
public class TinydbDeleteService extends AbstractTinydbService {

	public void tinyService(Context context, DBOperator operator) {

		Bean deleteBean=context.get(beanType);
		if(deleteBean!=null){
			int record=operator.delete(deleteBean);
			context.put(resultKey, record);
		}else{
			logger.logMessage(LogLevel.WARN, "删除服务时,从上下文中找不到bean对象，其beantype:[{}]",beanType);
		}
		
	}

}
