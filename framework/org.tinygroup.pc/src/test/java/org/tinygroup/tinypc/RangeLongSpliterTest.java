package org.tinygroup.tinypc;

import junit.framework.TestCase;
import org.tinygroup.tinypc.range.LongRangeSpliter;

import java.util.List;

/**
 * Created by luoguo on 14-3-3.
 */
public class RangeLongSpliterTest extends TestCase {
    public void testSplit() throws Exception {
        LongRangeSpliter spliter = new LongRangeSpliter();
        List<Range<Long>> list = spliter.split(1l, 100l, 10);
        for (Range range : list) {
            System.out.println(range.toString());
        }
    }

    public void testSplit1() throws Exception {
        LongRangeSpliter spliter = new LongRangeSpliter();
        List<Range<Long>> list = spliter.split(0l, 100l, 10);
        for (Range range : list) {
            System.out.println(range.toString());
        }
    }

    public void testSplit2() throws Exception {
        LongRangeSpliter spliter = new LongRangeSpliter();
        List<Range<Long>> list = spliter.split(-2l, 10l, 4);
        for (Range range : list) {
            System.out.println(range.toString());
        }
    }
}
