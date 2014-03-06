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

import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.ExpressionVisitor;

/**
 * A column. It can have the table name it belongs to.
 */
public class Column implements Expression {

    private String columnName = "";
    private Table table;

    public Column() {
    }

    public Column(Table table, String columnName) {
        this.table = table;
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public Table getTable() {
        return table;
    }

    public void setColumnName(String string) {
        columnName = string;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * @return the name of the column, prefixed with 'tableName' and '.'
     */
    public String getWholeColumnName() {

        String columnWholeName = null;
        String tableWholeName = table.getWholeTableName();

        if (tableWholeName != null && tableWholeName.length() != 0) {
            columnWholeName = tableWholeName + "." + columnName;
        } else {
            columnWholeName = columnName;
        }

        return columnWholeName;

    }


    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }


    public String toString() {
        return getWholeColumnName();
    }
}
