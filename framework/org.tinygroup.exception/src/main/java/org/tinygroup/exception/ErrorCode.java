/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
package org.tinygroup.exception;

import java.io.Serializable;

import org.tinygroup.exception.constant.ErrorLevel;
import org.tinygroup.exception.constant.ErrorType;

/**
 *
 * Created by luog on 15/5/18.
 */
public interface ErrorCode extends Serializable{

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
