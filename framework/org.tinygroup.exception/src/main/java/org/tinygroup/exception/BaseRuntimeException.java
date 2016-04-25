/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.exception;

import org.tinygroup.commons.i18n.LocaleUtil;
import org.tinygroup.commons.tools.ExceptionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.exception.util.ErrorUtil;
import org.tinygroup.i18n.I18nMessage;
import org.tinygroup.i18n.I18nMessageFactory;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -1141168272047460629L;
    private static final I18nMessage i18nMessage = I18nMessageFactory
            .getI18nMessages();// 需要在启动的时候注入进来
    private String errorMsg;
	private static Pattern pattern = Pattern.compile("[{](.)*?[}]");

    private ErrorCode errorCode;

    public BaseRuntimeException(String errorCode, Object... params) {
        this(errorCode, "", LocaleUtil.getContext().getLocale(), params);
    }

    public BaseRuntimeException(String errorCode, String defaultErrorMsg,
                                Locale locale, Object... params) {
        String errorI18nMsg = i18nMessage.getMessage(errorCode, locale,
                defaultErrorMsg, params);
        if(StringUtil.isBlank(errorI18nMsg)){
        	this.errorMsg=format(errorCode, params);
        }else{
        	this.errorMsg=errorI18nMsg;
        }
        initErrorCode(errorCode, errorI18nMsg);
    }

    public BaseRuntimeException(String errorCode, Throwable throwable,
                                Object... params) {
        this(errorCode, "", LocaleUtil.getContext().getLocale(), throwable,
                params);
    }


    public BaseRuntimeException(String errorCode, String defaultErrorMsg,
                                Throwable throwable, Object... params) {
        this(errorCode, defaultErrorMsg, LocaleUtil.getContext().getLocale(),
                throwable, params);
    }

    public BaseRuntimeException(String errorCode, String defaultErrorMsg,
                                Locale locale, Throwable throwable, Object... params) {
        super(throwable);
        String errorI18nMsg = i18nMessage.getMessage(errorCode, locale,
                defaultErrorMsg, params);
        if(StringUtil.isBlank(errorI18nMsg)){
        	this.errorMsg=format(errorCode, params);
        }else{
        	this.errorMsg=errorI18nMsg;
        }
        initErrorCode(errorCode, errorI18nMsg);
    }

    public BaseRuntimeException(String errorCode, Context context, Locale locale) {
        this(errorCode, "", context, locale);
    }

    public BaseRuntimeException(String errorCode, String defaultErrorMsg,
                                Context context, Locale locale) {
        String errorI18nMsg = i18nMessage.getMessage(errorCode, defaultErrorMsg,
                context, locale);
        if(StringUtil.isBlank(errorI18nMsg)){
        	this.errorMsg=i18nMessage.format(errorCode, context);
        }else{
        	this.errorMsg=errorI18nMsg;
        }
        initErrorCode(errorCode, errorI18nMsg);
    }

    public BaseRuntimeException(String errorCode, Context context) {
        this(errorCode, "", context, LocaleUtil.getContext().getLocale());
    }

    public BaseRuntimeException() {
        super();
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    public static ErrorContext getErrorContext(Throwable throwable) {
        ErrorContext errorContext = new ErrorContext();
        List<Throwable> causes = ExceptionUtil.getCauses(throwable, true);
        for (Throwable cause : causes) {
            if (cause instanceof BaseRuntimeException) {
                BaseRuntimeException exception = (BaseRuntimeException) cause;
                ErrorUtil.makeAndAddError(errorContext,
                        exception.getErrorCode(), exception.getMessage());
            }
        }
        return errorContext;
    }

    @Override
    public String getMessage() {
        StringBuffer msgBuffer = new StringBuffer();
        if (errorCode == null) {
            if (StringUtil.isBlank(errorMsg))
                return super.getMessage();
            msgBuffer.append(errorMsg);
        } else {
            msgBuffer.append(String.format("[%s]", errorCode));
            if (!StringUtil.isBlank(errorMsg)) msgBuffer.append(" : ").append(errorMsg);
        }
        return msgBuffer.toString();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    private void initErrorCode(String errorCode, String errorI18nMsg) {
        try {
            this.errorCode = ErrorCodeFactory.parseErrorCode(errorCode, this);
        } catch (Exception e) {//兼容以前错误码没有规范的处理,扑捉异常不外抛
            this.errorCode = null;
        }
    }

    /**
     * Check whether this exception contains an exception of the given type:
     * either it is of the given class itself or it contains a nested cause of
     * the given type.
     *
     * @param exType the exception type to look for
     * @return whether there is a nested exception of the specified type
     */
    public boolean contains(Class exType) {
        if (exType == null) {
            return false;
        }
        if (exType.isInstance(this)) {
            return true;
        }
        Throwable cause = getCause();
        if (cause == this) {
            return false;
        }
        if (cause instanceof BaseRuntimeException) {
            return ((BaseRuntimeException) cause).contains(exType);
        } else {
            while (cause != null) {
                if (exType.isInstance(cause)) {
                    return true;
                }
                if (cause.getCause() == cause) {
                    break;
                }
                cause = cause.getCause();
            }
            return false;
        }
    }
    
    private String format(String message, Object... args) {
		Matcher matcher = pattern.matcher(message);
		StringBuilder stringBuffer = new StringBuilder();
		int start = 0;
		int count = 0;
		while (matcher.find(start)) {
			stringBuffer.append(message.substring(start, matcher.start()));
			stringBuffer.append(args[count++]);
			start = matcher.end();
		}
		stringBuffer.append(message.substring(start, message.length()));
		return stringBuffer.toString();
	}

}
