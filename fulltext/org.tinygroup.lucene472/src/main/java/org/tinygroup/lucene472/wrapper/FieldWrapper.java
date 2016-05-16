package org.tinygroup.lucene472.wrapper;

import org.apache.lucene.index.IndexableField;
import org.tinygroup.fulltext.field.Field;
import org.tinygroup.fulltext.field.FieldType;
import org.tinygroup.fulltext.field.StringField;

@SuppressWarnings("rawtypes")
public class FieldWrapper implements Field{

	private Field field;
	
	public FieldWrapper(IndexableField indexableField){
		field = new StringField(indexableField.name(),indexableField.stringValue());
	}
	
	public String getName() {
		return field.getName();
	}

	public FieldType getType() {
		return field.getType();
	}

	public Object getValue() {
		return field.getValue();
	}

	public String toString(){
		return field.toString();
	}
}
