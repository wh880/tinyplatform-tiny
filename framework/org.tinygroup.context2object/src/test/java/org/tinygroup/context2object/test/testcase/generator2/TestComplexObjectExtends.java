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
package org.tinygroup.context2object.test.testcase.generator2;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.bean.ComplexObjectChild;
import org.tinygroup.context2object.test.generator2.testcase.BaseTestCast2;

public class TestComplexObjectExtends extends BaseTestCast2{
	public void testObjectArray() {
		Context context = new ContextImpl();
		context.put("complexObjectChild.cat.name", "name1");
		ComplexObjectChild c = (ComplexObjectChild) generator.getObject(null,null, ComplexObjectChild.class.getName(),this.getClass().getClassLoader(), context);
		assertEquals(c.getCat().getName(), "name1");
		System.out.println(c.getCat().getName());
	}
}
