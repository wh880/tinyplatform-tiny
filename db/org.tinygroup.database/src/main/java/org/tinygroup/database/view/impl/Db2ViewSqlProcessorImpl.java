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
package org.tinygroup.database.view.impl;

import org.tinygroup.database.config.view.View;
import org.tinygroup.database.view.impl.creator.MysqlViewSqlCreator;
import org.tinygroup.database.view.impl.creator.ViewSqlCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Db2ViewSqlProcessorImpl extends ViewSqlProcessorImpl {

	public String getCreateSql(View view) {
	    ViewSqlCreator creator = new MysqlViewSqlCreator(view);

		return creator.getCreateSql();
	}

    public boolean checkViewExists(View view, Connection conn) throws SQLException {
        boolean checkResult = false;
        String checkSql = buildCheckViewSql(view);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement(checkSql);
            preparedStatement.setString(1,view.getName());
            preparedStatement.setString(2,view.getSchema());
            resultSet.next();
            long count = resultSet.getLong(1);
            checkResult = count>0?true:false;
        } finally{
            if(null != resultSet){
                resultSet.close();
            }
            if(null != preparedStatement){
                preparedStatement.close();
            }
        }
        return checkResult;
    }

    /**
     * 获取mysql中检测视图是否存在的sql语句
     * @param view 视图构建元数据
     * @return 检测视图是否存在的sql语句
     */
    private String buildCheckViewSql(View view){
        StringBuffer checkSql = new StringBuffer();
        checkSql.append("SELECT count(0) FROM information_schema.VIEWS WHERE information_schema.VIEWS.TABLE_NAME = ?")
        .append(" and information_schema.VIEWS.TABLE_SCHEMA=?");
        return checkSql.toString();
    }
}
