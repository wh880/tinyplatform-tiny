/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package net.sf.jsqlparser.test;

import org.tinygroup.jsqlparser.JSQLParserException;
import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.parser.CCJSqlParserUtil;
import org.tinygroup.jsqlparser.statement.Statement;
import org.tinygroup.jsqlparser.util.deparser.ExpressionDeParser;
import org.tinygroup.jsqlparser.util.deparser.SelectDeParser;
import org.tinygroup.jsqlparser.util.deparser.StatementDeParser;

import java.io.StringReader;

import static junit.framework.Assert.assertEquals;

/**
 * @author toben
 */
public class TestUtils {
    public static void assertSqlCanBeParsedAndDeparsed(String statement) throws JSQLParserException {
        Statement parsed = CCJSqlParserUtil.parse(new StringReader(statement));
        assertStatementCanBeDeparsedAs(parsed, statement);
    }

    public static void assertStatementCanBeDeparsedAs(Statement parsed, String statement) {
        assertEquals(statement, parsed.toString());

        StatementDeParser deParser = new StatementDeParser(new StringBuilder());
        parsed.accept(deParser);
        assertEquals(statement, deParser.getBuffer().toString());
    }

    public static void assertExpressionCanBeDeparsedAs(final Expression parsed, String expression) {
        ExpressionDeParser expressionDeParser = new ExpressionDeParser();
        StringBuilder stringBuffer = new StringBuilder();
        expressionDeParser.setBuffer(stringBuffer);
        SelectDeParser selectDeParser = new SelectDeParser(expressionDeParser, stringBuffer);
        expressionDeParser.setSelectVisitor(selectDeParser);
        parsed.accept(expressionDeParser);

        assertEquals(expression, stringBuffer.toString());
    }
}
