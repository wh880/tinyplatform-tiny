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

import org.springframework.jdbc.core.RowMapper;
import org.tinygroup.jdbctemplatedslsession.RowMapperHolder;
import org.tinygroup.jdbctemplatedslsession.RowMapperSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的RowMapper选择器
 * @author renhui
 *
 */
public class SimpleRowMapperSelector implements RowMapperSelector {
	
	private List<RowMapperHolder> holders=new ArrayList<RowMapperHolder>();
	
	public SimpleRowMapperSelector(){
		holders.add(new SingleColumnRowMapperHolder());
		holders.add(new BeanPropertyRowMapperHolder());
	}

	public RowMapper rowMapperSelector(Class requiredType) {
		for (RowMapperHolder holder : holders) {
			if(holder.isMatch(requiredType)){
				return holder.getRowMapper(requiredType);
			}
		}
	    throw new RuntimeException(String.format("类型:%s,获取不到相应的RowMapper实例", requiredType));
	}

	public List<RowMapperHolder> getHolders() {
		return holders;
	}

	public void setHolders(List<RowMapperHolder> holders) {
		this.holders = holders;
	}
	
}
