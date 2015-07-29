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
package org.tinygroup.jdbctemplatedslsession.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.tinygroup.jdbctemplatedslsession.rowmapper.TinyBeanPropertyRowMapper;

/**
 * 分页处理的结果集
 * @author renhui
 *
 */
public class PageResultSetExtractor implements ResultSetExtractor {

	private int start;// start with 0;
	private int limit;
	private boolean isCursor;// 游标分页
	private RowMapper rowMapper;

	public PageResultSetExtractor(int start, int limit, boolean isCursor,
			Class requiredType) {
		this.start = start;
		this.limit = limit;
		if (start < 0) {
			this.start = 0;
		}
		rowMapper = new TinyBeanPropertyRowMapper(requiredType);
		this.isCursor = isCursor;
	}

	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		List results = new ArrayList();
		if (isCursor) {// 需要进行游标分页
			if (rs.getType() == ResultSet.TYPE_FORWARD_ONLY) {
				results = extractDataWithForward(rs, rowMapper);
			} else {
				results = extractDataWithScroll(rs, rowMapper);
			}
		} else {
			results = extractData(rs, rowMapper);
		}
		return results;
	}

	private List extractData(ResultSet rs, RowMapper mapper)
			throws SQLException {
		List results = new ArrayList();
		int rowNum = 0;
		while (rs.next()) {
			results.add(mapper.mapRow(rs, rowNum++));
		}
		return results;
	}

	private List extractDataWithScroll(ResultSet rs, RowMapper mapper)
			throws SQLException {
		List results = new ArrayList();
		int rowNum = 0;
		if (rs.absolute(start + 1)) {
			do {
				results.add(mapper.mapRow(rs, rowNum++));
			} while ((rs.next()) && (rowNum < limit));
		}
		return results;
	}

	private List extractDataWithForward(ResultSet rs, RowMapper mapper)
			throws SQLException {
		List results = new ArrayList();
		int rowNum = 0;
		int count = 0;
		// 1.游标位置定位
		while (rs.next()) {
			if (count == start) {
				break;
			}
			count++;
		}
		if (count == start) { // 绝对定位成功 ，记录数足够则提取分页需要的记录数
			do {
				results.add(mapper.mapRow(rs, rowNum++));
			} while ((rs.next()) && (rowNum < limit));
		}
		return results;
	}

}
