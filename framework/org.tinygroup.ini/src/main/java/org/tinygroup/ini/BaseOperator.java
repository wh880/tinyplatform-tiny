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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by luoguo on 14-3-28.
 */
interface BaseOperator {
    void setSections(Sections sections);

    Sections getSections();

    /**
     * 读入配置信息
     *
     * @return
     */
    void read(InputStream inputStream, String charset) throws IOException;

    /**
     * 写出配置文件
     *
     * @param outputStream
     */
    void write(OutputStream outputStream, String charset) throws IOException;

    Section getSection(String sectionName);

    void setCommentChar(char commitChar);
}
