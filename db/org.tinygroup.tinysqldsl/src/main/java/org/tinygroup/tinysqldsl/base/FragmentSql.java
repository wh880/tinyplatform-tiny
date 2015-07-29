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
package org.tinygroup.tinysqldsl.base;

import org.tinygroup.tinysqldsl.expression.FragmentExpressionSql;
import org.tinygroup.tinysqldsl.formitem.FragmentFromItemSql;
import org.tinygroup.tinysqldsl.selectitem.FragmentSelectItemSql;

/**
 * 保存sql片段的对象
 * @author renhui
 *
 */
public class FragmentSql {
    /**
     * sql片段
     */
    private String fragment;

    public FragmentSql(String fragment) {
        super();
        this.fragment = fragment;
    }

    public String getFragment() {
        return fragment;
    }

    public static FragmentSelectItemSql fragmentSelect(String fragment) {
        return new FragmentSelectItemSql(fragment);
    }

    public static FragmentFromItemSql fragmentFrom(String fragment) {
        return new FragmentFromItemSql(fragment);
    }

    public static Condition fragmentCondition(String fragment, Object... values) {
        FragmentExpressionSql fragmentExpressionSql = new FragmentExpressionSql(fragment);
        return new Condition(fragmentExpressionSql, values);
    }

    @Override
    public String toString() {
        return fragment;
    }
}
