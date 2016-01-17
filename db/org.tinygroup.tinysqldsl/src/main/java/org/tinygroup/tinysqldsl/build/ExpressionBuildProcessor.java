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
package org.tinygroup.tinysqldsl.build;

import org.tinygroup.tinysqldsl.base.StatementSqlBuilder;

/**
 * sql拼接处理与参数组装
 * 
 * @author renhui
 * 
 */
public interface ExpressionBuildProcessor {

	/**
	 * 实现接口的sql片段通过builder.appendSql(String
	 * segment)进行拼接,也可以builder.getStringBuilder方法获取StringBuilder,然后进行append
	 * 实现的参数信息通过builder.addParamValue(Object... values)进行参数组装
	 * 
	 * @param builder
	 */
	void builderExpression(StatementSqlBuilder builder);

}
