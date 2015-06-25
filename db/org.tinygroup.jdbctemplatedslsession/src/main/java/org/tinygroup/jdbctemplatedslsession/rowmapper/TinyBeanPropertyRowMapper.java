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
package org.tinygroup.jdbctemplatedslsession.rowmapper;

import org.springframework.beans.BeanWrapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.tinygroup.jdbctemplatedslsession.editor.AllowNullNumberEditor;

/**
 * 
 * @author renhui
 * 
 */
public class TinyBeanPropertyRowMapper extends BeanPropertyRowMapper {

	public TinyBeanPropertyRowMapper(Class requiredType) {
		super(requiredType);
	}

	@Override
	protected void initBeanWrapper(BeanWrapper bw) {
		bw.registerCustomEditor(byte.class, new AllowNullNumberEditor(
				Byte.class, true));
		bw.registerCustomEditor(short.class, new AllowNullNumberEditor(
				Short.class, true));
		bw.registerCustomEditor(int.class, new AllowNullNumberEditor(
				Integer.class, true));
		bw.registerCustomEditor(long.class, new AllowNullNumberEditor(
				Long.class, true));
		bw.registerCustomEditor(float.class, new AllowNullNumberEditor(
				Float.class, true));
		bw.registerCustomEditor(double.class, new AllowNullNumberEditor(
				Double.class, true));
	}

}
