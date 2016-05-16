package org.tinygroup.fulltext.field;

public class LongField extends AbstractField<Long> implements StoreField<Long>{

	public LongField(String name, Long value) {
		this(name,value,true,true,false);
	}
	
	public LongField(String name, String value) {
		this(name,value,true,true,false);
	}
	
	public LongField(String name, Long value,boolean indexed,boolean stored,boolean tokenized) {
		super(name, value);
		this.setIndexed(indexed);
		this.setStored(stored);
		this.setTokenized(tokenized);
	}
	
	public LongField(String name, String value,boolean indexed,boolean stored,boolean tokenized) {
		this(name,Long.parseLong(value),indexed,stored,tokenized);
	}

	public FieldType getType() {
		return FieldType.LONG;
	}


	public String toString() {
		return "LongField [name=" + getName() + ", value="
				+ getValue() + "]";
	}


}
