package org.tinygroup.tinydb.sql.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.commons.tools.ObjectUtil;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.BeanDbNameConverter;
import org.tinygroup.tinydb.Configuration;
import org.tinygroup.tinydb.config.ColumnConfiguration;
import org.tinygroup.tinydb.config.TableConfiguration;
import org.tinygroup.tinydb.exception.TinyDbException;
import org.tinygroup.tinydb.impl.DefaultNameConverter;
import org.tinygroup.tinydb.sql.SqlAndValues;
import org.tinygroup.tinydb.sql.StatementTransform;

public class StatementTransformAdapter implements StatementTransform {
	protected Configuration configuration;

	protected BeanDbNameConverter beanDbNameConverter = new DefaultNameConverter();;

	protected String schema;

	public StatementTransformAdapter() {
		super();
	}

	public StatementTransformAdapter(Configuration configuration) {
		init(configuration);
	}

	public void setConfiguration(Configuration configuration) {
		init(configuration);
	}

	public void init(Configuration configuration) {
		this.configuration = configuration;
		this.beanDbNameConverter = configuration.getConverter();
		this.schema = configuration.getDefaultSchema();
	}

	public SqlAndValues toSelect(Bean bean) throws TinyDbException {
		return null;
	}

	public String toInsert(Bean bean) throws TinyDbException {
		return null;
	}

	public String toDelete(Bean bean) throws TinyDbException {
		return null;
	}

	public String toUpdate(Bean bean) throws TinyDbException {
		return null;
	}

	/**
	 * 获取操作的所有数据库字段名称
	 * 
	 * @param bean
	 * @return
	 */
	protected List<String> getColumnNames(Bean bean) {
		TableConfiguration table = configuration.getTableConfiguration(
				bean.getType(), schema);
		List<String> insertColumnNames = new ArrayList<String>();
		for (ColumnConfiguration column : table.getColumns()) {
			String columnsName = column.getColumnName();
			String propertyName = beanDbNameConverter
					.dbFieldNameToPropertyName(columnsName);
			if (bean.containsKey(propertyName)) {
				insertColumnNames.add(columnsName);
			}
		}
		return insertColumnNames;
	}

	protected String getFullTableName(String beanType) {
		String tempType = beanType;
		String tableName = beanDbNameConverter.typeNameToDbTableName(tempType);
		return getTableNameWithSchame(tableName);
	}

	protected String getTableNameWithSchame(String tableName) {
		if (schema == null || "".equals(schema)) {
			return tableName;
		}
		return schema + "." + tableName;
	}

	protected String getConditionSql(List<String> conditionColumns, Bean bean) {
		StringBuffer condition = new StringBuffer();
		for (String columnName : conditionColumns) {
			if (bean == null || !checkBeanPropertyNull(bean, columnName)) {
				// 不判断参数是否为空
				if (condition.length() > 0) {
					condition.append(" and ");
				}
				condition.append(columnName).append("=?");
			}
		}
		return condition.toString();
	}

	// 判断bean的某个属性是否为空对象
	protected boolean checkBeanPropertyNull(Bean bean, String columnName) {
		String propertyName = beanDbNameConverter
				.dbFieldNameToPropertyName(columnName);
		Object value = bean.getProperty(propertyName);
		return ObjectUtil.isEmptyObject(value);
	}
	
	protected String getTableName(TableConfiguration table) {
		return getTableNameWithSchame(table.getName());
	}
	
	/**
	 * 创建新增sql
	 * 
	 * @param bean
	 * @return
	 * @throws TinyDbException
	 */
	protected String getInsertSql(Bean bean) throws TinyDbException {
		TableConfiguration table = configuration.getTableConfiguration(
				bean.getType(), schema);
		if (table != null) {
			StringBuffer sb = new StringBuffer();
			StringBuffer field = new StringBuffer();
			StringBuffer values = new StringBuffer();
			String tableName = getTableName(table);
			sb.append("insert into " + tableName + "(");
			List<ColumnConfiguration> columns = table.getColumns();
			if (columns != null && columns.size() > 0) {
				for (ColumnConfiguration column : columns) {
					String columnName = column.getColumnName();
					String propertyName = beanDbNameConverter
							.dbFieldNameToPropertyName(columnName);
					if (bean.containsKey(propertyName) || column.isPrimaryKey()) {
						field.append(",").append(columnName);
						values.append(",?");
					}
				}
				sb.append(field.substring(1)).append(")values(")
						.append(values.substring(1)).append(")");
				return sb.toString();
			} else {
				throw new TinyDbException("表格:" + tableName + "不存在字段");
			}

		}
		throw new TinyDbException("不存在beanType：" + bean.getType() + "的表格");
	}

	/**
	 * 获取删除对象的sql语句，以及所需的参数的property列表
	 * 
	 * @param beanType
	 * @param conditionColumns
	 * @return
	 * @throws TinyDbException
	 */
	protected String getDeleteSql(String beanType, List<String> conditionColumns)
			throws TinyDbException {
		if (conditionColumns == null || conditionColumns.size() == 0) {
			throw new TinyDbException("beanType为:" + beanType + "的删除操作不存在查询条件");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ").append(getFullTableName(beanType));
		String condition = getConditionSql(conditionColumns, null);
		if (condition != null && condition.length() > 0) {
			sb.append(" where ").append(condition);
		}
		return sb.toString();
	}

	/**
	 * @param bean
	 * @param conditionColumns
	 *            sql中用到的字段的列表，包括 update table set * where *两个*区域用到的所有字段
	 *            外部传进来的空列表，由此函数进行填充，字段按在sql中使用的先后顺序放入列表
	 * @return
	 * @throws TinyDbException
	 */
	protected String getUpdateSql(Bean bean, List<String> conditionColumns)
			throws TinyDbException {

		if (conditionColumns == null || conditionColumns.size() == 0) {
			throw new TinyDbException("beanType为:" + bean.getType()
					+ "的更新操作不存在查询条件");
		}
		TableConfiguration table = configuration.getTableConfiguration(
				bean.getType(), schema);
		if (table != null) {
			StringBuffer sb = new StringBuffer();
			String field = getUpdateFieldSegment(table, bean, conditionColumns);
			// 条件字段计算
			String condition = getConditionSql(conditionColumns, bean);
			sb.append("update ").append(getTableName(table)).append(" set ")
					.append(field.substring(1));
			if (condition != null && condition.length() > 0) {
				sb.append(" where ").append(condition);
			}
			return sb.toString();
		}
		throw new TinyDbException("不存在beanType：" + bean.getType() + "的表格");
	}

	private String getUpdateFieldSegment(TableConfiguration table, Bean bean,
			List<String> conditionColumns) {
		StringBuffer field = new StringBuffer();
		List<ColumnConfiguration> columns = table.getColumns();
		// 更新字段计算
		for (ColumnConfiguration column : columns) {
			String columnName = column.getColumnName();
			if (!column.isPrimaryKey()) {
				// 如果说不是条件字段
				if (!conditionColumns.contains(columnName)) {
					String propertyName = beanDbNameConverter
							.dbFieldNameToPropertyName(columnName);
					if (bean.containsKey(propertyName)
							&& bean.getMark(propertyName)) {
						field.append("," + columnName + "=?");
					}
				}
			}
		}
		return field.toString();
	}

	protected List<Object> getConditionParams(Bean bean) {
		TableConfiguration table = configuration.getTableConfiguration(
				bean.getType(), schema);
		List<Object> params = new ArrayList<Object>();
		for (ColumnConfiguration column : table.getColumns()) {
			String columnsName = column.getColumnName();
			String propertyName = beanDbNameConverter
					.dbFieldNameToPropertyName(columnsName);
			// 增加过滤条件
			if (bean.containsKey(propertyName)
					&& !ObjectUtil
							.isEmptyObject(bean.getProperty(propertyName))) {
				params.add(bean.get(propertyName));
			}
		}
		return params;
	}

}
