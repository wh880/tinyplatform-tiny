package org.tinygroup.fulltext.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.fulltext.DocumentListCreator;
import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.TemplateDocumentCreator;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.fulltext.impl.AbstractCreator;

public class ResultSetDocumentListCreator extends AbstractCreator implements
		DocumentListCreator<ResultSet> {

	private TemplateDocumentCreator templateDocumentCreator;

	public TemplateDocumentCreator getTemplateDocumentCreator() {
		return templateDocumentCreator;
	}

	public void setTemplateDocumentCreator(
			TemplateDocumentCreator templateDocumentCreator) {
		this.templateDocumentCreator = templateDocumentCreator;
	}

	public boolean isMatch(ResultSet data) {
		return data instanceof ResultSet;
	}

	public List<Document> getDocument(String type, ResultSet data,
			Object... arguments) {

		List<Document> docs = new ArrayList<Document>();

		try {
			ResultSetMetaData rsmd = data.getMetaData();
			while (data.next()) {
				// 转换上下文
				Context context = new ContextImpl();
				context.put(FullTextHelper.getStoreType(), type);
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String name = rsmd.getColumnName(i);
					updateContext(data, i, name, context);
				}
				docs.add(templateDocumentCreator.execute(context));

			}
		} catch (SQLException e) {
			throw new FullTextException("遍历结果集发生异常", e);
		}
		return docs;
	}

	private void updateContext(ResultSet data, int i, String name,
			Context context) throws SQLException {
		ResultSetMetaData rsmd = data.getMetaData();
		int type = rsmd.getColumnType(i);
		if (isString(type)) {
			context.put(name, data.getString(i));
		} else if (isInt(type)) {
			context.put(name, data.getInt(i));
		} else if (isLong(type)) {
			context.put(name, data.getLong(i));
		} else if (isFloat(type)) {
			context.put(name, data.getFloat(i));
		} else if (isDouble(type)) {
			context.put(name, data.getDouble(i));
		} else if (isBigDecimal(type)) {
			context.put(name, data.getBigDecimal(i));
		} else if (isDate(type)) {
			updateDate(type, data, i, name, context);
		} else if (isByte(type)) {
			context.put(name, data.getBytes(i));
		} else {
			context.put(name, data.getObject(i));
		}
	}

	private void updateDate(int type, ResultSet data, int i, String name,
			Context context) throws SQLException {
		if (type == Types.DATE) {
			context.put(name, new Date(data.getDate(i).getTime()));
		} else if (type == Types.TIME) {
			context.put(name, new Date(data.getTime(i).getTime()));
		} else {
			context.put(name, new Date(data.getTimestamp(i).getTime()));
		}
	}

	private boolean isString(int type) {
		if (type == Types.CHAR || type == Types.VARCHAR
				|| type == Types.LONGVARCHAR) {
			return true;
		}
		return false;
	}

	private boolean isByte(int type) {
		if (type == Types.BINARY || type == Types.VARBINARY
				|| type == Types.LONGVARBINARY) {
			return true;
		}
		return false;
	}

	private boolean isInt(int type) {
		if (type == Types.BIT || type == Types.BOOLEAN
				|| type == Types.SMALLINT || type == Types.TINYINT
				|| type == Types.INTEGER) {
			return true;
		}
		return false;
	}

	private boolean isLong(int type) {
		if (type == Types.BIGINT) {
			return true;
		}
		return false;
	}

	private boolean isFloat(int type) {
		if (type == Types.REAL || type == Types.FLOAT) {
			return true;
		}
		return false;
	}

	private boolean isDouble(int type) {
		if (type == Types.DOUBLE) {
			return true;
		}
		return false;
	}

	private boolean isBigDecimal(int type) {
		if (type == Types.DECIMAL || type == Types.NUMERIC) {
			return true;
		}
		return false;
	}

	private boolean isDate(int type) {
		if (type == Types.DATE || type == Types.TIME || type == Types.TIMESTAMP) {
			return true;
		}
		return false;
	}
}
