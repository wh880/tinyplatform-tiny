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
package org.tinygroup.dbrouter.impl.shardrule;

import org.tinygroup.dbrouter.ShardRule;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by luoguo on 13-12-17.
 */
public abstract class ShardRuleByIdAbstract implements ShardRule {
    /**
     * 余数
     */
    @XStreamAsAttribute
    long remainder;
    /**
     * 表名
     */
    @XStreamAsAttribute
    @XStreamAlias("table-name")
    String tableName;
    /**
     * 主键字段
     */
    @XStreamAsAttribute
    @XStreamAlias("primary-key-field-name")
    String primaryKeyFieldName;

    public ShardRuleByIdAbstract() {

    }

    public ShardRuleByIdAbstract(String tableName, String primaryKeyFieldName, int remainder) {
        this.tableName = tableName;
        this.primaryKeyFieldName = primaryKeyFieldName;
        this.remainder = remainder;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKeyFieldName() {
        return primaryKeyFieldName;
    }

    public void setPrimaryKeyFieldName(String primaryKeyFieldName) {
        this.primaryKeyFieldName = primaryKeyFieldName;
    }

    public long getRemainder() {
        return remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }
}
