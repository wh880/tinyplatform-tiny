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
package org.tinygroup.tinydb.config;

import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.operator.DBOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaConfigContainer {
    // public static String UUID_KEY = "uuid";
    public final static String INCREASE_KEY = "increase"; // 主键类型--自增长
    /**
     * Map<schema,operatorBeanName>
     */
    private Map<String, String> schemaMap = new HashMap<String, String>();
    /**
     * Map<schema,keyType>
     */
    private Map<String, String> schemaKeyTypeMap = new HashMap<String, String>();
    /**
     * Map<schema,List<BeanName>>
     */
    private Map<String, List<String>> schemaBeanListMap = new HashMap<String, List<String>>();
    private List<String> schemaList = new ArrayList<String>();

    /**
     * Map<schema,tableNamePattern>
     */
    private Map<String, String> schemaTablePattern = new HashMap<String, String>();

    public String getKeyType(String schema) {
        return schemaKeyTypeMap.get(schema);
    }

    /**
     * 添加一个schema配置信息
     *
     * @param config
     */
    public void addSchemaConfig(SchemaConfig config) {
        String schema = config.getSchema();
        schemaMap.put(schema, config.getOperatorBeanName());
        String keyType = config.getKeyType();
        if (keyType == null || "".equals(keyType))
            keyType = INCREASE_KEY;
        schemaKeyTypeMap.put(schema, keyType);
        schemaTablePattern.put(schema, config.getTableNamePattern());
        if (!schemaList.contains(schema)) {
            schemaList.add(schema);
        }
    }

    /**
     * 根据schema和beanType获取一个operator
     *
     * @param schema
     * @param beanType
     * @return
     */
    public DBOperator<?> getDbOperator(String schema, String beanType) {
        DBOperator<?> operator = SpringUtil.getBean(schemaMap.get(schema));
        operator.setSchema(schema);
        operator.setBeanType(beanType);
        return operator;
    }

    /**
     * 获取schema及其下的bean列表的映射
     *
     * @return
     */
    public Map<String, List<String>> getSchemaBeanListMap() {
        return schemaBeanListMap;
    }

    /**
     * 获取当前所有的schema
     *
     * @return
     */
    public List<String> getSchemaList() {
        return schemaList;
    }

    /**
     * 获取schema对应的表格名过滤正则
     *
     * @param schema
     * @return
     */
    public String getTablePattern(String schema) {
        String pattern = schemaTablePattern.get(schema);
        if (pattern == null) {
            return null;
        }
        if (pattern.trim().equals("")) {
            pattern = null;
        }
        return pattern;
    }
}
