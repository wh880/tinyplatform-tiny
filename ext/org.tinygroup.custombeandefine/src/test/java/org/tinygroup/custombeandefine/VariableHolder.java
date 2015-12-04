package org.tinygroup.custombeandefine;

public class VariableHolder {
	
	private ThreadLocal<String> variable=new ThreadLocal<String>();
	
	private static VariableHolder HOLDER=new VariableHolder();
	
	private VariableHolder() {
		super();
	}
	
	public static VariableHolder getInstance(){
		return HOLDER;
	}

	public void setVariable(String value){
		variable.set(value);
	}
	
	public String getVariable(){
		return variable.get();
	}

}
