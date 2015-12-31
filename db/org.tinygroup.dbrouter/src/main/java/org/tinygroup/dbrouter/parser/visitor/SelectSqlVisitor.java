package org.tinygroup.dbrouter.parser.visitor;

import java.util.Iterator;

import org.tinygroup.dbrouter.exception.DbrouterRuntimeException;
import org.tinygroup.dbrouter.parser.GroupByColumn;
import org.tinygroup.dbrouter.parser.OrderByColumn;
import org.tinygroup.jsqlparser.expression.Alias;
import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.ExpressionVisitor;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.select.AllTableColumns;
import org.tinygroup.jsqlparser.statement.select.Fetch;
import org.tinygroup.jsqlparser.statement.select.Join;
import org.tinygroup.jsqlparser.statement.select.Limit;
import org.tinygroup.jsqlparser.statement.select.Offset;
import org.tinygroup.jsqlparser.statement.select.OrderByElement;
import org.tinygroup.jsqlparser.statement.select.Pivot;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;
import org.tinygroup.jsqlparser.statement.select.SelectItem;
import org.tinygroup.jsqlparser.statement.select.Top;
import org.tinygroup.jsqlparser.util.deparser.SelectDeParser;

/**
 * select语句访问对象
 * 
 * @author renhui
 *
 */
public class SelectSqlVisitor extends SelectDeParser {
	private SqlParserContext sqlParserContext;
	
	public SelectSqlVisitor(SqlParserContext sqlParserContext) {
		this.sqlParserContext=sqlParserContext;
		setBuffer(sqlParserContext.getBuffer());
	}

	public SelectSqlVisitor(ExpressionVisitor expressionVisitor,
			SqlParserContext sqlParserContext) {
		super(expressionVisitor, sqlParserContext.getBuffer());
		this.sqlParserContext = sqlParserContext;
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		buffer.append("SELECT ");
		if (plainSelect.getDistinct() != null) {
			buffer.append("DISTINCT ");
			if (plainSelect.getDistinct().getOnSelectItems() != null) {
				buffer.append("ON (");
				for (Iterator<SelectItem> iter = plainSelect.getDistinct()
						.getOnSelectItems().iterator(); iter.hasNext();) {
					SelectItem selectItem = iter.next();
					selectItem.accept(this);
					if (iter.hasNext()) {
						buffer.append(", ");
					}
				}
				buffer.append(") ");
			}

		}
		Top top = plainSelect.getTop();
		if (top != null) {
			buffer.append(top).append(" ");
		}

		for (Iterator<SelectItem> iter = plainSelect.getSelectItems()
				.iterator(); iter.hasNext();) {
			SelectItem selectItem = iter.next();
			selectItem.accept(this);
			if (iter.hasNext()) {
				buffer.append(", ");
			}
		}

		if (plainSelect.getIntoTables() != null) {
			buffer.append(" INTO ");
			for (Iterator<Table> iter = plainSelect.getIntoTables().iterator(); iter
					.hasNext();) {
				visit(iter.next());
				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
		}

		if (plainSelect.getFromItem() != null) {
			buffer.append(" FROM ");
			plainSelect.getFromItem().accept(this);
		}

		if (plainSelect.getJoins() != null) {
			for (Join join : plainSelect.getJoins()) {
				deparseJoin(join);
			}
		}

		if (plainSelect.getWhere() != null) {
			buffer.append(" WHERE ");
			plainSelect.getWhere().accept(expressionVisitor);
		}

		if (plainSelect.getOracleHierarchical() != null) {
			plainSelect.getOracleHierarchical().accept(expressionVisitor);
		}

		if (plainSelect.getGroupByColumnReferences() != null) {
			buffer.append(" GROUP BY ");
			for (Iterator<Expression> iter = plainSelect
					.getGroupByColumnReferences().iterator(); iter.hasNext();) {
				Expression columnReference = iter.next();
				if (columnReference instanceof Column) {
					Column column = (Column) columnReference;
					sqlParserContext.getGroupByColumns().add(
							new GroupByColumn(column));// 添加排序信息
				}
				columnReference.accept(expressionVisitor);
				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
		}

		if (plainSelect.getHaving() != null) {
			buffer.append(" HAVING ");
			plainSelect.getHaving().accept(expressionVisitor);
		}

		if (plainSelect.getOrderByElements() != null) {
			deparseOrderBy(plainSelect.isOracleSiblings(),
					plainSelect.getOrderByElements());
		}
		long skip = SqlParserContext.DEFAULT_SKIP_MAX;
		long rowCount = SqlParserContext.DEFAULT_SKIP_MAX;

		if (plainSelect.getLimit() != null) {
			Limit limit = plainSelect.getLimit();
			deparseLimit(limit);
			skip = getSkip(limit.isOffsetJdbcParameter(), limit.getOffset());
			rowCount = getRowCount(limit.isRowCountJdbcParameter(),
					limit.getRowCount());
		}
		if (plainSelect.getOffset() != null) {
			Offset offset = plainSelect.getOffset();
			deparseOffset(offset);
			skip = getSkip(offset.isOffsetJdbcParameter(), offset.getOffset());
		}
		if (plainSelect.getFetch() != null) {
			Fetch fetch = plainSelect.getFetch();
			deparseFetch(fetch);
			rowCount = getRowCount(fetch.isFetchJdbcParameter(),
					fetch.getRowCount());
		}
		if (skip != SqlParserContext.DEFAULT_SKIP_MAX && skip < 0) {
			throw new DbrouterRuntimeException("the skip is less than 0");
		}
		if (rowCount != SqlParserContext.DEFAULT_SKIP_MAX && rowCount < 0) {
			throw new DbrouterRuntimeException("the rowcount is less than 0");
		}
		sqlParserContext.setSkip(skip);
		sqlParserContext.setRowCount(rowCount);
		sqlParserContext.setMax(getMax(skip, rowCount));
		if (plainSelect.isForUpdate()) {
			buffer.append(" FOR UPDATE");
		}
		sqlParserContext.setForUpdate(plainSelect.isForUpdate());

	}

	private long getMax(long skip, long rowCount) {
		if (skip == SqlParserContext.DEFAULT_SKIP_MAX) {
			if (rowCount == SqlParserContext.DEFAULT_SKIP_MAX) {
				// 如果skip和rowcount都不存在就返回默认值.
				return SqlParserContext.DEFAULT_SKIP_MAX;
			} else {
				// 如果skip不存在，就返回rowcount.
				return rowCount;
			}
		} else {
			if (rowCount == SqlParserContext.DEFAULT_SKIP_MAX) {
				return skip;
			} else {
				return skip + rowCount;
			}
		}
	}

	private long getSkip(boolean isJdbcParameter, long offset) {
		long skip = SqlParserContext.DEFAULT_SKIP_MAX;
		if (isJdbcParameter) {
			Object value = sqlParserContext.getParamValue();
			if (value == null) {
				skip = SqlParserContext.DEFAULT_SKIP_MAX;
			} else {
				try {
					skip = Long.parseLong(value.toString());
				} catch (Exception e) {
					throw new DbrouterRuntimeException(
							"bind limit var has an error , " + value
									+ " is not a long value");
				}
			}
		} else {
			skip = offset;
		}
		return skip;
	}

	private long getRowCount(boolean isJdbcParameter, long count) {
		long rowCount = SqlParserContext.DEFAULT_SKIP_MAX;
		if (isJdbcParameter) {
			Object value = sqlParserContext.getParamValue();
			if (value == null) {
				rowCount = SqlParserContext.DEFAULT_SKIP_MAX;
			} else {
				try {
					rowCount = Long.parseLong(value.toString());
				} catch (Exception e) {
					throw new DbrouterRuntimeException(
							"bind rowCount var has an error , " + value
									+ " is not a long value");
				}
			}
		} else {
			rowCount = count;
		}
		return rowCount;
	}

	@Override
	public void visit(OrderByElement orderBy) {
		Expression expression = orderBy.getExpression();
		if (expression instanceof Column) {
			Column column = (Column) expression;
			sqlParserContext.getOrderByColumns().add(
					new OrderByColumn(column, orderBy.isAsc()));// 获取排序信息
		}
		expression.accept(expressionVisitor);
		if (!orderBy.isAsc()) {
			buffer.append(" DESC");
		} else if (orderBy.isAscDescPresent()) {
			buffer.append(" ASC");
		}
		if (orderBy.getNullOrdering() != null) {
			buffer.append(' ');
			buffer.append(orderBy.getNullOrdering() == OrderByElement.NullOrdering.NULLS_FIRST ? "NULLS FIRST"
					: "NULLS LAST");
		}
	}

	@Override
	public void visit(Table tableName) {
		if (sqlParserContext.canReplaceTableName(tableName.getName())) {
			buffer.append(sqlParserContext.getTargetTableName());
		} else {
			buffer.append(tableName.getName());
		}
		Pivot pivot = tableName.getPivot();
		if (pivot != null) {
			pivot.accept(this);
		}
		Alias alias = tableName.getAlias();
		if (alias != null) {
			buffer.append(alias);
		}
		sqlParserContext.getTableNames().add(tableName.getName());
	}

	public void visit(AllTableColumns allTableColumns) {
		if (sqlParserContext.canReplaceTableName(allTableColumns.getTable()
				.getName())) {
			buffer.append(sqlParserContext.getTargetTableName()).append(".*");
		} else {
			buffer.append(allTableColumns.getTable().getFullyQualifiedName())
					.append(".*");
		}
	}

}
