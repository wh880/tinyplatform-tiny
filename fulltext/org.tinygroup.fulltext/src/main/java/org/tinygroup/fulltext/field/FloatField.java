package org.tinygroup.fulltext.field;

public class FloatField extends AbstractField<Float> implements StoreField<Float>{

	public FloatField(String name, Float value) {
		this(name,value,true,true,false);
	}
	
	public FloatField(String name, String value) {
		this(name,value,true,true,false);
	}
	
	public FloatField(String name, Float value,boolean indexed,boolean stored,boolean tokenized) {
		super(name, value);
		this.setIndexed(indexed);
		this.setStored(stored);
		this.setTokenized(tokenized);
	}
	
	public FloatField(String name, String value,boolean indexed,boolean stored,boolean tokenized) {
		this(name,Float.parseFloat(value),indexed,stored,tokenized);
	}

	public FieldType getType() {
		return FieldType.FLOAT;
	}
	
	public String toString() {
		return "FloatField [name=" + getName() + ", value="
				+ getValue() + "]";
	}


}
