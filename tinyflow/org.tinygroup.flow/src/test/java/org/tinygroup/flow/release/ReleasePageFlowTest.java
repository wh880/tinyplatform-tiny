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
package org.tinygroup.flow.release;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.component.AbstractFlowComponent;
import org.tinygroup.flow.release.config.PageFlowRelease;
import org.tinygroup.flow.release.config.ReleaseItem;

public class ReleasePageFlowTest extends AbstractFlowComponent {


	protected void setUp() throws Exception {
		PageFlowReleaseManager.clear();
		PageFlowRelease flowRelease = new PageFlowRelease();
		ReleaseItem item = new ReleaseItem();
		List<String> flowNames = new ArrayList<String>();
		flowNames.add("pageflowrelease1");
		item.setItems(flowNames);
		flowRelease.setIncludes(item);
		PageFlowReleaseManager.add(flowRelease);
		super.setUp();
	}

	public void testInclude1() {
		Context context = new ContextImpl();
		try {
			pageFlowExecutor.execute("pageflowrelease1", "begin", context);
			assertEquals("Hello, luoguo", context.get("helloInfo"));
		} catch (Exception e) {
			assertTrue(false);
		}
		
		
	}

}
