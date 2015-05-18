package org.tinygroup.exception;

import org.tinygroup.exception.constant.ErrorLevel;
import org.tinygroup.exception.constant.ErrorType;

/**
 *
 * Created by luog on 15/5/18.
 */
public interface ErrorCode {

    String getErrorPrefix();

    void setErrorPrefix(String errorPrefix);

    String getVersion();

    void setVersion(String version);

    ErrorType getErrorType();

    void setErrorType(ErrorType errorType);

    ErrorLevel getErrorLevel();

    void setErrorLevel(ErrorLevel errorLevel);

    int getErrorScene();

    void setErrorScene(int errorScene);

    int getErrorNumber();

    void setErrorNumber(int errorNumber);
}
