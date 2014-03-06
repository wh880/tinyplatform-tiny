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
package net.sf.jsqlparser.test.simpleparsing;

import junit.framework.TestCase;
import net.sf.jsqlparser.test.TestException;
import net.sf.jsqlparser.test.create.CreateTableTest;
import org.tinygroup.jsqlparser.JSQLParserException;
import org.tinygroup.jsqlparser.parser.CCJSqlParserManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

public class CCJSqlParserManagerTest extends TestCase {

    public CCJSqlParserManagerTest(String arg0) {
        super(arg0);
    }

    public void testParse() throws Exception {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        BufferedReader in = new BufferedReader(new InputStreamReader(CreateTableTest.class.getResourceAsStream("/simple_parsing.txt")));

        String statement = "";
        while (true) {
            try {
                statement = CCJSqlParserManagerTest.getStatement(in);
                if (statement == null) {
                    break;
                }

                parserManager.parse(new StringReader(statement));
            } catch (JSQLParserException e) {
                throw new TestException("impossible to parse statement: " + statement, e);
            }
        }
    }

    public static String getStatement(BufferedReader in) throws Exception {
        StringBuilder buf = new StringBuilder();
        String line = null;
        while ((line = CCJSqlParserManagerTest.getLine(in)) != null) {

            if (line.length() == 0) {
                break;
            }

            buf.append(line);
            buf.append("\n");

        }

        if (buf.length() > 0) {
            return buf.toString();
        } else {
            return null;
        }

    }

    public static String getLine(BufferedReader in) throws Exception {
        String line = null;
        while (true) {
            line = in.readLine();
            if (line != null) {
                line.trim();
                // if ((line.length() != 0) && ((line.length() < 2) || (line.length() >= 2) && !(line.charAt(0) == '/'
                // && line.charAt(1) == '/')))
                if (((line.length() < 2) || (line.length() >= 2) && !(line.charAt(0) == '/'
                        && line.charAt(1) == '/'))) {
                    break;
                }
            } else {
                break;
            }

        }

        return line;
    }
}
