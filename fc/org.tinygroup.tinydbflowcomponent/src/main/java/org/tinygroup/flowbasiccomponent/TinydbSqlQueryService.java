package org.tinygroup.flowbasiccomponent;

import org.tinygroup.context.Context;
import org.tinygroup.context.Context2Map;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.BeanOperatorManager;
import org.tinygroup.tinydb.operator.DBOperator;

/**
 * sql查询服务 querySql的参数值需要存在于上下文中
 * 例如：select * from aaa where name=@name,上下文要存在name的参数。
 * @author renhui
 *
 */
public class TinydbSqlQueryService implements ComponentInterface {
	
	private int start;

	private int limit;
	
	private String querySql;
	
	private String beanType;
	private String resultKey;
	private String schema;
	
	private BeanOperatorManager manager;
	
	
	public BeanOperatorManager getManager() {
		return manager;
	}

	public void setManager(BeanOperatorManager manager) {
		this.manager = manager;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	
	public String getBeanType() {
		return beanType;
	}

	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}

	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void execute(Context context) {
		DBOperator operator= manager.getDbOperator(schema, beanType);
		Bean[] beans=null;
		Context2Map context2Map=new Context2Map(context);
        if(limit!=0){//分页查询
			beans=operator.getPageBeans(querySql, start, limit, context2Map);
		}else{
			beans=operator.getBeans(querySql, context2Map);
		}
        if(beans!=null){
        	context.put(resultKey, beans);
        }
	}

}
