package org.tinygroup.fulltext.field;

public class StringField extends AbstractField<String> implements StoreField<String>{

	public StringField(String name, String value) {
		this(name,value,true,true,false);
	}
	
	public StringField(String name, String value,boolean indexed,boolean stored,boolean tokenized) {
		super(name, value);
		this.setIndexed(indexed);
		this.setStored(stored);
		this.setTokenized(tokenized);
	}

	public FieldType getType() {
		return FieldType.STRING;
	}

	public String toString() {
		return "StringField [name=" + getName() + ", value="
				+ getValue() + "]";
	}
}
