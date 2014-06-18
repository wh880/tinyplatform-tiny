/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
 */
package org.tinygroup.template.rumtime.operator;

import java.util.AbstractList;


public class RangeList extends AbstractList<Integer> {
    private final int start;
    private final int step;
    private final int stop;
    private int current;
    private int size = 0;

    public RangeList(int start, int stop, int step) {
        this.start = start;
        this.stop = stop;
        this.step = step;

        this.current = start;
        size = (start >= stop ? start - stop : stop - start) + 1;
    }


    @Override
    public Integer get(int index) {
        return start + index * step;
    }

    @Override
    public int size() {
        return size;
    }
}