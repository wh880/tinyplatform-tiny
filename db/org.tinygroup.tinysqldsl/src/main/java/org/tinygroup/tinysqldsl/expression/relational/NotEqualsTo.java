/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p/>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.gnu.org/licenses/gpl.html
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.tinysqldsl.expression.relational;

import org.tinygroup.tinysqldsl.expression.Expression;
import org.tinygroup.tinysqldsl.visitor.ExpressionVisitor;

public class NotEqualsTo extends OldOracleJoinBinaryExpression {

    private final String operator;


    public NotEqualsTo(Expression leftExpression, Expression rightExpression) {
        this(leftExpression, rightExpression, true, "<>");
    }

    public NotEqualsTo(Expression leftExpression, Expression rightExpression,
                       boolean not, String operator) {
        super(leftExpression, rightExpression, not);
        this.operator = operator;
        if (!"!=".equals(operator) && !"<>".equals(operator)) {
            throw new IllegalArgumentException("only <> or != allowed");
        }
    }

    public String getStringExpression() {
        return operator;
    }

    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}