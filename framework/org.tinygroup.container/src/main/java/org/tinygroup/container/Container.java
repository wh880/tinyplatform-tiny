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
package org.tinygroup.container;

import java.util.Comparator;
import java.util.List;

/**
 * 容器<br>
 * 用于放置一些列表数据
 * 
 * @author luoguo
 * 
 * @param <K>
 */
public interface Container<K extends Comparable<K>, T extends BaseObject<K>>
		extends BaseObject<K> {
	List<T> getList();// 返回原始排序列表

	void setList(List<T> list);// 设置包含的内容

	List<T> getList(Comparator<T> comparator);// 返回经过排序的对象列表

	boolean contains(T object);// 返回是否包含某对象

}
