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

import java.util.ArrayList;
import java.util.List;

/**
 * INI的Sections，可以包含多个段
 * Created by luoguo on 14-3-28.
 */
public class Sections {
    List<Section> sectionList;

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public void addSection(Section section) {
        if (sectionList == null) {
            sectionList = new ArrayList<Section>();
        }
        sectionList.add(section);
    }

    public Section getSection(String sectionName) {
        if (sectionList != null) {
            for (Section section : sectionList) {
                if (section.getName() == null || sectionName == null) {
                    if (section.getName() == sectionName) {
                        return section;
                    }
                } else if (sectionName != null && section.getName().equals(sectionName)) {
                    return section;
                }
            }
        }
        return null;
    }
}
