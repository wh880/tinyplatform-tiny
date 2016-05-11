package org.tinygroup.lucene472.wrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.field.Field;

/**
 * 查询结果包装
 * @author yancheng11334
 *
 */
@SuppressWarnings("rawtypes")
public class DocumentWrapper implements Document{

	private List<Field> fields = new ArrayList<Field>();
	
	public DocumentWrapper(List<Field> newFields){
		this.fields = newFields;
	}
	
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
		for(Field field:fields){
			if(field.getName().equals(name)){
			   return field;
			}
		}
		return null;
	}

	public List<Field> getFields(String name) {
		List<Field> newFields = new ArrayList<Field>();
		for(Field field:fields){
			if(field.getName().equals(name)){
				newFields.add(field);
			}
		}
		return newFields;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("Document[");
		Iterator<Field> it = iterator();
		while(it.hasNext()){
			sb.append(it.next().toString()).append(",");
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append("]");
		return sb.toString();
	}
	
}
