package org.tinygroup.fulltext.field;

import java.util.Date;

public class DateField extends AbstractField<Date> implements StoreField<Date>{

	public DateField(String name, Date value) {
		this(name, value,true,true,false);
	}
	
	public DateField(String name, Date value,boolean indexed,boolean stored,boolean tokenized) {
		super(name, value);
		this.setIndexed(indexed);
		this.setStored(stored);
		this.setTokenized(tokenized);
	}

	public FieldType getType() {
		return FieldType.DATE;
	}


	public String toString() {
		return "DateField [name=" + getName() + ", value="
				+ getValue() + "]";
	}
}
