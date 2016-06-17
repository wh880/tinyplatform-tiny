package org.tinygroup.sequence;

import org.tinygroup.sequence.exception.SequenceException;

/**
 * 序列dao接口
 * @author renhui
 *
 */
public interface SequenceDao {
    /**
     * 取得下一个可用的序列区间
     *
     * @param name 序列名称
     * @return 返回下一个可用的序列区间
     * @throws SequenceException
     */
    SequenceRange nextRange(String name) throws SequenceException;

}