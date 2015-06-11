package org.tinygroup.jdbctemplatedslsession;

import javax.sql.DataSource;

/**
 *根据metadata获取表格相关的元数据信息
 * @author renhui
 *
 */
public interface TableMetaDataProvider {

	TableMetaData generatedKeyNamesWithMetaData(DataSource dataSource,
			String catalogName, String schemaName, String tableName);

}
