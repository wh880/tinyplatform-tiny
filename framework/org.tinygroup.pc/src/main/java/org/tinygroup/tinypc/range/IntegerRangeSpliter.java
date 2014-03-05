package org.tinygroup.tinypc.range;

import org.tinygroup.tinypc.Range;
import org.tinygroup.tinypc.RangeSpliter;

import java.util.ArrayList;
import java.util.List;

/**
 * 整型数字的平均切分
 * Created by luoguo on 14-3-3.
 */
public class IntegerRangeSpliter implements RangeSpliter<Integer> {
    public List<Range<Integer>> split(Integer start, Integer end, int pieces) {
        List<Range<Integer>> pairList = new ArrayList<Range<Integer>>();
        float step = (end - start + 1) / (float) pieces;
        for (float i = start; i < end; i += step) {
            Range<Integer> range = new Range<Integer>(Math.round(i), Math.round(i + step - 1));
            pairList.add(range);
        }
        return pairList;
    }
}
