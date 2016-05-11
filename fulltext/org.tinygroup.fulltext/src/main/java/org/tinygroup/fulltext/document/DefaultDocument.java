package org.tinygroup.fulltext.document;

import java.util.Iterator;
import java.util.List;

import org.tinygroup.fulltext.field.Field;

/**
 * 默认的文档对象
 * @author yancheng11334
 *
 */
@SuppressWarnings("rawtypes")
public class DefaultDocument extends AbstractDocument implements Document{
	
	public void addField(Field field){
		fields.add(field);
	}
	
	public void addFields(List<Field> fieldList){
		fields.addAll(fieldList);
	}
	
	public void deleteField(String name){
		Iterator<Field> it = iterator();
		while(it.hasNext()){
			Field field = it.next();
			if(field.getName().equals(name)){
			   it.remove();
			   return;
			}
		}
	}
	
	public void deleteFields(String name){
		Iterator<Field> it = iterator();
		while(it.hasNext()){
			Field field = it.next();
			if(field.getName().equals(name)){
			   it.remove();
			}
		}
	}

}
