package org.tinygroup.fulltext.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.field.Field;
import org.tinygroup.fulltext.field.FieldType;
import org.tinygroup.fulltext.field.HighlightField;


/**
 * 高亮渲染结果文档
 * @author yancheng11334
 *
 */
@SuppressWarnings("rawtypes")
public class HighlightDocument {
	
	private List<Field> fields = new ArrayList<Field>();
	/**
	 * 默认构造
	 * @param document  要渲染的文档
	 */
	public HighlightDocument(Document document){
		this(document, new Object[]{});
	}
	
	/**
	 * 包装构建
	 * @param document  要渲染的文档
	 * @param arguments 渲染参数
	 */
	public HighlightDocument(Document document,Object... arguments){
        Iterator<Field> it = document.iterator();
        while(it.hasNext()){
        	Field field = it.next();
        	//支持高亮
        	if(field instanceof HighlightField){
        		HighlightField f = (HighlightField) field;
        		fields.add(new FieldWrapper(f,arguments));
        	}else{
        		fields.add(field);
        	}
        }
	}
	
	public String getId(){
		Field f = getField(FullTextHelper.getStoreId());
		return f==null?null:f.getValue().toString();
	}
	
	public String getType(){
		Field f = getField(FullTextHelper.getStoreType());
		return f==null?null:f.getValue().toString();
	}
	
	public String getTitle(){
		Field f = getField(FullTextHelper.getStoreTitle());
		return f==null?null:f.getValue().toString();
	}
	
	public String getAbstract(){
		Field f = getField(FullTextHelper.getStoreAbstract());
		return f==null?null:f.getValue().toString();
	}
	
	private Field getField(String name) {
		for(Field field:fields){
		   if(field.getName().equals(name)){
			   return field;
		   }
		}
		return null;
	}
    
	class FieldWrapper implements Field{
        private HighlightField field;
        private Object[] arguments;
    	public FieldWrapper(HighlightField hField,Object... arguments){
    		this.field = hField;
    		this.arguments = arguments;
    	}
		public String getName() {
			return field.getName();
		}

		public FieldType getType() {
			return field.getType();
		}

		public Object getValue() {
			if(arguments==null || arguments.length<=0){
				//返回原值
				return field.getSourceValue();
			}else{
				//返回包装值
				return field.getRenderValue(arguments);
			}
		}
    	
    }
}
