package org.tinygroup.database.sequence;

import java.sql.Connection;
import java.sql.SQLException;

import org.tinygroup.database.config.sequence.Sequence;

/**
 * 序列sql处理
 * @author renhui
 *
 */
public interface SequenceSqlProcessor {
	String getCreateSql(Sequence sequence);
	String getDropSql(Sequence sequence);
	boolean checkSequenceExist(Sequence sequence,Connection connection)throws SQLException;
}
