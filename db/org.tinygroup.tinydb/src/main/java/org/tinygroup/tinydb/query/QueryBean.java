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
package org.tinygroup.tinydb.query;

import java.util.List;

/**
 * 查询条件描述接口
 *
 * @author luoguo
 */
public interface QueryBean {
    String AND = "and";
    String OR = "or";

    /**
     * 是否有值，有的语句是没有值的，比如：is null;
     *
     * @return
     */
    boolean hasValue();

    String getPropertyName();

    /**
     * 返回值
     *
     * @return
     */
    <T> T getValue();

    /**
     * 返回查询子句
     *
     * @return
     */
    String getQueryClause();

    /**
     * 获取子查询
     *
     * @return
     */
    List<QueryBean> getQueryBeanList();

    /**
     * 添加下级查询描述
     *
     * @param queryBean
     */
    void addQueryBean(QueryBean queryBean);

    /**
     * 设置连接方式
     *
     * @param connectMode
     */
    void setConnectMode(String connectMode);

    /**
     * 返回连接方式
     *
     * @return
     */
    String getConnectMode();

}
