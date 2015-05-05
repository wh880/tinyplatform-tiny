package org.tinygroup.exception;

/**
 * 标准错误对象。
 * 
 * <p>标准错误对象包含:
 * <ul>
 *  <li>标准错误码</li>
 *  <li>错误默认文案</li>
 *  <li>错误产生位置</li>
 * </ul>
 * <p>标准错误对象是一次错误处理结果的描述。
 *      
 */
public class CommonError implements java.io.Serializable {

    /** 序列ID */
    private static final long serialVersionUID = -5421371978945260773L;

    /** 错误编码 */
    private ErrorCode         errorCode;

    /** 错误描述 */
    private String            errorMsg;

    /** 错误发生系统 */
    private String            location;

    // ~~~ 构造方法

    /**
     * 默认构造方法
     */
    public CommonError() {
    }

    /**
     * 构造方法
     * 
     * @param code
     * @param msg
     */
    public CommonError(ErrorCode code, String msg, String location) {
        this.errorCode = code;
        this.errorMsg = msg;
        this.location = location;
    }

    // ~~~ 公共方法

    /**
     * 转化为简单字符串表示。
     * 
     * @return
     */
    public String toDigest() {

        return errorCode + "@" + location;
    }

    // ~~~ 重写方法

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return errorCode + "@" + location + "::" + errorMsg;
    }

    // ~~~ bean方法

    /**
     * Getter method for property <tt>errorCode</tt>.
     * 
     * @return property value of errorCode
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for property <tt>errorCode</tt>.
     * 
     * @param errorCode value to be assigned to property errorCode
     */
    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>errorMsg</tt>.
     * 
     * @return property value of errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Setter method for property <tt>errorMsg</tt>.
     * 
     * @param errorMsg value to be assigned to property errorMsg
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * Getter method for property <tt>location</tt>.
     * 
     * @return property value of location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter method for property <tt>location</tt>.
     * 
     * @param location value to be assigned to property location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
