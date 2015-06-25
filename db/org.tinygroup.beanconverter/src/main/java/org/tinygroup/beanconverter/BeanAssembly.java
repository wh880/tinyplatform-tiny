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
package org.tinygroup.beanconverter;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context2object.ObjectAssembly;
import org.tinygroup.database.util.DataSourceInfo;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.util.TinyDBUtil;

import java.util.List;

/**
 * bean对象的组装实现
 * 
 * @author renhui
 * 
 */
public class BeanAssembly implements ObjectAssembly<Bean> {

	private static final String SPLIT = ",";
	private static final String BEAN_TYPE_KEY = "@beantype";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BeanAssembly.class);

	public void assemble(String varName,Bean object, Context context) {
		String beanType = context.get(BEAN_TYPE_KEY);
		if (StringUtil.isBlank(beanType)) {
			LOGGER.errorMessage("未设置参数名称为@beantype的参数");
			throw new RuntimeException("未设置参数名称为@beantype的参数");
		}
		String[] types = beanType.split(SPLIT);
		String type=findType(varName,types);
		object.setType(type);
		String schema = DataSourceInfo.getDataSource();
		List<String> properties = TinyDBUtil.getBeanProperties(
				beanType, schema, this.getClass().getClassLoader());
		TinyDBUtil.context2Bean(context, object, properties);
	}

	private String findType(String varName,String[] types) {
		if(types.length==1){
			return types[0];
		}
        return varName;
	}

	public boolean isMatch(Class<?> type) {
		return Bean.class.equals(type);
	}
}
