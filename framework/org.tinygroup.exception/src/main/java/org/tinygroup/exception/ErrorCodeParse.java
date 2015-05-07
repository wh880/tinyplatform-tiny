package org.tinygroup.exception;

/**
 * 错误码持有接口
 * @author renhui
 *
 */
public interface ErrorCodeParse {
   /**
    * 错误码长度匹配
    * @param errorCodeLength
    * @return
    */
	boolean isLengthMatch(int errorCodeLength);
	
	/**
	 * 通过错误码字符串实例化真正的错误码对象
	 * @param errorCode
	 */
	AbstractErrorCode parse(String errorCode);
}
