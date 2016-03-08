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
import static org.tinygroup.tinysqldsl.Delete.delete;
import static org.tinygroup.tinysqldsl.base.StatementSqlBuilder.and;


public class JunitTestDelete extends TestCase {


    public void testDelete() {
        assertEquals(delete(CUSTOM).where(CUSTOM.NAME.eq("悠然")).sql(), "DELETE FROM custom WHERE custom.name = ?");

        assertEquals(delete(CUSTOM).where(and(CUSTOM.NAME.leftLike("A"), CUSTOM.AGE.between(20, 30))).sql(), "DELETE FROM custom WHERE (custom.name LIKE ? and custom.age BETWEEN ? AND ?)");
    }

}
