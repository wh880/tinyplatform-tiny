package org.tinygroup.dbrouter.impl.shardrule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tinygroup.commons.tools.Assert;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.dbrouter.ShardRule;
import org.tinygroup.dbrouter.config.Partition;
import org.tinygroup.dbrouter.config.Shard;
import org.tinygroup.dbrouter.factory.RouterManagerBeanFactory;
import org.tinygroup.dbrouter.parser.SqlParserResult;
import org.tinygroup.dbrouter.parser.SqlParserResultHold;
import org.tinygroup.dbrouter.parser.impl.DefaultSqlParserResult;
import org.tinygroup.dbrouter.parser.visitor.SqlParserContext;
import org.tinygroup.dbrouter.parser.visitor.StatementSqlVisitor;
import org.tinygroup.jsqlparser.statement.Statement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/*
 * 通用的shardrule可以支持ddl语句
 * 
 */
public abstract class AbstractParserResultShardRule implements ShardRule {
	
	@XStreamAlias("target-table-name")
	@XStreamAsAttribute
	private String targetTableName;
	
	@XStreamAlias("logic-table-name")
	@XStreamAsAttribute
	private String logicTabelName;
	
	public String getTargetTableName() {
		return targetTableName;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public String getLogicTabelName() {
		return logicTabelName;
	}

	public void setLogicTabelName(String logicTabelName) {
		this.logicTabelName = logicTabelName;
	}

	public boolean isMatch(Partition partition, Shard shard, String sql,
			Object... preparedParams) {
		List<Object> arguments = new ArrayList<Object>();
		Collections.addAll(arguments, preparedParams);
		SqlParserContext sqlParserContext=buildSqlParserContext(sql,arguments);
		Statement statement=RouterManagerBeanFactory.getManager().getSqlStatement(sql);
		StatementSqlVisitor sqlVisitor=new StatementSqlVisitor(sqlParserContext);
		statement.accept(sqlVisitor);
		SqlParserResult sqlParserResult=new DefaultSqlParserResult(sqlParserContext);
        SqlParserResultHold.setSqlParserResult(sqlParserResult);
		return internalMatch(partition, shard, sqlParserResult);
	}

	private SqlParserContext buildSqlParserContext(String sql,
			List<Object> arguments) {
		SqlParserContext sqlParserContext=new SqlParserContext();
		sqlParserContext.setOriginalSql(sql);
		sqlParserContext.setArguments(arguments);
		Assert.assertTrue(!StringUtil.isBlank(logicTabelName),"logicTabelName属性值不能为空");
		sqlParserContext.setLogicTabelName(logicTabelName);
		if(StringUtil.isBlank(targetTableName)){
			targetTableName=logicTabelName;
		}
		sqlParserContext.setTargetTableName(targetTableName);
		return sqlParserContext;
	}

	protected abstract boolean internalMatch(Partition partition, Shard shard,
			SqlParserResult parserResult);

	public String getReplacedSql(Partition partition, Shard shard, String sql,
			Object... preparedParams) {
		List<Object> arguments = new ArrayList<Object>();
		Collections.addAll(arguments, preparedParams);
		SqlParserResult parserResult = SqlParserResultHold.getSqlParserResult();
		return parserResult.getReplaceSql();
	}

}
