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
package net.sf.jsqlparser.util;

import junit.framework.TestCase;
import org.tinygroup.jsqlparser.JSQLParserException;
import org.tinygroup.jsqlparser.expression.BinaryExpression;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Addition;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Concat;
import org.tinygroup.jsqlparser.parser.CCJSqlParserManager;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.util.ConnectExpressionsVisitor;

import java.io.StringReader;


/**
 * @author tw
 */
public class ConnectExpressionsVisitorTest extends TestCase {

    public ConnectExpressionsVisitorTest() {
    }


    public static void setUpClass() {
    }


    public static void tearDownClass() {
    }


    public void setUp() {
    }


    public void tearDown() {
    }

    CCJSqlParserManager parserManager = new CCJSqlParserManager();

    public void testVisit_PlainSelect_concat() throws JSQLParserException {
        String sql = "select a,b,c from test";
        Select select = (Select) parserManager.parse(new StringReader(sql));
        ConnectExpressionsVisitor instance = new ConnectExpressionsVisitor() {

            protected BinaryExpression createBinaryExpression() {
                return new Concat();
            }
        };
        select.getSelectBody().accept(instance);

        assertEquals("SELECT a || b || c AS expr FROM test", select.toString());
    }

    public void testVisit_PlainSelect_addition() throws JSQLParserException {
        String sql = "select a,b,c from test";
        Select select = (Select) parserManager.parse(new StringReader(sql));
        ConnectExpressionsVisitor instance = new ConnectExpressionsVisitor("testexpr") {

            protected BinaryExpression createBinaryExpression() {
                return new Addition();
            }
        };
        select.getSelectBody().accept(instance);

        assertEquals("SELECT a + b + c AS testexpr FROM test", select.toString());
    }
}
