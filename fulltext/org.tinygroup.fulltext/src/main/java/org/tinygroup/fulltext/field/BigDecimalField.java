package org.tinygroup.fulltext.field;

import java.math.BigDecimal;

public class BigDecimalField extends AbstractField<BigDecimal> implements StoreField<BigDecimal>{

	public BigDecimalField(String name, BigDecimal value) {
		this(name, value,true,true,false);
	}
	
	public BigDecimalField(String name, BigDecimal value,boolean indexed,boolean stored,boolean tokenized) {
		super(name, value);
		this.setIndexed(indexed);
		this.setStored(stored);
		this.setTokenized(tokenized);
	}

	public FieldType getType() {
		return FieldType.BIGDECIMAL;
	}

	public String toString() {
		return "BigDecimalField [name=" + getName() + ", value="
				+ getValue() + "]";
	}
	
	

}
