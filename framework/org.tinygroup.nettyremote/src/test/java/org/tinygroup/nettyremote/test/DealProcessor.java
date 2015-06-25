/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.nettyremote.test;

import org.tinygroup.nettyremote.impl.ClientImpl;
import org.tinygroup.threadgroup.AbstractProcessor;

public class DealProcessor extends AbstractProcessor {
	int count;
	ClientImpl c;

	public DealProcessor(ClientImpl c, String name, int count) {
		super(name);
		this.count = count;
		this.c = c;
	}

	protected void action() throws Exception {
		for (int i = 0; i < count; i++) {
			c.write(getName() + i);
		}
	}

}
