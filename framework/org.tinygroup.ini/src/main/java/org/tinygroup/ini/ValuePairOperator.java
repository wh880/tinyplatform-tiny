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
 *  鐗堟潈 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  鏈紑婧愯蒋浠堕伒寰� GPL 3.0 鍗忚;
 *  濡傛灉鎮ㄤ笉閬靛惊姝ゅ崗璁紝鍒欎笉琚厑璁镐娇鐢ㄦ鏂囦欢銆�
 *  浣犲彲浠ヤ粠涓嬮潰鐨勫湴鍧�鑾峰彇瀹屾暣鐨勫崗璁枃鏈�
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.ini;

import java.util.List;

/**
 * Created by luoguo on 14-3-28.
 */
interface ValuePairOperator {
    /**
     * 添加值对
     *
     * @param sectionName
     * @param valuePair
     */
    void add(String sectionName, ValuePair valuePair);

    /**
     * 如果有valuePair同名的key，则会替换
     *
     * @param sectionName
     * @param valuePair
     */
    void set(String sectionName, ValuePair valuePair);

    /**
     * 添加所有的值对
     *
     * @param sectionName
     * @param valuePairList
     */
    void add(String sectionName, List<ValuePair> valuePairList);

    /**
     * 返回指定键的值对
     *
     * @param sectionName
     * @param key
     * @return
     */
    List<ValuePair> getValuePairList(String sectionName, String key);

    /**
     * 如果有多个，则会返回第一个
     *
     * @param sectionName
     * @param key
     * @return
     */
    ValuePair getValuePair(String sectionName, String key);
}
