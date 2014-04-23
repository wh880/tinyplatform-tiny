/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
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
