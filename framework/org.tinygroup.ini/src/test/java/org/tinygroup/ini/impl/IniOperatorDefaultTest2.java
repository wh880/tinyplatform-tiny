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
package org.tinygroup.ini.impl;

import junit.framework.TestCase;
import org.tinygroup.ini.IniOperator;
import org.tinygroup.ini.Sections;
import org.tinygroup.ini.ValuePair;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoguo on 14-3-29.
 */
public class IniOperatorDefaultTest2 extends TestCase {
	IniOperator operator;

	public void setUp() throws Exception {
		super.setUp();
		operator = new IniOperatorDefault();
		Sections sections = new Sections();
		operator.setSections(sections);
	}

	public void testGetSections() throws Exception {
		assertNotNull(operator.getSections());
	}

	public void testInt() throws Exception {
		operator.put("aa", "abc", 456);
		assertEquals(true,456==operator.get(int.class, "aa", "abc", 1233));
	}
	
	public void testInteger() throws Exception {
		operator.put("aa", "abc", 456);
		assertEquals(true,456==operator.get(Integer.class, "aa", "abc", 1233));
	}
	
	public void testBoolean() throws Exception {
		operator.put("aa", "abc", "true");
		assertEquals(true,Boolean.TRUE==operator.get(boolean.class, "aa", "abc", false));
	}
	
	

}
