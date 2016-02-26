package org.tinygroup.httpvisitor.struct;

/**
 * URL请求参数(框架构建，无需考虑包装)
 * @author yancheng11334
 *
 */
public class Parameter {
    
	String name;
	
	Object value;
	
	public Parameter(String name,Object value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
