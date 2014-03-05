package org.tinygroup.tinypc.range;

import org.tinygroup.tinypc.Range;
import org.tinygroup.tinypc.RangeSpliter;

import java.util.ArrayList;
import java.util.List;

/**
 * 长整型数的平均分配
 * Created by luoguo on 14-3-3.
 */
public class LongRangeSpliter implements RangeSpliter<Long> {
    public List<Range<Long>> split(Long start, Long end, int pieces) {
        List<Range<Long>> pairList = new ArrayList<Range<Long>>();
        double step = (end - start + 1) / (double) pieces;
        for (double i = start; i < end; i += step) {
            Range<Long> range = new Range<Long>(Math.round(i), Math.round(i + step - 1));
            pairList.add(range);
        }
        return pairList;
    }
}
