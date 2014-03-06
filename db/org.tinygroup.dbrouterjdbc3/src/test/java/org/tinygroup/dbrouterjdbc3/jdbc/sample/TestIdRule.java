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
package org.tinygroup.dbrouterjdbc3.jdbc.sample;

import org.tinygroup.dbrouter.config.Router;
import org.tinygroup.dbrouter.impl.shardrule.ShardRuleByIdDifferentSchema;

/**
 * Created by luoguo on 13-12-15.
 */
public class TestIdRule {
    public static void main(String[] args) {
        Router router = TestRouterUtil.getSameSchemaDiffrentTableRouter();
        ShardRuleByIdDifferentSchema rule = new ShardRuleByIdDifferentSchema("aaa", "id", 1);
        System.out.println(rule.isMatch(router.getPartitions().get(0),"insert into aaa(id,aaa) values(?,'ccc')", 1l));
        ShardRuleByIdDifferentSchema rule2 = new ShardRuleByIdDifferentSchema("aaa", "id", 2);
        System.out.println(rule2.isMatch(router.getPartitions().get(0),"insert into aaa(id,aaa) values(?,'ccc')", 2l));

    }
}
