package org.tinygroup.flowbasiccomponent;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.BeanOperatorManager;
import org.tinygroup.tinydb.operator.DBOperator;

public abstract class AbstractTinydbService implements ComponentInterface {

	
	protected String beanType;
	protected String resultKey;
	protected String schema;
	
	protected Logger logger=LoggerFactory.getLogger(TinydbAddService.class);

	public String getBeanType() {
		return beanType;
	}

	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}
	
	

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}
	
	public void execute(Context context) {
		BeanOperatorManager manager=SpringUtil.getBean(BeanOperatorManager.OPERATOR_MANAGER_BEAN);
		DBOperator operator= manager.getDbOperator(schema, beanType);
		tinyService(context, operator);
	}
	
	public abstract void tinyService(Context context,DBOperator operator);
	

	
}
