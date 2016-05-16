package org.tinygroup.lucene472.builder;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.fulltext.field.StoreField;

/**
 * 构建Lucene的IndexableField对象
 * 
 * @author yancheng11334
 * 
 */
@SuppressWarnings("rawtypes")
public class IndexableFieldBuilder {

	private static final String HTML_TAG_RULE = "<[/]?[\\s\\S]*?[/]?>";
	
	public org.apache.lucene.index.IndexableField build(StoreField field,boolean tag) {
		switch (field.getType()) {
		case STRING: {
			return buildStringField(field,tag);
		}
		case INT: {
			return buildIntField(field);
		}
		case LONG: {
			return buildLongField(field);
		}
		case FLOAT: {
			return buildFloatField(field);
		}
		case DOUBLE: {
			return buildDoubleField(field);
		}
		case DATE: {
			return buildDateField(field);
		}
		case BIGDECIMAL: {
			return buildBigDecimalField(field);
		}
		case BINARY: {
			return buildBinaryField(field);
		}
		default: {
			return buildObjectField(field);
		}
		}
	}

	private org.apache.lucene.index.IndexableField buildStringField(
			StoreField field,boolean tag) {
		String v = StringUtil.defaultIfBlank((String)field.getValue(), "");
		v = tag?v.replaceAll(HTML_TAG_RULE, ""):v;
		if (field.isTokenized()) {
			return new TextField(field.getName(),
					v, field.isStored()?Store.YES:Store.NO);
		} else {
			return new StringField(field.getName(),
					v, field.isStored()?Store.YES:Store.NO);
		}
	}

	private org.apache.lucene.index.IndexableField buildIntField(
			StoreField field) {
		return new IntField(field.getName(), (Integer) field.getValue(),
				field.isStored()?Store.YES:Store.NO);
	}

	private org.apache.lucene.index.IndexableField buildLongField(
			StoreField field) {
		return new LongField(field.getName(), (Long) field.getValue(),
				field.isStored()?Store.YES:Store.NO);
	}

	private org.apache.lucene.index.IndexableField buildFloatField(
			StoreField field) {
		return new FloatField(field.getName(), (Float) field.getValue(),
				field.isStored()?Store.YES:Store.NO);
	}

	private org.apache.lucene.index.IndexableField buildDoubleField(
			StoreField field) {
		return new DoubleField(field.getName(), (Double) field.getValue(),
				field.isStored()?Store.YES:Store.NO);
	}

	private org.apache.lucene.index.IndexableField buildDateField(
			StoreField field) {
		Date date = (Date) field.getValue();
		return new StringField(field.getName(), DateTools.dateToString(date,
				DateTools.Resolution.SECOND), field.isStored()?Store.YES:Store.NO);
	}

	private org.apache.lucene.index.IndexableField buildBigDecimalField(
			StoreField field) {
		BigDecimal bigDecimal = (BigDecimal) field.getValue();
		return new StringField(field.getName(), bigDecimal.toString(),
				field.isStored()?Store.YES:Store.NO);
	}

	private org.apache.lucene.index.IndexableField buildBinaryField(
			StoreField field) {
		return new org.apache.lucene.document.StoredField(field.getName(),
				(byte[]) field.getValue());
	}

	private org.apache.lucene.index.IndexableField buildObjectField(
			StoreField field) {
		return new org.apache.lucene.document.StoredField(field.getName(),
				field.getValue().toString());
	}
}
