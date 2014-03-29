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
interface ValueOperator {
    /**
     * 设置值，如果有同名的，则会被替换
     *
     * @param sectionName
     * @param key
     * @param value
     * @param <T>
     */
    <T> void put(String sectionName, String key, T value);

    /**
     * 添加值，不关心是否有同名的
     *
     * @param sectionName
     * @param key
     * @param value
     * @param <T>
     */
    <T> void add(String sectionName, String key, T value);

    /**
     * 获取值，如果读不到值，则取默认值
     * 如果有多个，则只取第一个
     *
     * @param sectionName
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
     */
    <T> T get(Class<T> tClass, String sectionName, String key, T defaultValue);

    String get(String sectionName, String key, String defaultValue);

    String get(String sectionName, String key);

    /**
     * 返回指定键值列表
     *
     * @param sectionName
     * @param key
     * @param <T>
     * @return
     */
    <T> List<T> getList(Class<T> tClass, String sectionName, String key);

    /**
     * 读取值，如果有多个，则返回第一个
     *
     * @param sectionName
     * @param key
     * @param <T>
     * @return
     */
    <T> T get(Class<T> tClass, String sectionName, String key);

}
