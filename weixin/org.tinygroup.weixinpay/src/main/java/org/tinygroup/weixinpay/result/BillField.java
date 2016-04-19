package org.tinygroup.weixinpay.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 账单的动态字段
 * @author yancheng11334
 *
 */
public class BillField {
	 
	private Map<String,String> maps = new HashMap<String,String>();
	
	public  String  getValue(String name){
		return maps.get(name);
	}
	
	public void setValue(String name,String value){
		maps.put(name, value);
	}
}
