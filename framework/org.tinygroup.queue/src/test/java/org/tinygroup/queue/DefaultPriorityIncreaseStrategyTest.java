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
package org.tinygroup.queue;

import junit.framework.TestCase;

import org.tinygroup.queue.impl.PriorityQueueImpl;

public class DefaultPriorityIncreaseStrategyTest extends TestCase {

	PriorityQueueImpl<Integer> queue = null;

	protected void setUp() throws Exception {
		super.setUp();
		queue = new PriorityQueueImpl<Integer>(16);
	}
	
	public void test1() {
		
		for(int i =1; i <= 16; i++) {
			if(i < 10) {
				queue.offer(i, i);
			} else {
				queue.offer(i, 10);
			}
		}
	
		for(int i =1; i <= 15; i++) {
			assertEquals(i, queue.poll().intValue());
		}
		queue.offer(17, 10);
		queue.offer(18, 10);
		assertEquals(16, queue.poll().intValue());
		queue.offer(19, 10);
		queue.offer(20, 10);
		assertEquals(17, queue.poll().intValue());
		assertEquals(18, queue.poll().intValue());
		assertEquals(19, queue.poll().intValue());
		assertEquals(20, queue.poll().intValue());
		//queue.offer(8, 8);
		//System.out.println(queue.poll());
	}
	
	
}
