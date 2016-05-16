package org.tinygroup.fulltext.field;


public class BinaryField extends AbstractField<byte[]> implements StoreField<byte[]>{

	public BinaryField(String name, byte[] value) {
		this(name, value,true,true,false);
	}
	
	public BinaryField(String name, byte[] value,boolean indexed,boolean stored,boolean tokenized) {
		super(name, value);
		this.setIndexed(indexed);
		this.setStored(stored);
		this.setTokenized(tokenized);
	}

	public FieldType getType() {
		return FieldType.BINARY;
	}
	
	public String toString() {
		return "BinaryField [name=" + getName() + ", value="
				+ getValue() + "]";
	}

}
