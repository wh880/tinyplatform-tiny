package org.tinygroup.mockservice.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Obj {
	List<Obj2> list2 =new ArrayList<Obj2>();
	List<Obj1> list1 =new ArrayList<Obj1>();
	Map<String,Obj2> map2 = new HashMap<String, Obj2>();
	Obj2[] array2 ;
	public Obj2[] getArray2() {
		return array2;
	}
	public void setArray2(Obj2[] array2) {
		this.array2 = array2;
	}
	public List<Obj2> getList2() {
		return list2;
	}
	public void setList2(List<Obj2> list2) {
		this.list2 = list2;
	}
	public List<Obj1> getList1() {
		return list1;
	}
	public void setList1(List<Obj1> list1) {
		this.list1 = list1;
	}
	public Map<String, Obj2> getMap2() {
		return map2;
	}
	public void setMap2(Map<String, Obj2> map2) {
		this.map2 = map2;
	}
	
	
}
