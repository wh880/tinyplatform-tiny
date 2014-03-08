package org.tinygroup.dbrouterjdbc4.thread;

import org.tinygroup.dbrouterjdbc4.jdbc.RealStatementExecutor;
import org.tinygroup.threadgroup.AbstractProcessor;

public class MultiThreadStatementProcessor<T> extends AbstractProcessor {
	
	private StatementProcessorCallBack<T> callBack;

	private RealStatementExecutor statement;
	
	private T resultSet;
	
	public MultiThreadStatementProcessor(String name) {
		super(name);
	}
	

	public MultiThreadStatementProcessor(String name, RealStatementExecutor statement) {
		super(name);
		this.statement = statement;
	}


	protected void action() throws Exception {
		if(callBack!=null){
		    resultSet=callBack.callBack(statement);	
		}
	}
	
	public T getResult(){
		return resultSet;
	}


	public void setCallBack(StatementProcessorCallBack<T> callBack) {
		this.callBack = callBack;
	}
	
	

}
