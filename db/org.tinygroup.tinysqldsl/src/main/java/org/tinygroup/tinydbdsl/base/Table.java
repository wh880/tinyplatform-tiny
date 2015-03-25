package org.tinygroup.tinydbdsl.base;

import org.tinygroup.tinydbdsl.formitem.FromItem;
import org.tinygroup.tinydbdsl.visitor.FromItemVisitor;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Table implements FromItem, MultiPartName {
	private String schemaName;
	private String name;

	private Alias alias;

	public Table() {
	}

	public Table(String name) {
		this.name = name;
	}

	public Table(String schemaName, String name) {
		this.schemaName = schemaName;
		this.name = name;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String string) {
		schemaName = string;
	}

	public String getName() {
		return name;
	}

	public void setName(String string) {
		name = string;
	}

	public Alias getAlias() {
		return alias;
	}

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	public String getFullyQualifiedName() {
		String fqn = "";

		if (schemaName != null) {
			fqn += schemaName;
		}
		if (!(fqn == null || fqn.length() == 0)) {
			fqn += ".";
		}

		if (name != null) {
			fqn += name;
		}

		return fqn;
	}

	public String toString() {
		return getFullyQualifiedName()
				+ ((alias != null) ? alias.toString() : "");
	}

	public void accept(FromItemVisitor fromItemVisitor) {
		fromItemVisitor.visit(this);
	}

}
