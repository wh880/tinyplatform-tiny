package org.tinygroup.menucommand.exception;

public class MenuCommandException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2516940153965456346L;

	public MenuCommandException(){
		super();
	}
	
    public MenuCommandException(String message){
		super(message);
	}
    
    public MenuCommandException(String message, Throwable cause){
    	super(message,cause);
    }
    
    public MenuCommandException(Throwable cause){
    	super(cause);
    }
}
