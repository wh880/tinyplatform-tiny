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

package org.tinygroup.jspengine.util;

import java.util.Vector;

/**
 * A simple FIFO queue class which causes the calling thread to wait
 * if the queue is empty and notifies threads that are waiting when it
 * is not empty.
 *
 * @author Anil V (akv@eng.sun.com)
 */
public class Queue {
    private Vector vector = new Vector();

    /** 
     * Put the object into the queue.
     * 
     * @param	object		the object to be appended to the
     * 				queue. 
     */
    public synchronized void put(Object object) {
	vector.addElement(object);
	notify();
    }
    
    /**
     * Pull the first object out of the queue. Wait if the queue is
     * empty.
     */
    public synchronized Object pull() {
	while (isEmpty())
	    try {
		wait();
	    } catch (InterruptedException ex) {
	    }
	return get();
    }

    /**
     * Get the first object out of the queue. Return null if the queue
     * is empty. 
     */
    public synchronized Object get() {
	Object object = peek();
	if (object != null)
	    vector.removeElementAt(0);
	return object;
    }

    /**
     * Peek to see if something is available.
     */
    public Object peek() {
	if (isEmpty())
	    return null;
	return vector.elementAt(0);
    }
    
    /**
     * Is the queue empty?
     */
    public boolean isEmpty() {
	return vector.isEmpty();
    }

    /**
     * How many elements are there in this queue?
     */
    public int size() {
	return vector.size();
    }
}