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
package org.tinygroup.vfs.util;

/**
 * An enumerated type, which represents an OS family.
 * 
 * @author <a href="http://commons.apache.org/vfs/team-list.html">Commons VFS
 *         team</a>
 */
public final class OsFamily {

	private final String name;
	private final OsFamily[] families;

	OsFamily(final String name) {
		this.name = name;
		families = new OsFamily[0];
	}

	OsFamily(final String name, final OsFamily[] families) {
		this.name = name;
		this.families = families;
	}

	/**
	 * Returns the name of this family.
	 * 
	 * @return The name of this family.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the OS families that this family belongs to.
	 * 
	 * @return an array of OSFamily objects that this family belongs to.
	 */
	public OsFamily[] getFamilies() {
		return families;
	}
}
