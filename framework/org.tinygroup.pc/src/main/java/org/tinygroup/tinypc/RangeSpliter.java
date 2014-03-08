package org.tinygroup.tinypc;

import java.util.List;

/**
 * 数字范围分解器
 * Created by luoguo on 14-3-3.
 */
public interface RangeSpliter<T extends Number> {
    /**
     * 把从值域范围的值均分成pieces份
     *
     * @param start  开始值
     * @param end    结束值
     * @param pieces 要分成的片
     * @return
     */
    List<Range<T>> split(T start, T end, int pieces);

    List<Range<T>> split(Range<T> range, int pieces);
}
