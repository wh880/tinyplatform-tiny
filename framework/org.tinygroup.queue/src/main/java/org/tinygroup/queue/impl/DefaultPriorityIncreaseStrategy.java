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
package org.tinygroup.queue.impl;

import org.tinygroup.queue.PriorityIncreaseStrategy;
import org.tinygroup.queue.PriorityQueue;

/**
 * Created by IntelliJ IDEA. User: luoguo Date: 11-3-31 Time: 上午8:54 To change
 * this template use File | Settings | File Templates.
 */
public class DefaultPriorityIncreaseStrategy<E> implements
		PriorityIncreaseStrategy<E> {
	// 什么时候进行优先级提升，默认设定调用次数是阶列大小的时候，如果要调整提升策略，就可以修改此段代码
	public void increasePriority(PriorityQueue<E> queue) {
		PriorityQueueImpl<E> priorityQueue = (PriorityQueueImpl<E>) queue;
		if (priorityQueue.getCallTimes() == priorityQueue.getSize()) {
			synchronized (priorityQueue.dateQueueListArray) {
				for (int i = priorityQueue.getReverseLevel(); i < priorityQueue.dateQueueListArray.length - 1; i++) {
					priorityQueue.dateQueueListArray[i]
							.addAll(priorityQueue.dateQueueListArray[i + 1]);
				}
			}
		}
	}

}