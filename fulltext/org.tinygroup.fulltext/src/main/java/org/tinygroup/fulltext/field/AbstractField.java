package org.tinygroup.fulltext.field;

public abstract class AbstractField<T> {

    private String name;
	
    private T value;
    
    private boolean indexed;
    
    private boolean stored;
    
    private boolean tokenized;
	
	public AbstractField(String name,T value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public T getValue() {
		return value;
	}

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

	public boolean isStored() {
		return stored;
	}

	public void setStored(boolean stored) {
		this.stored = stored;
	}

	public boolean isTokenized() {
		return tokenized;
	}

	public void setTokenized(boolean tokenized) {
		this.tokenized = tokenized;
	}

}
