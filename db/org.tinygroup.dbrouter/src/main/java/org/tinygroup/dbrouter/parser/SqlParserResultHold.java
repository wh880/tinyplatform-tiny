package org.tinygroup.dbrouter.parser;


/**
 * 
 * @author renhui
 *
 */
public class SqlParserResultHold {

	private static ThreadLocal<SqlParserResult> parserResultLocal = new ThreadLocal<SqlParserResult>();

	public static SqlParserResult getSqlParserResult(){
		return parserResultLocal.get();
	}
	
	public static void setSqlParserResult(SqlParserResult sqlParserResult){
		parserResultLocal.set(sqlParserResult);
	}

}
