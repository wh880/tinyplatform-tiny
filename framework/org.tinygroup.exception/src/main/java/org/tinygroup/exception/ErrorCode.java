package org.tinygroup.exception;

import org.tinygroup.exception.constant.ErrorLevel;
import org.tinygroup.exception.constant.ErrorType;

/**
 * <p/>
 * 此错误码是全站推行的标准错误码。
 * <p/>
 * 标准错误码的格式如下：
 * <table border="1">
 * <tr>
 * <td><b>位置</b></td>
 * <td>1</td>
 * <td>2</td>
 * <td>3</td>
 * <td>4</td>
 * <td>5</td>
 * <td>6</td>
 * <td>7</td>
 * <td>8</td>
 * <td>9</td>
 * <td>10</td>
 * <td>11</td>
 * <td>12</td>
 * </tr>
 * <tr>
 * <td><b>示例</b></td>
 * <td>T</td>
 * <td>E</td>
 * <td>0</td>
 * <td>1</td>
 * <td>1</td>
 * <td>1</td>
 * <td>0</td>
 * <td>1</td>
 * <td>1</td>
 * <td>0</td>
 * <td>2</td>
 * <td>7</td>
 * </tr>
 * <tr>
 * <td><b>说明</b></td>
 * <td colspan=2>固定<br>
 * 标识</td>
 * <td>规<br>
 * 范<br>
 * 版<br>
 * 本</td>
 * <td>错<br>
 * 误<br>
 * 类<br>
 * 型</td>
 * <td>错<br>
 * 误<br>
 * 级<br>
 * 别</td>
 * <td colspan=4>错误场景</td>
 * <td colspan=3>错误编<br>
 * 码</td>
 * </tr>
 * </table>
 */
public class ErrorCode extends AbstractErrorCode {

	// 这里是定义长度
	private static final int[] FIELD_LENGTH = { 2, 1, 1, 1, 4, 3 };
	/**
     *
     */
	private static final long serialVersionUID = -8398330229603671639L;
	

	public ErrorCode() {
		super();
	}

	public ErrorCode(String errorCode) {
		super(errorCode);
	}

	public ErrorCode(ErrorType errorType, ErrorLevel errorLevel,
			int errorScene, int errorNumber, String errorPrefix) {
		super(DEFAULT_VERSION, errorType, errorLevel, errorScene, errorNumber,
				errorPrefix);
	}

	@Override
	protected int[] getFieldLength() {
		return FIELD_LENGTH;
	}

	@Override
	protected String getErrorCodeFormatString() {
		return "%2s%1s%1d%1d%04d%03d";
	}

	@Override
	protected AbstractErrorCode getReserveErrorCode(ErrorType errorType) {
		if (errorType == null) {
			errorType = ErrorType.FRAMEWORK;
		}
		AbstractErrorCode errorCode = new ErrorCode(errorType,
				ErrorLevel.ERROR, 9999, 999, errorPrefix);
		return errorCode;
	}

	@Override
	protected void internalParse(char[] chars) {
		this.errorScene = Integer.valueOf("" + chars[5] + chars[6] + chars[7]
				+ chars[8]);
		this.errorNumber = Integer.valueOf("" + chars[9] + chars[10]
				+ chars[11]);
	}
	
}
