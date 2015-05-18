package org.tinygroup.exception;

/**
 * 错误码解析接口
 * @author renhui
 *
 */
public interface ErrorCodeParser {
   /**
    * 错误码长度匹配
    * @param errorNumber
    * @return
    */
	boolean isMatch(String errorNumber);
	
	/**
	 * 通过错误码字符串实例化真正的错误码对象
	 * @param errorCode
	 */
	ErrorCode parse(String errorCode);
}
