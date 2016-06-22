package org.tinygroup.sequence;

import java.util.List;

/**
 * 数据源选择接口
 * @author renhui
 *
 */
public interface DataSourceSelector {

	/**
	 * 随机选择数据源的接口，
	 * @param excludeIndexes 排除的数据源序号
	 * @return 返回数据源序号
	 */
	public int getRandomDataSourceIndex(List<Integer> excludeIndexes);
	
}
