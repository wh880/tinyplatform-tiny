package org.tinygroup.tinydbdsl.operator;

import org.tinygroup.tinydbdsl.expression.Function;

/**
 * 统计操作接口
 * @author renhui
 *
 */
public interface StatisticsOperater {

	public Function sum();

	public Function count();

	public Function avg();

	public Function max();

	public Function min();

}
