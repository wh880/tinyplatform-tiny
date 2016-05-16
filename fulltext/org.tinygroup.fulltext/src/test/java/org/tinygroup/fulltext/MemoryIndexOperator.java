package org.tinygroup.fulltext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.field.Field;

public class MemoryIndexOperator implements IndexOperator{
	
    private Map<String,Document> results = new HashMap<String,Document>();
    
	public void createIndex(List<Document> docs) {
		for(Document doc:docs){
			results.put((String)doc.getId().getValue(), doc);
		}
	}

	public void deleteIndex(List<Field> docIds) {
		for(Field field:docIds){
			results.remove(field.getValue());
		}
	}

	public Pager<Document> search(String searchCondition, int start, int limit) {
		List<Document> list = new ArrayList<Document>();
        String[] keys = searchCondition.split(",");
        for(Entry<String,Document> entry:results.entrySet()){
        	boolean tag = isMatch(keys,entry.getValue());
        	if(tag){
        	   list.add(entry.getValue());
        	}
        }
        int total = list.size();
        if(start<total && start>0){
            list = list.subList(start, total);
        }
        if(limit<list.size() && limit >0){
        	list = list.subList(0, limit);
        }
        
        Pager<Document> page = new Pager<Document>(total,start,limit,list);
		return page;
	}
	
	private boolean isMatch(String[] keys,Document doc){
		Iterator<Field> it = doc.iterator();
		while(it.hasNext()){
			Field field = it.next();
			String content = field.getValue().toString();
			for(String key:keys){
			   if(content.contains(key)){
				  return true;
			   }
			}
		}
		return false;
	}

}
