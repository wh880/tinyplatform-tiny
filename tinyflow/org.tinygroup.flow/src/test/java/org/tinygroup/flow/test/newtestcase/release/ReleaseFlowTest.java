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
import org.tinygroup.flow.release.FlowReleaseManager;
import org.tinygroup.flow.release.config.FlowRelease;
import org.tinygroup.flow.release.config.ReleaseItem;

public class ReleaseFlowTest extends AbstractFlowComponent {


	protected void setUp() throws Exception {
		FlowReleaseManager.clear();
		FlowRelease flowRelease = new FlowRelease();
		ReleaseItem item = new ReleaseItem();
		List<String> flowNames = new ArrayList<String>();
		flowNames.add("releaseFlow");
		item.setItems(flowNames);
		flowRelease.setIncludes(item);
		FlowReleaseManager.add(flowRelease);
		super.setUp();
	}

	public void testInclude1() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		flowExecutor.execute("releaseFlow", "begin", context);
		assertEquals(3, Integer.valueOf(context.get("sum").toString()).intValue());
	}

}
