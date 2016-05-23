package org.tinygroup.sqlindexsource.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.fulltext.DocumentListCreator;
import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.templateindex.TemplateIndexRender;

public class ResultSetDocumentListCreator extends AbstractResultSetOperator implements
		DocumentListCreator<ResultSet> {

	private TemplateIndexRender templateIndexRender;

	public TemplateIndexRender getTemplateIndexRender() {
		return templateIndexRender;
	}

	public void setTemplateIndexRender(TemplateIndexRender templateIndexRender) {
		this.templateIndexRender = templateIndexRender;
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
				docs.add(templateIndexRender.execute(context));

			}
		} catch (SQLException e) {
			throw new FullTextException("遍历结果集发生异常", e);
		}
		return docs;
	}

	
}
