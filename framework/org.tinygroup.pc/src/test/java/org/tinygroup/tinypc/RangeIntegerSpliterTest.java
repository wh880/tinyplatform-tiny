package org.tinygroup.tinypc;

import junit.framework.TestCase;
import org.tinygroup.tinypc.range.IntegerRangeSpliter;

import java.util.List;

/**
 * Created by luoguo on 14-3-3.
 */
public class RangeIntegerSpliterTest extends TestCase {
    public void testSplit() throws Exception {
        IntegerRangeSpliter spliter = new IntegerRangeSpliter();
        List<Range<Integer>> list = spliter.split(1, 100, 10);
        for (Range range : list) {
            System.out.println(range.toString());
        }
    }
    public void testSplit1() throws Exception {
        IntegerRangeSpliter spliter = new IntegerRangeSpliter();
        List<Range<Integer>> list = spliter.split(0, 100, 10);
        for (Range range : list) {
            System.out.println(range.toString());
        }
    }
    public void testSplit2() throws Exception {
        IntegerRangeSpliter spliter = new IntegerRangeSpliter();
        List<Range<Integer>> list = spliter.split(-2, 10, 4);
        for (Range range : list) {
            System.out.println(range.toString());
        }
    }
}
