package org.tinygroup.fulltext.field;

public class IntField extends AbstractField<Integer> implements StoreField<Integer>{

	public IntField(String name, Integer value) {
		this(name,value,true,true,false);
	}
	
	public IntField(String name, String value) {
		this(name,value,true,true,false);
	}
	
	public IntField(String name, Integer value,boolean indexed,boolean stored,boolean tokenized) {
		super(name, value);
		this.setIndexed(indexed);
		this.setStored(stored);
		this.setTokenized(tokenized);
	}
	
	public IntField(String name, String value,boolean indexed,boolean stored,boolean tokenized) {
		this(name,Integer.parseInt(value),indexed,stored,tokenized);
	}

	public FieldType getType() {
		return FieldType.INT;
	}


	public String toString() {
		return "IntField [name=" + getName() + ", value="
				+ getValue() + "]";
	}


}
