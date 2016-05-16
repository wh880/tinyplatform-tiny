package org.tinygroup.fulltext.field;

public class DoubleField extends AbstractField<Double> implements StoreField<Double>{

	public DoubleField(String name, Double value) {
		this(name,value,true,true,false);
	}
	
	public DoubleField(String name, String value) {
		this(name,value,true,true,false);
	}
	
	public DoubleField(String name, Double value,boolean indexed,boolean stored,boolean tokenized) {
		super(name, value);
		this.setIndexed(indexed);
		this.setStored(stored);
		this.setTokenized(tokenized);
	}
	
	public DoubleField(String name, String value,boolean indexed,boolean stored,boolean tokenized) {
		this(name,Double.parseDouble(value),indexed,stored,tokenized);
	}

	public FieldType getType() {
		return FieldType.DOUBLE;
	}

	public String toString() {
		return "DoubleField [name=" + getName() + ", value="
				+ getValue() + "]";
	}

}
