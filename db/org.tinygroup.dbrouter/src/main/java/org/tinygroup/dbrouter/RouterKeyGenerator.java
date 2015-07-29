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
package org.tinygroup.dbrouter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.tinygroup.dbrouter.config.KeyTables;
import org.tinygroup.dbrouter.config.Router;

/**
 * 分布式Key获取器
 *
 * @param <T>
 * @author luoguo
 */
@XStreamAlias("key-generator")
public interface RouterKeyGenerator<T> {
    /**
     * 返回指定表的新主键
     *
     * @param tableName
     * @return
     */
    T getKey(String tableName);

    /**
     * 注入Router对象给主键获取器
     * 有的时候，主键发生器需要获取集群相关的信息
     *
     * @param router
     */
    void setRouter(Router router);
    
    /**
     * 是否能动态创建数据库存储表
     * @return
     */
    boolean isAutoCreate();
    
    /**
     * 创建动态数据库存储表
     */
    void createKeyTable(KeyTables keyTables);
}
