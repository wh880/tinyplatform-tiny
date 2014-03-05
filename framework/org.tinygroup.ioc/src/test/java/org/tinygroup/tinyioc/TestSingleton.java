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
package org.tinygroup.tinyioc;

import org.tinygroup.tinyioc.impl.BeanContainerImpl;

/**
 * Created by luoguo on 13-12-27.
 */
public class TestSingleton {
    public static void main(String[] args) {
        BeanContainer container = new BeanContainerImpl();
        container.registerClass(Pet1.class);
        Pet pet = container.getBeanByName("pet1");
        Pet pet1 = container.getBeanByName("pet1");
        System.out.println(pet == pet1);
        System.out.println(pet.getClass().getName());
    }
}
