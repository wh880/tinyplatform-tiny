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
package org.tinygroup.exception.dao;

import org.apache.commons.lang.RandomStringUtils;
import org.tinygroup.exception.constant.Constant;
import org.tinygroup.exception.pojo.Custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomDaoImpl implements CustomDao {

	private Map<String, Custom> customs = new HashMap<String, Custom>();

	public Custom insertCustom(Custom custom) {
		String id = RandomStringUtils.random(5, true, true);
		if (customs.containsKey(id)) {
			throw new DaoException(Constant.PRIMARY_KEY_CONFLICT);
		}
		custom.setId(id);
		customs.put(id, custom);
		return custom;
	}

	public int updateCustom(Custom custom) {
		if (customs.containsKey(custom.getId())) {
			customs.put(custom.getId(), custom);
			return 1;
		}
		throw new DaoException(Constant.RECORD_NOT_EXIST);
	}

	public int deleteCustom(String id) {
		if (customs.containsKey(id)) {
			customs.remove(id);
			return 1;
		}
		throw new DaoException(Constant.RECORD_NOT_EXIST);
	}

	public Custom getCustomById(String id) {
		if (customs.containsKey(id)) {
			return customs.get(id);
		}
		throw new DaoException(Constant.RECORD_NOT_EXIST);
	}

	public List<Custom> queryCustom(Custom queryCustom) {
		List<Custom> customlList = new ArrayList<Custom>();
		for (Custom custom : customs.values()) {
			if (custom.getAge() == queryCustom.getAge()
					&& custom.getName().equals(queryCustom.getAge())) {
				customlList.add(custom);
			}
		}
		return customlList;
	}

}
