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
package org.tinygroup.dbrouterjdbc4.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * 功能说明: result的包装类
 * <p>

 * 开发人员: renhui <br>
 * 开发时间: 2013-12-18 <br>
 * <br>
 */
public class TinyResultSetWrapper implements ResultSet {
	private String sql;
	private ResultSet resultSet;
	private TinyConnection tinyConnection;
	private TinyStatement tinyStatement;

	public TinyResultSetWrapper(String sql, ResultSet resultSet,
			TinyStatement tinyStatement, TinyConnection tinyConnection) {
		this.sql = sql;
		this.resultSet = resultSet;
		this.tinyStatement = tinyStatement;
		this.tinyConnection = tinyConnection;

	}

	public boolean next() throws SQLException {
		return resultSet.next();
	}

	public void close() throws SQLException {
		resultSet.close();
	}

	public boolean wasNull() throws SQLException {
		return resultSet.wasNull();
	}

	public String getString(int columnIndex) throws SQLException {
		return resultSet.getString(columnIndex);
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		return resultSet.getBoolean(columnIndex);
	}

	public byte getByte(int columnIndex) throws SQLException {
		return resultSet.getByte(columnIndex);
	}

	public short getShort(int columnIndex) throws SQLException {
		return resultSet.getShort(columnIndex);
	}

	public int getInt(int columnIndex) throws SQLException {
		return resultSet.getInt(columnIndex);
	}

	public long getLong(int columnIndex) throws SQLException {
		return resultSet.getLong(columnIndex);
	}

	public float getFloat(int columnIndex) throws SQLException {
		return resultSet.getFloat(columnIndex);
	}

	public double getDouble(int columnIndex) throws SQLException {
		return resultSet.getDouble(columnIndex);
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException {
		return resultSet.getBigDecimal(columnIndex);
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		return resultSet.getBytes(columnIndex);
	}

	public Date getDate(int columnIndex) throws SQLException {
		return resultSet.getDate(columnIndex);
	}

	public Time getTime(int columnIndex) throws SQLException {
		return resultSet.getTime(columnIndex);
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return resultSet.getTimestamp(columnIndex);
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		return resultSet.getAsciiStream(columnIndex);
	}

	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return resultSet.getUnicodeStream(columnIndex);
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		return resultSet.getBinaryStream(columnIndex);
	}

	public String getString(String columnName) throws SQLException {
		return resultSet.getString(columnName);
	}

	public boolean getBoolean(String columnName) throws SQLException {
		return resultSet.getBoolean(columnName);
	}

	public byte getByte(String columnName) throws SQLException {
		return resultSet.getByte(columnName);
	}

	public short getShort(String columnName) throws SQLException {
		return resultSet.getShort(columnName);
	}

	public int getInt(String columnName) throws SQLException {
		return resultSet.getInt(columnName);
	}

	public long getLong(String columnName) throws SQLException {
		return resultSet.getLong(columnName);
	}

	public float getFloat(String columnName) throws SQLException {
		return resultSet.getFloat(columnName);
	}

	public double getDouble(String columnName) throws SQLException {
		return resultSet.getDouble(columnName);
	}

	public BigDecimal getBigDecimal(String columnName, int scale)
			throws SQLException {
		return resultSet.getBigDecimal(columnName);
	}

	public byte[] getBytes(String columnName) throws SQLException {
		return resultSet.getBytes(columnName);
	}

	public Date getDate(String columnName) throws SQLException {
		return resultSet.getDate(columnName);
	}

	public Time getTime(String columnName) throws SQLException {
		return resultSet.getTime(columnName);
	}

	public Timestamp getTimestamp(String columnName) throws SQLException {
		return resultSet.getTimestamp(columnName);
	}

	public InputStream getAsciiStream(String columnName) throws SQLException {
		return resultSet.getAsciiStream(columnName);
	}

	public InputStream getUnicodeStream(String columnName) throws SQLException {
		return resultSet.getUnicodeStream(columnName);
	}

	public InputStream getBinaryStream(String columnName) throws SQLException {
		return resultSet.getBinaryStream(columnName);
	}

	public SQLWarning getWarnings() throws SQLException {
		return resultSet.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		resultSet.clearWarnings();
	}

	public String getCursorName() throws SQLException {
		return resultSet.getCursorName();
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return new TinyResultSetMetaData(sql, resultSet.getMetaData());
	}

	public Object getObject(int columnIndex) throws SQLException {
		return resultSet.getObject(columnIndex);
	}

	public Object getObject(String columnName) throws SQLException {
		return resultSet.getObject(columnName);
	}

	public int findColumn(String columnName) throws SQLException {
		return resultSet.findColumn(columnName);
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		return resultSet.getCharacterStream(columnIndex);
	}

	public Reader getCharacterStream(String columnName) throws SQLException {
		return resultSet.getCharacterStream(columnName);
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return resultSet.getBigDecimal(columnIndex);
	}

	public BigDecimal getBigDecimal(String columnName) throws SQLException {
		return resultSet.getBigDecimal(columnName);
	}

	public boolean isBeforeFirst() throws SQLException {
		return resultSet.isBeforeFirst();
	}

	public boolean isAfterLast() throws SQLException {
		return resultSet.isAfterLast();
	}

	public boolean isFirst() throws SQLException {
		return resultSet.isFirst();
	}

	public boolean isLast() throws SQLException {
		return resultSet.isLast();
	}

	public void beforeFirst() throws SQLException {
		resultSet.beforeFirst();
	}

	public void afterLast() throws SQLException {
		resultSet.afterLast();
	}

	public boolean first() throws SQLException {
		return resultSet.first();
	}

	public boolean last() throws SQLException {
		return resultSet.last();
	}

	public int getRow() throws SQLException {
		return resultSet.getRow();
	}

	public boolean absolute(int row) throws SQLException {
		return resultSet.absolute(row);
	}

	public boolean relative(int rows) throws SQLException {
		return resultSet.relative(rows);
	}

	public boolean previous() throws SQLException {
		return resultSet.previous();
	}

	public void setFetchDirection(int direction) throws SQLException {
		resultSet.setFetchDirection(direction);
	}

	public int getFetchDirection() throws SQLException {
		return resultSet.getFetchDirection();
	}

	public void setFetchSize(int rows) throws SQLException {
		resultSet.setFetchSize(rows);
	}

	public int getFetchSize() throws SQLException {
		return resultSet.getFetchSize();
	}

	public int getType() throws SQLException {
		return resultSet.getType();
	}

	public int getConcurrency() throws SQLException {
		return resultSet.getConcurrency();
	}

	public boolean rowUpdated() throws SQLException {
		return resultSet.rowDeleted();
	}

	public boolean rowInserted() throws SQLException {
		return resultSet.rowInserted();
	}

	public boolean rowDeleted() throws SQLException {
		return resultSet.rowDeleted();
	}

	public void updateNull(int columnIndex) throws SQLException {
		resultSet.updateNull(columnIndex);
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		resultSet.updateBoolean(columnIndex, x);
	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		resultSet.updateByte(columnIndex, x);
	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		resultSet.updateShort(columnIndex, x);
	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		resultSet.updateInt(columnIndex, x);
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		resultSet.updateLong(columnIndex, x);
	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		resultSet.updateFloat(columnIndex, x);
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		resultSet.updateDouble(columnIndex, x);
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException {
		resultSet.updateBigDecimal(columnIndex, x);
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		resultSet.updateString(columnIndex, x);
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		resultSet.updateBytes(columnIndex, x);
	}

	public void updateDate(int columnIndex, Date x) throws SQLException {
		resultSet.updateDate(columnIndex, x);
	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		resultSet.updateTime(columnIndex, x);
	}

	public void updateTimestamp(int columnIndex, Timestamp x)
			throws SQLException {
		resultSet.updateTimestamp(columnIndex, x);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		resultSet.updateAsciiStream(columnIndex, x, length);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		resultSet.updateBinaryStream(columnIndex, x, length);
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length)
			throws SQLException {
		resultSet.updateCharacterStream(columnIndex, x, length);
	}

	public void updateObject(int columnIndex, Object x, int scale)
			throws SQLException {
		resultSet.updateObject(columnIndex, x, scale);
	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		resultSet.updateObject(columnIndex, x);
	}

	public void updateNull(String columnName) throws SQLException {
		resultSet.updateNull(columnName);
	}

	public void updateBoolean(String columnName, boolean x) throws SQLException {
		resultSet.updateBoolean(columnName, x);
	}

	public void updateByte(String columnName, byte x) throws SQLException {
		resultSet.updateByte(columnName, x);
	}

	public void updateShort(String columnName, short x) throws SQLException {
		resultSet.updateShort(columnName, x);
	}

	public void updateInt(String columnName, int x) throws SQLException {
		resultSet.updateInt(columnName, x);
	}

	public void updateLong(String columnName, long x) throws SQLException {
		resultSet.updateLong(columnName, x);
	}

	public void updateFloat(String columnName, float x) throws SQLException {
		resultSet.updateFloat(columnName, x);
	}

	public void updateDouble(String columnName, double x) throws SQLException {
		resultSet.updateDouble(columnName, x);
	}

	public void updateBigDecimal(String columnName, BigDecimal x)
			throws SQLException {
		resultSet.updateBigDecimal(columnName, x);
	}

	public void updateString(String columnName, String x) throws SQLException {
		resultSet.updateString(columnName, x);
	}

	public void updateBytes(String columnName, byte[] x) throws SQLException {
		resultSet.updateBytes(columnName, x);
	}

	public void updateDate(String columnName, Date x) throws SQLException {
		resultSet.updateDate(columnName, x);
	}

	public void updateTime(String columnName, Time x) throws SQLException {
		resultSet.updateTime(columnName, x);
	}

	public void updateTimestamp(String columnName, Timestamp x)
			throws SQLException {
		resultSet.updateTimestamp(columnName, x);
	}

	public void updateAsciiStream(String columnName, InputStream x, int length)
			throws SQLException {
		resultSet.updateAsciiStream(columnName, x, length);
	}

	public void updateBinaryStream(String columnName, InputStream x, int length)
			throws SQLException {
		resultSet.updateBinaryStream(columnName, x, length);
	}

	public void updateCharacterStream(String columnName, Reader reader,
			int length) throws SQLException {
		resultSet.updateCharacterStream(columnName, reader, length);
	}

	public void updateObject(String columnName, Object x, int scale)
			throws SQLException {
		resultSet.updateObject(columnName, x, scale);
	}

	public void updateObject(String columnName, Object x) throws SQLException {
		resultSet.updateObject(columnName, x);
	}

	public void insertRow() throws SQLException {
		resultSet.insertRow();
	}

	public void updateRow() throws SQLException {
		resultSet.updateRow();
	}

	public void deleteRow() throws SQLException {
		resultSet.deleteRow();
	}

	public void refreshRow() throws SQLException {
		resultSet.refreshRow();
	}

	public void cancelRowUpdates() throws SQLException {
		resultSet.cancelRowUpdates();
	}

	public void moveToInsertRow() throws SQLException {
		resultSet.moveToInsertRow();
	}

	public void moveToCurrentRow() throws SQLException {
		resultSet.moveToCurrentRow();
	}

	public Statement getStatement() throws SQLException {
		return resultSet.getStatement();
	}

	public Object getObject(int i, Map<String, Class<?>> map)
			throws SQLException {
		return resultSet.getObject(i, map);
	}

	public Ref getRef(int i) throws SQLException {
		return resultSet.getRef(i);
	}

	public Blob getBlob(int i) throws SQLException {
		return resultSet.getBlob(i);
	}

	public Clob getClob(int i) throws SQLException {
		return resultSet.getClob(i);
	}

	public Array getArray(int i) throws SQLException {
		return resultSet.getArray(i);
	}

	public Object getObject(String colName, Map<String, Class<?>> map)
			throws SQLException {
		return resultSet.getObject(colName, map);
	}

	public Ref getRef(String colName) throws SQLException {
		return resultSet.getRef(colName);
	}

	public Blob getBlob(String colName) throws SQLException {
		return resultSet.getBlob(colName);
	}

	public Clob getClob(String colName) throws SQLException {
		return resultSet.getClob(colName);
	}

	public Array getArray(String colName) throws SQLException {
		return resultSet.getArray(colName);
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return resultSet.getDate(columnIndex, cal);
	}

	public Date getDate(String columnName, Calendar cal) throws SQLException {
		return resultSet.getDate(columnName, cal);
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return resultSet.getTime(columnIndex, cal);
	}

	public Time getTime(String columnName, Calendar cal) throws SQLException {
		return resultSet.getTime(columnName, cal);
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException {
		return resultSet.getTimestamp(columnIndex, cal);
	}

	public Timestamp getTimestamp(String columnName, Calendar cal)
			throws SQLException {
		return resultSet.getTimestamp(columnName, cal);
	}

	public URL getURL(int columnIndex) throws SQLException {
		return resultSet.getURL(columnIndex);
	}

	public URL getURL(String columnName) throws SQLException {
		return resultSet.getURL(columnName);
	}

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		resultSet.updateRef(columnIndex, x);
	}

	public void updateRef(String columnName, Ref x) throws SQLException {
		resultSet.updateRef(columnName, x);
	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		resultSet.updateBlob(columnIndex, x);
	}

	public void updateBlob(String columnName, Blob x) throws SQLException {
		resultSet.updateBlob(columnName, x);
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		resultSet.updateClob(columnIndex, x);
	}

	public void updateClob(String columnName, Clob x) throws SQLException {
		resultSet.updateClob(columnName, x);
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		resultSet.updateArray(columnIndex, x);
	}

	public void updateArray(String columnName, Array x) throws SQLException {
		resultSet.updateArray(columnName, x);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("not support method");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("not support method");
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		return resultSet.getRowId(columnIndex);
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		return resultSet.getRowId(columnLabel);
	}

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		resultSet.updateRowId(columnIndex, x);
	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		resultSet.updateRowId(columnLabel, x);
	}

	public int getHoldability() throws SQLException {
		return resultSet.getHoldability();
	}

	public boolean isClosed() throws SQLException {
		return resultSet.isClosed();
	}

	public void updateNString(int columnIndex, String nString)
			throws SQLException {
		resultSet.updateNString(columnIndex, nString);

	}

	public void updateNString(String columnLabel, String nString)
			throws SQLException {
		resultSet.updateNString(columnLabel, nString);
	}

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		resultSet.updateNClob(columnIndex, nClob);
	}

	public void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {
		resultSet.updateNClob(columnLabel, nClob);
	}

	public NClob getNClob(int columnIndex) throws SQLException {
		return resultSet.getNClob(columnIndex);
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		return resultSet.getNClob(columnLabel);
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return resultSet.getSQLXML(columnIndex);
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return resultSet.getSQLXML(columnLabel);
	}

	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		resultSet.updateSQLXML(columnIndex, xmlObject);
	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		resultSet.updateSQLXML(columnLabel, xmlObject);
	}

	public String getNString(int columnIndex) throws SQLException {
		return resultSet.getNString(columnIndex);
	}

	public String getNString(String columnLabel) throws SQLException {
		return resultSet.getNString(columnLabel);
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return resultSet.getNCharacterStream(columnIndex);
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return resultSet.getNCharacterStream(columnLabel);
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		resultSet.updateNCharacterStream(columnIndex, x, length);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		resultSet.updateNCharacterStream(columnLabel, reader, length);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		resultSet.updateAsciiStream(columnIndex, x, length);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		resultSet.updateBinaryStream(columnIndex, x, length);
	}

	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		resultSet.updateCharacterStream(columnIndex, x, length);
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {
		resultSet.updateAsciiStream(columnLabel, x, length);
	}

	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		resultSet.updateBinaryStream(columnLabel, x, length);
	}

	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		resultSet.updateCharacterStream(columnLabel, reader, length);
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		resultSet.updateBlob(columnIndex, inputStream, length);
	}

	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		resultSet.updateBlob(columnLabel, inputStream, length);
	}

	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		resultSet.updateClob(columnIndex, reader, length);
	}

	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		resultSet.updateNClob(columnLabel, reader, length);
	}

	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		resultSet.updateNClob(columnIndex, reader, length);
	}

	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		resultSet.updateNClob(columnLabel, reader, length);
	}

	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		resultSet.updateNCharacterStream(columnIndex, x);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		resultSet.updateNCharacterStream(columnLabel, reader);
	}

	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		resultSet.updateAsciiStream(columnIndex, x);
	}

	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		resultSet.updateBinaryStream(columnIndex, x);
	}

	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		resultSet.updateCharacterStream(columnIndex, x);
	}

	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		resultSet.updateAsciiStream(columnLabel, x);
	}

	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		resultSet.updateBinaryStream(columnLabel, x);
	}

	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		resultSet.updateCharacterStream(columnLabel, reader);
	}

	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		resultSet.updateBlob(columnIndex, inputStream);
	}

	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		resultSet.updateBlob(columnLabel, inputStream);
	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		resultSet.updateClob(columnIndex, reader);
	}

	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		resultSet.updateClob(columnLabel, reader);
	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		resultSet.updateClob(columnIndex, reader);
	}

	public void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		resultSet.updateClob(columnLabel, reader);
	}

}
