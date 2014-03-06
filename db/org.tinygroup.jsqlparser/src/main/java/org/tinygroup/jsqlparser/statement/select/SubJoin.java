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
package org.tinygroup.jsqlparser.statement.select;

/**
 * A table created by "(tab1 join tab2)".
 */
public class SubJoin implements FromItem {

    private FromItem left;
    private Join join;
    private String alias;
    private Pivot pivot;


    public void accept(FromItemVisitor fromItemVisitor) {
        fromItemVisitor.visit(this);
    }

    public FromItem getLeft() {
        return left;
    }

    public void setLeft(FromItem l) {
        left = l;
    }

    public Join getJoin() {
        return join;
    }

    public void setJoin(Join j) {
        join = j;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }


    public String getAlias() {
        return alias;
    }


    public void setAlias(String string) {
        alias = string;
    }


    public String toString() {
        return "(" + left + " " + join + ")" +
                ((pivot != null) ? " " + pivot : "") +
                ((alias != null) ? " AS " + alias : "");
    }
}
