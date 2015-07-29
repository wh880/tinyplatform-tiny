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
package org.tinygroup.jdbctemplatedslsession.keygenerator;

import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.tinysqldsl.KeyGenerator;
import org.tinygroup.tinysqldsl.base.InsertContext;

/**
 * 
 * @author renhui
 *
 */
public class AppKeyGenerator implements KeyGenerator {
	
	private DataFieldMaxValueIncrementer incrementer;
	public AppKeyGenerator(DataFieldMaxValueIncrementer incrementer) {
		super();
		this.incrementer = incrementer;
	}

	@SuppressWarnings("unchecked")
	public <T> T generate(InsertContext insertContext) {
		Assert.assertNotNull(incrementer, "AppKeyGenerator require incrementer can not be null");
		return (T) incrementer.nextStringValue();
	}
	
}
