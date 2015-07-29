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
package org.tinygroup.tinysqldsl;

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.Delete.and;
import static org.tinygroup.tinysqldsl.Delete.delete;

/**
 * Created by luoguo on 2015/3/11.
 */
public class TestDelete {
    public static void main(String[] args) {
        System.out.println(delete(CUSTOM).where(CUSTOM.NAME.eq("悠然")));
        System.out.println(delete(CUSTOM).where(
                and(CUSTOM.NAME.leftLike("A"), CUSTOM.AGE.between(20, 30))));
    }
}
