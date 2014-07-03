package org.tinygroup.database.sequence;

import org.tinygroup.database.config.sequence.Sequence;

/**
 * 序列sql处理
 * @author renhui
 *
 */
public interface SequenceSqlProcessor {
	String getCreateSql(Sequence sequence);
	String getDropSql(Sequence sequence);
}
