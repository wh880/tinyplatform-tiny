/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.exception.errorcode;

import org.tinygroup.exception.ErrorCode;
import org.tinygroup.exception.ErrorCodeParser;
import org.tinygroup.exception.constant.ErrorLevel;
import org.tinygroup.exception.constant.ErrorType;

import java.io.Serializable;

/**
 * 错误码抽象类
 * 
 * @author renhui
 */
public abstract class AbstractErrorCode implements Serializable,ErrorCodeParser, ErrorCode,Cloneable {
	public static final int VERSION = 0, PREFIX = 1, TYPE = 2, LEVEL = 3, SCENE = 4,
			NUMBER = 5;

	/**
     *
     */
	private static final long serialVersionUID = 4604190154626896337L;

	/**
	 * 默认错误前缀，TE表示TinyError的意思
	 */
	protected static final String DEFAULT_PREFIX = "TE";

	/**
	 * 统一错误规范默认版本
	 */
	protected static final String DEFAULT_VERSION = "1";
	/**
	 * 错误前缀
	 */
	protected String errorPrefix = DEFAULT_PREFIX;

	/**
	 * 错误规范版本，错误规范的版本不同，表示某些位数的长度不同
	 */
	protected String version = DEFAULT_VERSION;

	/**
	 * 错误类型<code>ErrorTypes</code>定义
	 */
	protected ErrorType errorType;

	/**
	 * 错误级别,见<code>ErrorLevels</code>定义
	 */
	protected ErrorLevel errorLevel;

	/**
	 * 错误场景
	 */
	protected int errorScene;

	/**
	 * 具体错误码
	 */
	protected int errorNumber;

	/**
	 * @return
	 */
	protected abstract int[] getFieldLength();

	protected abstract String getErrorCodeFormatString();

	public AbstractErrorCode() {
		super();
	}

	public AbstractErrorCode(String version, ErrorType errorType,
			ErrorLevel errorLevel, int errorScene, int errorNumber,
			String errorPrefix) {

		assertLength(VERSION, version);
		assertLength(PREFIX, errorPrefix);
		assertLength(SCENE, errorScene + "");
		assertLength(NUMBER, errorNumber + "");
		this.version = version;
		this.errorPrefix = errorPrefix;
		this.errorType = errorType;
		this.errorLevel = errorLevel;
		this.errorScene = errorScene;
		this.errorNumber = errorNumber;
	}

	public ErrorCode parse(String errorCode) {
		char[] chars = errorCode.toCharArray();
		try {
			ErrorCode code= (ErrorCode) this.clone();
			int position=0;
			code.setVersion( errorCode.substring(position,getFieldLength()[0]));
			position+=getFieldLength()[0];
			code.setErrorPrefix( errorCode.substring(position,position+getFieldLength()[1]));
			position+=getFieldLength()[1];
			code.setErrorType(ErrorType.find(chars[position++] + ""));
			code.setErrorLevel(ErrorLevel.find(chars[position++] + ""));
			code.setErrorScene(Integer.parseInt(errorCode.substring(position, position+getFieldLength()[4])));
			position+=getFieldLength()[4];
			code.setErrorNumber(Integer.parseInt(errorCode.substring(position, position+getFieldLength()[5])));
			return code;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	protected void assertLength(int field, String errorPrefix) {
		if (errorPrefix == null
				|| errorPrefix.length() != getFieldLength()[field]) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(getErrorCodeFormatString(), version,errorPrefix,
				errorType.getType(), errorLevel.getLevel(), errorScene,
				errorNumber);
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

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	public ErrorLevel getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(ErrorLevel errorLevel) {
		this.errorLevel = errorLevel;
	}

	public int getErrorScene() {
		return errorScene;
	}

	public void setErrorScene(int errorScene) {
		this.errorScene = errorScene;
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}
	
}
