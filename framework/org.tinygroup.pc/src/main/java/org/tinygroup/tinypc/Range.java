/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.tinypc;

/**
 * 数的范围，用于记录整数和长整数的范围
 * Created by luoguo on 14-3-3.
 */
public class Range<T> {
    /**
     * 起始数
     */
    private T start;
    /**
     * 结束数
     */
    private T end;

    public Range() {

    }

    public Range(T start, T end) {
        this.start = start;
        this.end = end;
    }

    public void setStart(T start) {
        this.start = start;
    }

    public void setEnd(T end) {
        this.end = end;
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    public String toString() {
        return String.format("start:%s,end:%s", start, end);
    }
}