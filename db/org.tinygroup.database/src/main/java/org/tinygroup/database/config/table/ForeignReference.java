package org.tinygroup.database.config.table;

import org.tinygroup.commons.tools.EqualsUtil;
import org.tinygroup.commons.tools.HashCodeUtil;
import org.tinygroup.metadata.config.BaseObject;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 外键引用关系
 * @author renhui
 *
 */
@XStreamAlias("foreign-reference")
public class ForeignReference extends BaseObject{

	/**
	 * 外键引用的主表
	 */
	@XStreamAlias("main-table")
	private String mainTable;
	/**
	 * 外键引用的主表字段
	 */
	@XStreamAlias("reference-field")
	private String referenceField;
	/**
	 * 外键字段
	 */
	@XStreamAlias("foreign-field")
	private String foreignField;
	
	public ForeignReference() {
		super();
	}
	public ForeignReference(String name,String mainTable, String referenceField,
			String foreignField) {
		super();
		setName(name);
		this.mainTable = mainTable;
		this.referenceField = referenceField;
		this.foreignField = foreignField;
	}
	public String getMainTable() {
		return mainTable;
	}
	public void setMainTable(String mainTable) {
		this.mainTable = mainTable;
	}
	public String getReferenceField() {
		return referenceField;
	}
	public void setReferenceField(String referenceField) {
		this.referenceField = referenceField;
	}
	public String getForeignField() {
		return foreignField;
	}
	public void setForeignField(String foreignField) {
		this.foreignField = foreignField;
	}
	@Override
	public int hashCode() {
		return HashCodeUtil.reflectionCompareHashCode(this, new String[]{"mainTable","referenceField","foreignField","name"});
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsUtil.reflectionCompareEquals(this, obj, new String[]{"mainTable","referenceField","foreignField","name"});
	}
	
}