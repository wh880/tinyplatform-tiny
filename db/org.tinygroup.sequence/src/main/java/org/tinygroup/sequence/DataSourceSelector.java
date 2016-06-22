package org.tinygroup.sequence;

import java.util.List;

/**
 * 数据源选择接口
 * @author renhui
 *
 */
public interface DataSourceSelector {

	public int getRandomDataSourceIndex(List<Integer> excludeIndexes);
	
}
