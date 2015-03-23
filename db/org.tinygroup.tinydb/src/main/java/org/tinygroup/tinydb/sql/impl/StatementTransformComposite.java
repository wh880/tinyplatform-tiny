package org.tinygroup.tinydb.sql.impl;

import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.Configuration;
import org.tinygroup.tinydb.config.BeanQueryConfig;
import org.tinygroup.tinydb.exception.TinyDbException;
import org.tinygroup.tinydb.sql.SqlAndValues;
import org.tinygroup.tinydb.sql.StatementTransform;

/**
 * 把bean对象转换成对应的sql语句
 * @author renhui
 *
 */
public class StatementTransformComposite extends StatementTransformAdapter implements StatementTransform {
	
	private DefaultStatementTransform statementTransform=new DefaultStatementTransform();
	
	public StatementTransformComposite() {
		super();
	}

	public StatementTransformComposite(Configuration configuration){
		super(configuration);
	}
	
	
	@Override
	public void init(Configuration configuration) {
		super.init(configuration);
		statementTransform.setConfiguration(configuration);
	}
	
	@Override
	public void setSchema(String schema) {
		super.setSchema(schema);
		statementTransform.setSchema(getSchema());
	}

	public SqlAndValues toSelect(Bean bean)throws TinyDbException {
		BeanQueryConfig beanQueryConfig=configuration.getBeanQueryConfig(bean.getType());
		if(beanQueryConfig==null){
			return statementTransform.toSelect(bean);
		}
		BeanQueryConfigStatementTransform statementTransform=new BeanQueryConfigStatementTransform(configuration,beanQueryConfig);
		statementTransform.setSchema(getSchema());
		return statementTransform.toSelect(bean);
	}

	public String toInsert(Bean bean)throws TinyDbException {
		return statementTransform.toInsert(bean);
	}

	public String toDelete(Bean bean)throws TinyDbException {
		return statementTransform.toDelete(bean);
	}

	public String toUpdate(Bean bean)throws TinyDbException{
		return statementTransform.toUpdate(bean);
	}

}
