package org.tinygroup.fulltext.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.field.Field;

/**
 * 抽象文档类
 * @author yancheng11334
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractDocument implements Document{

	protected List<Field> fields = new ArrayList<Field>();
	
	public Iterator<Field> iterator() {
		return fields.iterator();
	}

	public Field getId() {
		return getField(FullTextHelper.getStoreId());
	}
	
	public Field getType() {
		return getField(FullTextHelper.getStoreType());
	}

	public Field getTitle() {
		return getField(FullTextHelper.getStoreTitle());
	}

	public Field getAbstract() {
		return getField(FullTextHelper.getStoreAbstract());
	}


	public Field getField(String name) {
		Iterator<Field> it = iterator();
		while(it.hasNext()){
			Field field = it.next();
			if(field.getName().equals(name)){
			   return field;
			}
		}
		return null;
	}

	public List<Field> getFields(String name) {
		List<Field> result = new ArrayList<Field>();
		Iterator<Field> it = iterator();
		while(it.hasNext()){
			Field field = it.next();
			if(field.getName().equals(name)){
			  result.add(field);
			}
		}
		return result;
	}
}
