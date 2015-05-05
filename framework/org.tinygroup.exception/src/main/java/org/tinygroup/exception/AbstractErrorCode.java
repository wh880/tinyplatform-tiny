package org.tinygroup.exception;

import org.tinygroup.exception.constant.ErrorLevels;
import org.tinygroup.exception.constant.ErrorTypes;

/**
 * 抽象的错误码规范
 * 
 * @author renhui
 * 
 */
public abstract class AbstractErrorCode implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4604190154626896337L;

	/** 固定标识[第 1-2位] */
	protected static final String PREFIX = "TE";

	/** 统一错误规范默认版本 */
	protected static final String DEFAULT_VERSION = "0";

	protected String errorPrefix;

	/** 规范版本[第3位] */
	protected String version = DEFAULT_VERSION;

	/** 错误类型[第4位],见<code>ErrorTypes</code>定义 */
	protected String errorType;

	/** 错误级别[第5位],见<code>ErrorLevels</code>定义 */
	protected String errorLevel;

	/** 错误场景[第6-9位] */
	protected String errorScene;

	/** 具体错误码[第10-12位] */
	protected String errorSpecific;

	public AbstractErrorCode(String errorCode) {
		buildErrorCode(errorCode);
	}

	public AbstractErrorCode(String version, String errorType,
			String errorLevel, String errorScene, String errorSpecific,
			String errorPrefix) {
		super();
		checkPrefix(errorPrefix);
		checkVersion(version);
		checkType(errorType);
		checkLevel(errorLevel);
		checkScene(errorScene);
		checkSpecific(errorSpecific);
		this.errorPrefix = errorPrefix;
		this.version = version;
		this.errorType = errorType;
		this.errorLevel = errorLevel;
		this.errorScene = errorScene;
		this.errorSpecific = errorSpecific;
	}

	protected abstract void buildErrorCode(String errorCode);

	/**
	 * 错误场景检查逻辑，该方法需要子类实现
	 * 
	 * @param errorScene
	 */
	protected abstract void checkScene(String errorScene);

	/**
	 * 错误码检查逻辑，该方法需要子类实现
	 * 
	 * @param errorSpecific
	 */
	protected abstract void checkSpecific(String errorSpecific);

	protected void checkLevel(String errorLevel) {
		checkLength(errorLevel, 1);
		checkNumber(errorLevel);
		ErrorLevels levels = ErrorLevels.find(errorLevel);
		if (levels == null) {
			throw new IllegalArgumentException("错误级别未在ErrorLevels中定义");
		}

	}

	protected void checkType(String errorType) {
		checkLength(errorType, 1);
		checkNumber(errorType);
		ErrorTypes types = ErrorTypes.find(errorType);
		if (types == null) {
			throw new IllegalArgumentException("错误类型未在ErrorTypes中定义");
		}

	}

	protected void checkVersion(String version) {
		checkLength(version, 1);
		checkNumber(version);
	}

	protected void checkPrefix(String errorPrefix) {

	}

	/**
	 * 字符串长度检查
	 * 
	 * @param str
	 * @param length
	 */
	public void checkLength(String str, int length) {

		if (str == null || str.length() != length) {
			throw new IllegalArgumentException();
		}
	}

	public void checkNumber(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("必须是数字类型", e);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(errorPrefix);
		sb.append(version);
		sb.append(errorType);
		sb.append(errorLevel);
		sb.append(errorScene);
		sb.append(errorSpecific);
		return sb.toString();
	}

	public String getErrorPrefix() {
		return errorPrefix;
	}

	public void setErrorPrefix(String errorPrefix) {
		this.errorPrefix = errorPrefix;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(String errorLevel) {
		this.errorLevel = errorLevel;
	}

	public String getErrorScene() {
		return errorScene;
	}

	public void setErrorScene(String errorScene) {
		this.errorScene = errorScene;
	}

	public String getErrorSpecific() {
		return errorSpecific;
	}

	public void setErrorSpecific(String errorSpecific) {
		this.errorSpecific = errorSpecific;
	}

}
