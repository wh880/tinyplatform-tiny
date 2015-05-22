package org.tinygroup.jdbctemplatedslsession;

import javax.sql.DataSource;

/**
 *根据metadata获取表格相关的主键信息 
 * @author renhui
 *
 */
public interface PrimaryKeyProvider {

	String[] generatedKeyNamesWithMetaData(DataSource dataSource,
			String catalogName, String schemaName, String tableName);

}
