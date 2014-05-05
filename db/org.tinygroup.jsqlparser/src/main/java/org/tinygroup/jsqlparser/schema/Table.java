/*
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2013 JSQLParser
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 2.1 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package org.tinygroup.jsqlparser.schema;

import org.tinygroup.jsqlparser.expression.Alias;
import org.tinygroup.jsqlparser.statement.select.FromItem;
import org.tinygroup.jsqlparser.statement.select.FromItemVisitor;
import org.tinygroup.jsqlparser.statement.select.IntoTableVisitor;
import org.tinygroup.jsqlparser.statement.select.Pivot;

/**
 * A table. It can have an alias and the schema name it belongs to.
 */
public class Table implements FromItem, MultiPartName {

    private Database database;
    private String schemaName;
    private String name;

    private Alias alias;
    private Pivot pivot;

    public Table() {
    }

    public Table(String name) {
        this.name = name;
    }

    public Table(String schemaName, String name) {
        this.schemaName = schemaName;
        this.name = name;
    }

    public Table(Database database, String schemaName, String name) {
        this.database = database;
        this.schemaName = schemaName;
        this.name = name;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
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

        if (database != null) {
            fqn += database.getFullyQualifiedName();
        }
        if (!(fqn==null||fqn.length()==0)) {
            fqn += ".";
        }

        if (schemaName != null) {
            fqn += schemaName;
        }
        if (!(fqn==null||fqn.length()==0)) {
            fqn += ".";
        }

        if (name != null) {
            fqn += name;
        }

        return fqn;
    }


    public void accept(FromItemVisitor fromItemVisitor) {
        fromItemVisitor.visit(this);
    }

    public void accept(IntoTableVisitor intoTableVisitor) {
        intoTableVisitor.visit(this);
    }


    public Pivot getPivot() {
        return pivot;
    }


    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }


    public String toString() {
        return getFullyQualifiedName()
               + ((pivot != null) ? " " + pivot : "")
               + ((alias != null) ? alias.toString() : "");
    }
}
