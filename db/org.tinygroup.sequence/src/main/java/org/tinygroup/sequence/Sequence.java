package org.tinygroup.sequence;

import org.tinygroup.sequence.exception.SequenceException;

/**
 * 生成序列号接口
 * 
 * @author renhui
 *
 */
public interface Sequence {
	/**
	 * 取得序列下一个值
	 *
	 * @return 返回序列下一个值
	 * @throws SequenceException
	 */
	long nextValue() throws SequenceException;
}
