/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p/>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.gnu.org/licenses/gpl.html
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.template.rumtime.operator;

import java.math.BigDecimal;

/**
 * Created by luoguo on 2014/6/5.
 */
public class AddOperator extends TwoConvertOperator {


    protected Object operation(Object left, Object right) {
        if (isType(left, Byte.class) && isType(right, Byte.class)) {
            return (Byte) left + (Byte) right;
        }
        if (isType(left, Character.class) && isType(right, Character.class)) {
            return (Character) left + (Character) right;
        }
        if (isType(left, Integer.class) && isType(right, Integer.class)) {
            return (Integer) left + (Integer) right;
        }
        if (isType(left, Float.class) && isType(right, Float.class)) {
            return (Float) left + (Float) right;
        }
        if (isType(left, Double.class) && isType(right, Double.class)) {
            return (Double) left + (Double) right;
        }
        if (isType(left, BigDecimal.class) && isType(right, BigDecimal.class)) {
            BigDecimal b1 = (BigDecimal) left;
            BigDecimal b2 = (BigDecimal) right;
            return b1.add(b2);
        }
        return (left == null ? left.toString() : "") + (right == null ? right.toString() : "");
    }


    public String getOperation() {
        return "+";
    }

}
