/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.tinysqldsl.base;

import org.tinygroup.tinysqldsl.expression.Expression;
import org.tinygroup.tinysqldsl.expression.JdbcNamedParameter;

public class NamedCondition implements Expression {

    private JdbcNamedParameter namedParameter;

    private Object value;

    public NamedCondition(String name, Object value) {
        this.namedParameter = new JdbcNamedParameter(name);
        this.value = value;
    }

    public NamedCondition(JdbcNamedParameter namedParameter, Object value) {
        super();
        this.namedParameter = namedParameter;
        this.value = value;
    }

    public JdbcNamedParameter getNamedParameter() {
        return namedParameter;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return namedParameter.toString();
    }

    public void builderExpression(StatementSqlBuilder builder) {
        namedParameter.builderExpression(builder);
        builder.addParamValue(value);
    }

}
