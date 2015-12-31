package org.tinygroup.dbrouter.parser.visitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.dbrouter.exception.DbrouterRuntimeException;
import org.tinygroup.dbrouter.util.DbRouterUtil;
import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.Statement;
import org.tinygroup.jsqlparser.statement.Statements;
import org.tinygroup.jsqlparser.statement.alter.Alter;
import org.tinygroup.jsqlparser.statement.create.index.CreateIndex;
import org.tinygroup.jsqlparser.statement.create.table.CreateTable;
import org.tinygroup.jsqlparser.statement.create.view.CreateView;
import org.tinygroup.jsqlparser.statement.delete.Delete;
import org.tinygroup.jsqlparser.statement.drop.Drop;
import org.tinygroup.jsqlparser.statement.execute.Execute;
import org.tinygroup.jsqlparser.statement.insert.Insert;
import org.tinygroup.jsqlparser.statement.replace.Replace;
import org.tinygroup.jsqlparser.statement.select.Join;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.statement.select.SelectBody;
import org.tinygroup.jsqlparser.statement.select.SelectExpressionItem;
import org.tinygroup.jsqlparser.statement.select.SelectItem;
import org.tinygroup.jsqlparser.statement.select.SubSelect;
import org.tinygroup.jsqlparser.statement.select.WithItem;
import org.tinygroup.jsqlparser.statement.truncate.Truncate;
import org.tinygroup.jsqlparser.statement.update.Update;

/**
 * 路由SQL对象的访问者
 * @author renhui
 *
 */
public class StatementSqlVisitor1 extends SqlVisitorAdapter {
	protected boolean isDML;//是不是DML语句的标识
	protected boolean isDDL;//是不是DDL语句的标识
	protected Set<String> tableNames=new HashSet<String>();//表名
	protected String logicTable;//逻辑表名
	protected String targetTable;//要替换成的表名
	protected Statement copyStatement;
	
	public StatementSqlVisitor1() {
		super();
	}

	public StatementSqlVisitor1(String logicTable, String targetTable) {
		super();
		this.logicTable = logicTable;
		this.targetTable = targetTable;
	}

	public String getReplacedSql(){
		return copyStatement.toString();
	}
	
	public Set<String> getTableNames() {
		return tableNames;
	}
	
	public boolean isDML() {
		return isDML;
	}

	public boolean isDDL() {
		return isDDL;
	}

	public void visit(Select select) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(select);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("Select SQL对象深拷贝出错",e);
		} 
		isDML=true;
		List<WithItem> withItems=select.getWithItemsList();
		if(!CollectionUtil.isEmpty(withItems)){
			for (WithItem withItem : withItems) {
				List<SelectItem> selectItems= withItem.getWithItemList();
				if(!CollectionUtil.isEmpty(selectItems)){
					for (SelectItem selectItem : selectItems) {
                         selectItem.accept(this);						
					}
				}
				SelectBody selectBody=withItem.getSelectBody();
				selectBody.accept(this);
			}
		}
		select.getSelectBody().accept(this);
	}
	
	public void visit(Delete delete) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(delete);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("Delete SQL对象深拷贝出错",e);
		} 
		isDML=true;
		delete.getTable().accept(this);
		delete.getWhere().accept(this);
	}

	public void visit(Update update) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(update);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("Update SQL对象深拷贝出错",e);
		} 
		isDML=true;
		List<Table> tables=update.getTables();
		for (Table table : tables) {
			 table.accept(this);
		}
		List<Column> columns=update.getColumns();
		if(update.isUseSelect()){
			for (int i = 0; i < columns.size(); i++) {
				columns.get(i).accept(this);
			}
			update.getSelect().accept(this);
		}else{
			List<Expression> expressions=update.getExpressions();
			for (int i = 0; i < columns.size(); i++) {
				columns.get(i).accept(this);
				expressions.get(i).accept(this);
			}
		}
		
		if(update.getFromItem()!=null){
			update.getFromItem().accept(this);
			List<Join> joins=update.getJoins();
			if(!CollectionUtil.isEmpty(joins)){
				for (Join join : joins) {
				    visit(join);
				}
			}
		}
		
		if(update.getWhere()!=null){
			update.getWhere().accept(this);
		}
		
	}

	public void visit(Join join){
		join.getRightItem().accept(this);
        if(join.getOnExpression()!=null){
        	join.getOnExpression().accept(this);
        }
        List<Column> columns=join.getUsingColumns();
        visitColumns(columns);
	}
	
	
	public void visit(Insert insert) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(insert);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("Insert SQL对象深拷贝出错",e);
		} 
		isDML=true;
		insert.getTable().accept(this);
		List<Column> columns=insert.getColumns();
		visitColumns(columns);
		if(insert.getItemsList()!=null){
			insert.getItemsList().accept(this);
		}
		if(insert.getSelect()!=null){
			insert.getSelect().accept(this);
		}
		List<SelectExpressionItem> returningExpressionList=insert.getReturningExpressionList();
		if(!CollectionUtil.isEmpty(returningExpressionList)){
			for (SelectExpressionItem selectExpressionItem : returningExpressionList) {
				selectExpressionItem.accept(this);
			}
		}
	}

	private void visitColumns(List<Column> columns) {
		if(!CollectionUtil.isEmpty(columns)){
			for (Column column : columns) {
				column.accept(this);
			}
		}
	}

	public void visit(Replace replace) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(replace);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("Replace SQL对象深拷贝出错",e);
		} 
		isDDL=true;
		
		replace.getTable().accept(this);
		List<Expression> expressions=replace.getExpressions();
	    List<Column> columns=replace.getColumns();
		if(expressions != null && columns != null){
			for (int i = 0; i < columns.size(); i++) {
				columns.get(i).accept(this);
				expressions.get(i).accept(this);
			}
		}else if(columns!=null){
			visitColumns(columns);
		}
		if(replace.getItemsList()!=null){
			replace.getItemsList().accept(this);
		}
		
	}

	public void visit(Drop drop) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(drop);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("Drop SQL对象深拷贝出错",e);
		} 
		isDDL=true;
		if(drop.getType().equalsIgnoreCase("table")){
			String tableName=drop.getName();
			if(canReplaceTableName(tableName)){
				Drop copyDrop=(Drop)copyStatement;
				copyDrop.setName(targetTable);
			}
			tableNames.add(drop.getName());
		}
	}

	/**
	 * 判断是不是可以进行表名替换
	 * @param tableName
	 * @return
	 */
	protected boolean canReplaceTableName(String tableName) {
		return tableName.equalsIgnoreCase(logicTable)&&!StringUtil.isBlank(targetTable);
	}

	public void visit(Truncate truncate) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(truncate);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("Truncate SQL对象深拷贝出错",e);
		} 
		isDDL=true;
		truncate.getTable().accept(this);
	}

	public void visit(CreateIndex createIndex) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(createIndex);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("CreateIndex SQL对象深拷贝出错",e);
		} 
		isDDL=true;
		createIndex.getTable().accept(this);
	}

	public void visit(CreateTable createTable) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(createTable);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("CreateTable SQL对象深拷贝出错",e);
		} 
		isDDL=true;
		createTable.getTable().accept(this);
		if(createTable.getSelect()!=null){
			createTable.getSelect().accept(this);
		}
		
	}

	public void visit(CreateView createView) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(createView);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("CreateView SQL对象深拷贝出错",e);
		} 
		isDDL=true;
		createView.getView().accept(this);
		if(createView.getSelectBody()!=null){
			createView.getSelectBody().accept(this);
		}
	}

	public void visit(Alter alter) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(alter);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("Alter SQL对象深拷贝出错",e);
		} 
		isDDL=true;
		alter.getTable().accept(this);
		
	}

	public void visit(Statements stmts) {
		for (Statement statement : stmts.getStatements()) {
			statement.accept(this);
		}
	}

	public void visit(Execute execute) {
		try {
			copyStatement=(Statement) DbRouterUtil.deepCopy(execute);
		} catch (Exception e) {
			throw new DbrouterRuntimeException("execute SQL对象深拷贝出错",e);
		} 
		execute.getExprList().accept(this);
	}
	
	@Override
	public void visit(SubSelect subSelect) {
		subSelect.getSelectBody().accept(this);
	}


}
