package org.tinygroup.lucene472.wrapper;

import org.apache.lucene.index.IndexableField;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.fulltext.field.Field;
import org.tinygroup.fulltext.field.FieldType;
import org.tinygroup.fulltext.field.HighlightField;
import org.tinygroup.fulltext.field.StringField;

@SuppressWarnings("rawtypes")
public class HighlightFieldWrapper implements HighlightField{
    
	private Field field;
	private String perfix;
	private String suffix;
	private String template;
	
	public HighlightFieldWrapper(IndexableField indexableField,String perfix,String suffix,String template){
		field = new StringField(indexableField.name(),indexableField.stringValue());
		this.perfix = perfix;
		this.suffix = suffix;
		this.template = template;
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

	public Object getSourceValue() {
		return getRenderValue("","");
	}

	public Object getRenderValue(Object... arguments) {
		try{
			String pword = (String) arguments[0];
			String sword = (String) arguments[1];
			
			String text = template.toString();
			return text.replaceAll(perfix, pword).replaceAll(suffix, sword);
		}catch(Exception e){
			throw new FullTextException("高亮字段渲染失败:参数格式不正确",e);
		}
	}
	
	public String toString() {
		return "HighlightFieldWrapper [perfix=" + perfix + ", suffix=" + suffix
				+ ", name=" + field.getName() + ", template=" + template + "]";
	}
	
}
