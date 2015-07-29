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
package org.tinygroup.tinydb.sql.impl;

import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.Configuration;
import org.tinygroup.tinydb.config.BeanQueryConfig;
import org.tinygroup.tinydb.exception.TinyDbException;
import org.tinygroup.tinydb.sql.SqlAndValues;
import org.tinygroup.tinydb.sql.StatementTransform;

/**
 * 把bean对象转换成对应的sql语句
 * 
 * @author renhui
 * 
 */
public class StatementTransformComposite extends StatementTransformAdapter
		implements StatementTransform {

	private DefaultStatementTransform statementTransform = new DefaultStatementTransform();

	public StatementTransformComposite() {
		super();
	}

	public StatementTransformComposite(Configuration configuration) {
		super(configuration);
		statementTransform = new DefaultStatementTransform(configuration);
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);
		statementTransform.setConfiguration(configuration);
	}

	@Override
	public void setSchema(String schema) {
		super.setSchema(schema);
		statementTransform.setSchema(getSchema());
	}

	public SqlAndValues toSelect(Bean bean) throws TinyDbException {
		BeanQueryConfig beanQueryConfig = configuration.getBeanQueryConfig(bean
				.getType());
		if (beanQueryConfig == null) {
			return statementTransform.toSelect(bean);
		}
		BeanQueryConfigStatementTransform statementTransform = new BeanQueryConfigStatementTransform(
				configuration, beanQueryConfig);
		statementTransform.setSchema(getSchema());
		return statementTransform.toSelect(bean);
	}

	public String toInsert(Bean bean) throws TinyDbException {
		return statementTransform.toInsert(bean);
	}

	public String toDelete(Bean bean) throws TinyDbException {
		return statementTransform.toDelete(bean);
	}

	public String toUpdate(Bean bean) throws TinyDbException {
		return statementTransform.toUpdate(bean);
	}

}
