package org.tinygroup.dbrouter.parser.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.dbrouter.exception.DbrouterRuntimeException;
import org.tinygroup.dbrouter.parser.GroupByColumn;
import org.tinygroup.dbrouter.parser.OrderByColumn;
import org.tinygroup.dbrouter.parser.base.ColumnInfo;
import org.tinygroup.dbrouter.parser.base.Condition;
import org.tinygroup.jsqlparser.schema.Column;

/**
 * Sql解析上下文
 * @author renhui
 *
 */
public class SqlParserContext {
	
	public final static long DEFAULT_SKIP_MAX = -1000;
	public static final String COUNT = "count";
	public static final String MAX = "max";
	public static final String AVG = "avg";
	public static final String MIN = "min";
	public static final String SUM = "sum";
	private final List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
	private final List<Condition> conditions = new ArrayList<Condition>();
	private final List<OrderByColumn> orderByColumns = new ArrayList<OrderByColumn>();
	private final List<GroupByColumn> groupByColumns = new ArrayList<GroupByColumn>();
	private final StringBuilder buffer = new StringBuilder(); //重新生成的sql
	// 逻辑表名
	private String logicTableName;
		// 要进行替换的表名
	private String targetTableName;
	private long skip;
	private long rowCount;
	private long max;
	private boolean isForUpdate;
	
	private String originalSql;//原来的sql
	
	private int jdbcParameterIndex = -1;// ?符合出现的位置
	private final Map<Integer, Condition> conditionMap = new HashMap<Integer, Condition>();
	private boolean existGroupFunction;//
	private boolean existAvg;
	
	private List<Object> arguments = new ArrayList<Object>();// sql的参数信息,sql语句"?"符号关联的参数值
	
	private boolean isDML;//是不是DML语句的标识
	private boolean isDDL;//是不是DDL语句的标识
	private final Set<String> tableNames=new HashSet<String>();//表名
	
	private ThreadLocal<Integer> indexLocal=new ThreadLocal<Integer>(){

		@Override
		protected Integer initialValue() {
			return 0;
		}
		
	};
	public long getSkip() {
		return skip;
	}
	public void setSkip(long skip) {
		this.skip = skip;
	}
	public long getRowCount() {
		return rowCount;
	}
	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public boolean isForUpdate() {
		return isForUpdate;
	}
	public void setForUpdate(boolean isForUpdate) {
		this.isForUpdate = isForUpdate;
	}
	public List<ColumnInfo> getColumns() {
		return columns;
	}
	public List<Condition> getConditions() {
		return conditions;
	}
	public List<OrderByColumn> getOrderByColumns() {
		return orderByColumns;
	}
	public List<GroupByColumn> getGroupByColumns() {
		return groupByColumns;
	}
	public String getLogicTableName() {
		return logicTableName;
	}
	public void setLogicTableName(String logicTableName) {
		this.logicTableName = logicTableName;
	}
	public String getTargetTableName() {
		return targetTableName;
	}
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
	
	public List<Object> getArguments() {
		return arguments;
	}
	
	public void setOriginalSql(String originalSql) {
		this.originalSql = originalSql;
	}
	public void setArguments(List<Object> arguments) {
		this.arguments = arguments;
	}
	public boolean isExistGroupFunction() {
		return existGroupFunction;
	}
	public void setExistGroupFunction(boolean existGroupFunction) {
		this.existGroupFunction = existGroupFunction;
	}
	public boolean isExistAvg() {
		return existAvg;
	}
	public void setExistAvg(boolean existAvg) {
		this.existAvg = existAvg;
	}
	
	public String getReplaceSql(){
		return buffer.toString();
	}
	
	public StringBuilder getBuffer() {
		return buffer;
	}
	
	public String getOriginalSql() {
		return originalSql;
	}
	public boolean isDML() {
		return isDML;
	}
	public void setDML(boolean isDML) {
		this.isDML = isDML;
	}
	public boolean isDDL() {
		return isDDL;
	}
	public void setDDL(boolean isDDL) {
		this.isDDL = isDDL;
	}
	public Set<String> getTableNames() {
		return tableNames;
	}
	
	public Map<Integer, Condition> getConditionMap() {
		return conditionMap;
	}
	protected Object getParamValue(){
		int index=jdbcParameterIndex++;
		if(index>=arguments.size()){
			throw  new DbrouterRuntimeException(String.format("SQL的参数列表长度存在问题,expect:%d,actual:%d",index,arguments.size()));
		}
		return arguments.get(index);
	}

	
	public boolean canReplaceTableName(String tableName) {
		return tableName.equalsIgnoreCase(logicTableName)
				&& !StringUtil.isBlank(targetTableName);
	}
	

	public boolean isGroupFunction(String functionName) {
		if (StringUtil.isBlank(functionName)) {
			return false;
		}
		if (functionName.equalsIgnoreCase(AVG)
				|| functionName.equalsIgnoreCase(COUNT)
				|| functionName.equalsIgnoreCase(MAX)
				|| functionName.equalsIgnoreCase(MIN)
				|| functionName.equalsIgnoreCase(SUM)) {
			return true;
		}
		return false;
	}
	
	protected void setColumnIndex(Integer index){
		indexLocal.set(index);
	}
	
	protected Integer getColumnIndex(){
		return indexLocal.get();
	}
	
	protected void addColumn(Column column) {
		getColumns().add(new ColumnInfo(column));
	}

	protected void addCondition(int index, Column column) {
		ColumnInfo columnInfo = new ColumnInfo(column);
		Condition condition = new Condition();
		condition.setColumn(columnInfo);
		condition.setOperator(ExpressionSqlVisitor.EQUALS_OPERATOR);
		getConditions().add(condition);
		getConditionMap().put(index, condition);
	}
	
	
}
