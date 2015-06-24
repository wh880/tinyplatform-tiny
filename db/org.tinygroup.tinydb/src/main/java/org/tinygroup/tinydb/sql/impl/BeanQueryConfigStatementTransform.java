package org.tinygroup.tinydb.sql.impl;

import org.tinygroup.commons.tools.ArrayUtil;
import org.tinygroup.commons.tools.ObjectUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.Configuration;
import org.tinygroup.tinydb.config.BeanQueryConfig;
import org.tinygroup.tinydb.config.ConditionConfig;
import org.tinygroup.tinydb.config.GroupByConfig;
import org.tinygroup.tinydb.config.OrderByConfig;
import org.tinygroup.tinydb.exception.TinyDbException;
import org.tinygroup.tinydb.sql.SelectSqlBuilder;
import org.tinygroup.tinydb.sql.SqlAndValues;
import org.tinygroup.tinydb.sql.condition.ConditionGenerater;
import org.tinygroup.tinydb.sql.condition.impl.EqualsConditionGenerater;
import org.tinygroup.tinydb.sql.condition.impl.IsEmptyConditionGenerater;
import org.tinygroup.tinydb.sql.condition.impl.IsNullConditionGenerater;
import org.tinygroup.tinydb.sql.group.GroupGenerater;
import org.tinygroup.tinydb.sql.order.OrderGenerater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据bean查询配置信息来转换bean
 * 
 * @author renhui
 * 
 */
public class BeanQueryConfigStatementTransform extends
		StatementTransformAdapter {

	private BeanQueryConfig beanQueryConfig;

	private Map<String, String> conditionModeMap = new HashMap<String, String>();

	private Map<String, String> orderModeMap = new HashMap<String, String>();

	public BeanQueryConfigStatementTransform(Configuration configuration,
			BeanQueryConfig beanQueryConfig) {
		super(configuration);
		this.beanQueryConfig = beanQueryConfig;
		List<ConditionConfig> conditionConfigs = beanQueryConfig
				.getConditionConfigs();
		for (ConditionConfig conditionConfig : conditionConfigs) {
			conditionModeMap.put(conditionConfig.getPropertyName(),
					conditionConfig.getConditionMode());
		}
		List<OrderByConfig> orderByConfigs = beanQueryConfig
				.getOrderByConfigs();
		for (OrderByConfig orderByConfig : orderByConfigs) {
			orderModeMap.put(orderByConfig.getPropertyName(),
					orderByConfig.getOrderMode());
		}

	}

	public SqlAndValues toSelect(Bean bean) throws TinyDbException {
		List<Object> params = new ArrayList<Object>();
		SelectSqlBuilder builder = new SelectSqlBuilder(new StringBuffer());
		String selectItem = getReplaceValue(Bean.SELECT_ITEMS_KEY, bean);
		builder.appendSelectItem(selectItem);
		builder.appendTable(getFullTableName(bean.getType()));
		String conditionSegment = appendCondition(bean, params);
		builder.appendCondition(conditionSegment);
		String groupBySegment = appendGroupBY(bean);
		builder.appendGroupBy(groupBySegment);
		String orderSegment = appendOrderBy(bean);
		builder.appendOrderBy(orderSegment);
		return new SqlAndValues(builder.toSelectSql(), params);
	}

	private String appendOrderBy(Bean bean) throws TinyDbException {
		String[] orderFields = getReplaceValues(Bean.ORDER_BY_KEY, bean);
		String[] sortDirections = getReplaceValues(Bean.SORT_DIRECTION_KEY,
				bean);
		check(orderFields, sortDirections);
		StringBuffer orderSegment = new StringBuffer();
		List<OrderByConfig> orderByConfigs = null;
		if (!ArrayUtil.isEmptyArray(orderFields)
				&& !ArrayUtil.isEmptyArray(sortDirections)) {
			orderByConfigs = new ArrayList<OrderByConfig>();
			for (int i = 0; i < orderFields.length; i++) {
				orderByConfigs.add(new OrderByConfig(orderFields[i],
						sortDirections[i]));
			}
		} else {
			orderByConfigs = beanQueryConfig.getOrderByConfigs();
		}
		for (OrderByConfig orderByConfig : orderByConfigs) {
			if (orderSegment.length() > 0) {
				orderSegment.append(",");
			}
			String columnName = beanDbNameConverter
					.propertyNameToDbFieldName(orderByConfig.getPropertyName());
			OrderGenerater orderGenerater = configuration
					.getOrderGenerater(orderByConfig.getOrderMode());
			orderSegment.append(orderGenerater.generateOrder(columnName));
		}
		return orderSegment.toString();
	}

	private void check(String[] orderFields, String[] sortDirections)
			throws TinyDbException {
		if (!ArrayUtil.isArraySameLength(orderFields, sortDirections)) {
			throw new TinyDbException("排序字段与排序方向的参数数组长度必须相同");
		}
	}

	private String appendGroupBY(Bean bean) {
		StringBuffer groupBySegment = new StringBuffer();
		String[] groupFields = getReplaceValues(Bean.GROUP_BY_KEY, bean);
		List<GroupByConfig> groupByConfigs = null;
		if (!ArrayUtil.isEmptyArray(groupFields)) {
			groupByConfigs = new ArrayList<GroupByConfig>();
			for (int i = 0; i < groupFields.length; i++) {
				groupByConfigs.add(new GroupByConfig(groupFields[i]));
			}
		} else {
			groupByConfigs = beanQueryConfig.getGroupByConfigs();
		}
		for (GroupByConfig groupByConfig : groupByConfigs) {
			if (groupBySegment.length() > 0) {
				groupBySegment.append(",");
			}
			String columnName = beanDbNameConverter
					.propertyNameToDbFieldName(groupByConfig.getPropertyName());
			GroupGenerater generater = configuration.getGroupGenerater();
			groupBySegment.append(generater.generateGroupBy(columnName));
		}
		return groupBySegment.toString();
	}

	private String appendCondition(Bean bean, List<Object> params)
			throws TinyDbException {
		StringBuffer conditionSegment = new StringBuffer();
		String[] conditionFields = getReplaceValues(Bean.CONDITION_FIELD_KEY,
				bean);
		String[] conditionModes = getReplaceValues(Bean.CONDITION_MODE_KEY,
				bean);
		check(conditionFields, conditionModes);
		repalceConfig(conditionFields, conditionModes);
		List<String> conditionColumns = getColumnNames(bean);
		String emptyFields = getReplaceValue(Bean.EMPTY_CONDITION_KEY, bean);
		for (String columnName : conditionColumns) {
			String propertyName = beanDbNameConverter
					.dbFieldNameToPropertyName(columnName);
			boolean emptyCondition = isEmptyCondition(propertyName, emptyFields);
			Object value = bean.get(propertyName);
			if (!emptyCondition&&ObjectUtil.isEmptyObject(value)) {//如果条件字段不在空条件列表中,则不加条件子句.
				continue;
			}
			if (conditionSegment.length() > 0) {
				conditionSegment.append(" and ");
			}
			String conditionMode = conditionModeMap.get(propertyName);
			ConditionGenerater generater = configuration
					.getConditionGenerater(conditionMode);
			if (value == null) {// 如果值是为空或者null,那么使用为空或者null条件
				generater = new IsNullConditionGenerater();
			} else if (value instanceof String) {
				if(StringUtil.isBlank((String) value)){
					generater = new IsEmptyConditionGenerater();
				}
			}
			if (generater == null) {// 还是为空则用equals
				generater = new EqualsConditionGenerater();
			}
			generater.setValue(value);
			conditionSegment.append(generater.generateCondition(columnName));
			generater.paramValueProcess(params);
		}
		return conditionSegment.toString();
	}

	private boolean isEmptyCondition(String propertyName, String emptyFields) {
		if (emptyFields != null && emptyFields.indexOf(propertyName) != -1) {
			return true;
		}
		return false;
	}

	private void repalceConfig(String[] conditionFields, String[] conditionModes) {
		if (!ArrayUtil.isEmptyArray(conditionFields)
				&& !ArrayUtil.isEmptyArray(conditionModes)) {
			for (int i = 0; i < conditionModes.length; i++) {
				conditionModeMap.put(conditionFields[i], conditionModes[i]);
			}
		}
	}

	private String[] getReplaceValues(String propertyName, Bean bean) {
		Object value = bean.get(propertyName);
		if (value == null) {
			return null;
		}
		if (value.getClass().isArray()) {
			return (String[]) value;
		}
		return ((String) value).split(",");
	}

	private String getReplaceValue(String propertyName, Bean bean) {
		Object value = bean.get(propertyName);
		if (value == null) {
			return null;
		}
		if (value.getClass().isArray()) {
			return StringUtil.join((Object[]) value, ",");
		}
		return (String) value;
	}
}
