package org.tinygroup.tinysqldsl.build;

import org.tinygroup.tinysqldsl.base.StatementSqlBuilder;

/**
 * sql拼接处理与参数组装
 * 
 * @author renhui
 * 
 */
public interface SqlBuildProcessor {

	/**
	 * 实现接口的sql片段通过builder.appendSql(String
	 * segment)进行拼接,也可以builder.getStringBuilder方法获取StringBuilder,然后进行append
	 * 实现的参数信息通过builder.addParamValue(Object... values)进行参数组装
	 * 
	 * @param builder
	 */
	public void builder(StatementSqlBuilder builder);

}
