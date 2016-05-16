package org.tinygroup.fulltext.field;


/**
 * 复杂对象字段(其他字段无法描述,可以用这个字段包装)
 * @author yancheng11334
 *
 */
public class ObjectField extends AbstractField<Object> implements StoreField<Object>{

	public ObjectField(String name, Object value) {
		this(name,value,true,true,false);
	}
	
	public ObjectField(String name, Object value,boolean indexed,boolean stored,boolean tokenized) {
		super(name, value);
		this.setIndexed(indexed);
		this.setStored(stored);
		this.setTokenized(tokenized);
	}

	public FieldType getType() {
		return FieldType.OBJECT;
	}

	public String toString() {
		return "ObjectField [name=" + getName() + ", value="
				+ getValue() + "]";
	}

}
