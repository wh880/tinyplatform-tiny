package org.tinygroup.weixin.exception;


/**
 * 微信服务标准异常
 * @author yancheng11334
 *
 */
public class WeiXinException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2070842491939967063L;

	public WeiXinException(){
		super();
	}
	
    public WeiXinException(String message){
		super(message);
	}
    
    public WeiXinException(String message, Throwable cause){
    	super(message,cause);
    }
    
    public WeiXinException(Throwable cause){
    	super(cause);
    }
}
