package org.tinygroup.chinese;

/**
 * Created by luog on 15/4/15.
 */
public class ParserException extends  Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6738730979384915045L;
	public ParserException(String information){
        super(information);
    }
    public ParserException(Throwable throwable){
        super(throwable);
    }
}
