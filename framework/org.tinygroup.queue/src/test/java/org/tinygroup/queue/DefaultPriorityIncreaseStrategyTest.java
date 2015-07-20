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
