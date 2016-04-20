package org.tinygroup.flow.test;

import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;

public class ListComponent implements ComponentInterface{
	public List<String> list;
	public String str;
	public int ii;
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public int getIi() {
		return ii;
	}
	public void setIi(int ii) {
		this.ii = ii;
	}
	public void execute(Context context) {
		context.put("ii1", ii);
		context.put("str1", str);
		context.put("list1", list);
	}
	
}	
