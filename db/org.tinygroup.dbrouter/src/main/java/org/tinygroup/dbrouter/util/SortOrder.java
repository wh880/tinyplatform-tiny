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
package org.tinygroup.dbrouter.util;

import org.tinygroup.dbrouter.util.OrderByProcessor.OrderByValues;

import java.util.Comparator;

/**
 * 功能说明: 对象排序比较器
 * 开发人员: renhui <br>
 * 开发时间: 2013-12-19 <br>
 * <br>
 */
public class SortOrder implements Comparator<OrderByValues> {

    /**
     * 排序方式列表
     */
    private boolean[] orderTypes;//true表示升序,false为降序
    /**
     * 排序索引号列表
     */
    private int[] orderIndexes;


    public SortOrder(boolean[] orderTypes, int[] orderIndexes) {
        this.orderTypes = orderTypes;
        this.orderIndexes = orderIndexes;
    }

    public boolean[] getOrderTypes() {
        return orderTypes;
    }


    public void setOrderTypes(boolean[] orderTypes) {
        this.orderTypes = orderTypes;
    }

    /**
     * null值在升序时排在最后
     */
    public int compare(OrderByValues o1, OrderByValues o2) {
        for (int i = 0; i < orderTypes.length; i++) {
            boolean orderType = orderTypes[i];
            int index = orderIndexes[i];
            Object value1 = o1.getValues()[index];
            Object value2 = o2.getValues()[index];
            boolean aNull = (value1 == null), bNull = (value2 == null);
            if (aNull || bNull) {
                if (aNull == bNull) {
                    continue;
                }
                if (aNull) {
                    return orderType ? 1 : -1;
                } else {
                    return orderType ? -1 : 1;
                }
            }
            if (value1 instanceof Comparable) {
                Comparable comparable = (Comparable) value1;
                int compareValue = comparable.compareTo(value2);
                if (compareValue != 0) {
                    return orderType ? compareValue : -compareValue;
                }
            }
        }
        return 0;
    }
}
