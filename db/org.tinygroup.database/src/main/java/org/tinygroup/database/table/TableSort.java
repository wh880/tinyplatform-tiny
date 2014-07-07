package org.tinygroup.database.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.database.config.table.ForeignReference;
import org.tinygroup.database.config.table.Table;

public class TableSort implements Comparator<Table> {

	public int compare(Table table1, Table table2) {
		List<String> dependTables1 = getDependList(table1);
		List<String> dependTables2 = getDependList(table2);
		boolean isEmpty1 = CollectionUtil.isEmpty(dependTables1);
		boolean isEmpty2 = CollectionUtil.isEmpty(dependTables2);
		if (isEmpty1||isEmpty2) {
			boolean contains1 = dependTables1.contains(table2.getName());
			boolean contains2 = dependTables2.contains(table1.getName());
			if (contains1 && contains2) {
				throw new RuntimeException(String.format(
						"表1[name:%s]与表2[name:%s]相互依赖", table1.getName(),
						table2.getName()));
			} else if (contains1) {
				return 1;
			} else if (contains2) {
				return -1;
			}
		}
		return 0;
	}

	private List<String> getDependList(Table table) {
		List<ForeignReference> foreigns = table.getForeignReferences();
		List<String> dependencies = new ArrayList<String>();
		for (ForeignReference foreignReference : foreigns) {
			dependencies.add(foreignReference.getMainTable());
		}
		return dependencies;
	}
	
	public static void main(String[] args) {
		
		Table table1=new Table();
		table1.setId("personId");
		table1.setName("person");
		List<ForeignReference> foreigns=new ArrayList<ForeignReference>();
		ForeignReference reference=new ForeignReference("sds", "city", "dss", "dsdf");
		foreigns.add(reference);
		table1.setForeignReferences(foreigns);
		Table table2=new Table();
		table2.setId("cityId");
		table2.setName("city");
		
		Table table3=new Table();
		table3.setId("fdff");
		table3.setName("fddd");
		List<Table> tables=new ArrayList<Table>();
		tables.add(table1);
		tables.add(table2);
		tables.add(table3);
		Collections.sort(tables, new TableSort());
		
		for (Table table : tables) {
			System.out.println(table.getName());
		}
		
		
	}
	
}


