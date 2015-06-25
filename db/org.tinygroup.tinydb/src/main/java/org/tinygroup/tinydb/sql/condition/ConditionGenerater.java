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
package org.tinygroup.tinydb.sql.condition;

import java.util.List;


/**
 * 条件信息生成接口
 * @author renhui
 *
 */
public interface ConditionGenerater {

    /**
     * 根据字段名称、条件值生成比较条件信息
     *
     * @param columnName
     * @return
     */
    String generateCondition(String columnName);
    /**
     * 对原有的参数值进行处理，返回处理后的参数信息
     */
    void paramValueProcess(List<Object> params);
    
    /**
     * 获取条件的名称
     *
     * @return
     */
    String getConditionMode(); 
    
    /**
     * 条件比较操作对应的值
     * @param value
     */
    void setValue(Object value);
}
