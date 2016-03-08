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
package org.tinygroup.tinysqldsl;

import junit.framework.TestCase;

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.extend.MysqlSelect.selectFrom;
import static org.tinygroup.tinysqldsl.select.OrderByElement.desc;

public class TestMySql extends TestCase {

    public void testSql() {
        String sql = selectFrom(CUSTOM).orderBy(desc(CUSTOM.NAME)).limit(1, 10)
                .sql();
        assertEquals(
                "SELECT * FROM custom ORDER BY custom.name DESC LIMIT 10 OFFSET 1",
                sql);
    }
}
