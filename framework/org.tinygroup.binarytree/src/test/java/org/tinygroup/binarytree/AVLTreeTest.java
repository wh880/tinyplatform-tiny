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
package org.tinygroup.binarytree;

import org.tinygroup.binarytree.impl.AVLTreeImpl;

import junit.framework.TestCase;

public class AVLTreeTest extends TestCase {

	AVLTree<Integer> binTree;

	
	protected void setUp() throws Exception {
		super.setUp();
		binTree = new AVLTreeImpl<Integer>();
	}

	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testHeight() {
		binTree.add(9);
		binTree.add(8);
		binTree.add(7);
		binTree.add(6);
		assertEquals(2, binTree.height());
	}

	public void testHeight1() {
		binTree.add(9);
		binTree.add(9);
		assertEquals(0, binTree.height());
	}
	
	public void testRemove() {
		binTree.add(9);
		binTree.add(5);
		binTree.add(3);
		binTree.remove(3);
		assertFalse(binTree.remove(99));
//		assertEquals(-1, binTree.height());
	}
	
	public void testRemove1() {
		binTree.add(9);
		binTree.add(8);
		binTree.add(7);
		binTree.remove(7);
	}
	
	public void testRemove2() {
		binTree.add(9);
		binTree.add(8);
		binTree.add(7);
		binTree.remove(8);
	}
	
	public void testRemove3() {
		binTree.add(9);
		binTree.add(5);
		binTree.add(3);
		binTree.remove(9);
	}
	
	public void testRemove4() {
		binTree.add(9);
		binTree.add(15);
		binTree.add(5);
		binTree.remove(15);
	}
	
	public void testRemove5() {
		binTree.add(9);
		binTree.add(15);
		binTree.add(5);
		binTree.remove(5);
	}
	
	public void testRemove6() {
		binTree.add(19);
		binTree.add(15);
		binTree.add(5);
		binTree.remove(19);
	}
	
	public void testContains(){
		binTree.add(9);
		assertEquals(new Integer(9), binTree.contains(9));
		assertEquals(null, binTree.contains(8));
	}
	
}
