package org.tinygroup.fulltext.exception;

/**
 * 全文检索异常
 * @author yancheng11334
 *
 */
public class FullTextException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5212305943513874678L;

	public FullTextException(){
		super();
	}
	
    public FullTextException(String message){
		super(message);
	}
    
    public FullTextException(String message, Throwable cause){
    	super(message,cause);
    }
    
    public FullTextException(Throwable cause){
    	super(cause);
    }
}
