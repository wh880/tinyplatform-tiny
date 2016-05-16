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
package org.tinygroup.flow.test.newtestcase.release;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.component.AbstractFlowComponent;
import org.tinygroup.flow.release.PageFlowReleaseManager;
import org.tinygroup.flow.release.config.PageFlowRelease;
import org.tinygroup.flow.release.config.ReleaseItem;
import org.tinygroup.tinyrunner.Runner;

public class ReleasePageFlowTest extends AbstractFlowComponent {


	protected void setUp() throws Exception {
		PageFlowReleaseManager.clear();
		PageFlowRelease flowRelease = new PageFlowRelease();
		ReleaseItem item = new ReleaseItem();
		List<String> flowNames = new ArrayList<String>();
		flowNames.add("pageflowreleaseFlow");
		item.setItems(flowNames);
		flowRelease.setIncludes(item);
		PageFlowReleaseManager.add(flowRelease);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		PageFlowReleaseManager.clear();
		Runner.initDirect("application.xml", new ArrayList<String>());
		super.setUp();
	}
	
	public void testInclude1() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		pageFlowExecutor.execute("pageflowreleaseFlow", context);
		assertEquals(3, Integer.valueOf(context.get("sum").toString()).intValue());
	}

}
