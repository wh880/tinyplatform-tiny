package org.tinygroup.exception.constant;

/**
 * 保留错误码返回接口。
 */
public interface ReservedErrorCodeGetter {

    /**
     * 未知异常
     */
    String getUnknownErrorCode(ErrorType errorType);
}
