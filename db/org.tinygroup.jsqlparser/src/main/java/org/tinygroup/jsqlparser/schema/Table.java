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
package org.tinygroup.jsqlparser.schema;

import org.tinygroup.jsqlparser.statement.select.FromItem;
import org.tinygroup.jsqlparser.statement.select.FromItemVisitor;
import org.tinygroup.jsqlparser.statement.select.IntoTableVisitor;
import org.tinygroup.jsqlparser.statement.select.Pivot;

/**
 * A table. It can have an alias and the schema name it belongs to.
 */
public class Table implements FromItem {

    private String schemaName;
    private String name;
    private String alias;
    private Pivot pivot;

    public Table() {
    }

    public Table(String schemaName, String name) {
        this.schemaName = schemaName;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setName(String string) {
        name = string;
    }

    public void setSchemaName(String string) {
        schemaName = string;
    }


    public String getAlias() {
        return alias;
    }


    public void setAlias(String string) {
        alias = string;
    }

    public String getWholeTableName() {

        String tableWholeName = null;
        if (name == null) {
            return null;
        }
        if (schemaName != null) {
            tableWholeName = schemaName + "." + name;
        } else {
            tableWholeName = name;
        }

        return tableWholeName;

    }


    public void accept(FromItemVisitor fromItemVisitor) {
        fromItemVisitor.visit(this);
    }

    public void accept(IntoTableVisitor intoTableVisitor) {
        intoTableVisitor.visit(this);
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }


    public String toString() {
        return getWholeTableName() +
                ((pivot != null) ? " " + pivot : "") +
                ((alias != null) ? " AS " + alias : "");
    }
}
