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

import junit.framework.TestCase;
import org.tinygroup.binarytree.impl.AVLTreeImpl;

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

}
