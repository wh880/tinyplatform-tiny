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
package org.tinygroup.databasebuinstaller.impl;

import org.tinygroup.database.customesql.CustomSqlProcessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomSqlInstallProcessor extends AbstractInstallProcessor {

    private CustomSqlProcessor customSqlProcessor;

    public void setCustomSqlProcessor(CustomSqlProcessor customSqlProcessor) {
        this.customSqlProcessor = customSqlProcessor;
    }

    public List<String> getDealSqls(String language, Connection con) throws SQLException {
        List<String> customSqls = new ArrayList<String>();

        //根据顺序加载自定义sql
        customSqls.addAll(customSqlProcessor.getCustomSqls(CustomSqlProcessor.BEFORE, CustomSqlProcessor.STANDARD_SQL_TYPE));
        customSqls.addAll(customSqlProcessor.getCustomSqls(CustomSqlProcessor.BEFORE, language));

        customSqls.addAll(customSqlProcessor.getCustomSqls(CustomSqlProcessor.AFTER, CustomSqlProcessor.STANDARD_SQL_TYPE));
        customSqls.addAll(customSqlProcessor.getCustomSqls(CustomSqlProcessor.AFTER, language));

        return customSqls;
    }

}
