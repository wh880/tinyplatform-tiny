package org.tinygroup.cepcorenetty.test;

import java.util.ArrayList;
import java.util.List;

public class Processor {
	private List<Item> list = new ArrayList<Item>();

	public void add(Item i){
		list.add(i);
	}
	
	public boolean contain(String name){
		for(Item i:list){
			if(i.getName().equals(name))
				return true;
		}
		return false;
	}
	
	public List<Item> get(){
		return list;
	}
	
	public String get(String name){
		for(Item i:list){
			if(i.getName().equals(name)){
				return i.getValue();
			}
		}
		return null;
	}

	public Processor(int itemNum,int begin,String value){
		for(int i = 0; i < itemNum ; i ++,begin++){
			Item item = new Item();
			item.setName(value+begin);
			item.setValue(value+begin);
			this.add(item);
		}
	}
}
