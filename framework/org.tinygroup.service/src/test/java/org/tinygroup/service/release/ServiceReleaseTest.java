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
package org.tinygroup.service.release;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.service.release.config.ReleaseItem;
import org.tinygroup.service.release.config.ServiceRelease;
import org.tinygroup.service.util.ServiceTestUtil;

public class ServiceReleaseTest extends TestCase {
	
	/**
	 * 黑名单
	 */
	public void testExcludes() {
		ServiceRelease release = new ServiceRelease();
		ReleaseItem items = new ReleaseItem();
		List<String> excludes = new ArrayList<String>();
		items.setItems(excludes);
		release.setExcludes(items);
		//黑名单
		excludes.add("release4Exclude");
		ServiceReleaseManager.add(release);
		
		Context context = new ContextImpl();
		try {
			ServiceTestUtil.execute("release4Exclude", context);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ServiceTestUtil.execute("release4Include", context);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * 白名单
	 */
	public void testIncludes() {
		ServiceRelease release = new ServiceRelease();
		ReleaseItem items = new ReleaseItem();
		List<String> includes = new ArrayList<String>();
		items.setItems(includes);
		release.setIncludes(items);
		//白名单
		includes.add("release4Include");
		ServiceReleaseManager.add(release);
		
		Context context = new ContextImpl();
		try {
			ServiceTestUtil.execute("release4Include", context);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			ServiceTestUtil.execute("release4Exclude", context);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 黑白名单同时存在
	 */
	public void testIncludesExclude() {
		ServiceRelease release = new ServiceRelease();
		ReleaseItem excludeItems = new ReleaseItem();
		ReleaseItem includeItems = new ReleaseItem();
		List<String> excludes = new ArrayList<String>();
		List<String> includes = new ArrayList<String>();
		excludeItems.setItems(excludes);
		includeItems.setItems(includes);
		release.setExcludes(excludeItems);
		release.setIncludes(includeItems);
		//白名单
		excludes.add("release4Exclude");
		includes.add("release4Include");
		ServiceReleaseManager.add(release);
		
		Context context = new ContextImpl();
		try {
			ServiceTestUtil.execute("release4Exclude", context);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ServiceTestUtil.execute("release4Include", context);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
