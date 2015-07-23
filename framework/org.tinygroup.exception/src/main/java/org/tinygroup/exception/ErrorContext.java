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
package org.tinygroup.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * 错误上下文对象。
 * 
 * <p>错误上下文对象包含标准错误对象的堆栈，和第三方错误信息。
 * 
 */
public class ErrorContext implements java.io.Serializable {

    /** 序列ID */
    private static final long   serialVersionUID = 6436061876740168390L;

    /** 错误堆栈集合 */
    private List<Error>   errorStack       = new ArrayList<Error>();

    /** 第三方错误原始信息 */
    private String              thirdPartyError;

    /** 默认分隔符 */
    private static final String SPLIT            = "|";

    // ~~~ 构造方法

    /**
     * 默认构造方法
     */
    public ErrorContext() {
    }

    // ~~~ 公共方法

    /**
     * 获取当前错误对象
     * 
     * @return
     */
    public Error fetchCurrentError() {

        if (!errorStack.isEmpty()) {
            return errorStack.get(errorStack.size() - 1);
        }
        return null;
    }

    /**
     * 获取当前错误码
     * 
     * @return
     */
    public String fetchCurrentErrorCode() {

        if (!errorStack.isEmpty()) {

            return errorStack.get(errorStack.size() - 1).getErrorCode().toString();
        }
        return null;
    }

    /**
     * 获取原始错误对象
     * 
     * @return
     */
    public Error fetchRootError() {

        if (!errorStack.isEmpty()) {
            return errorStack.get(0);
        }
        return null;
    }

    /**
     * 向堆栈中添加错误对象。
     * 
     * @param error
     */
    public void addError(Error error) {

        if (errorStack == null) {

            errorStack = new ArrayList<Error>();
        }
        errorStack.add(error);
    }

    /**
     * 转化为简单字符串表示。
     * 
     * @return
     */
    public String toDigest() {

    	StringBuilder sb = new StringBuilder();

        for (int i = errorStack.size(); i > 0; i--) {

            if (i == errorStack.size()) {

                sb.append(digest(errorStack.get(i - 1)));
            } else {

                sb.append(SPLIT).append(digest(errorStack.get(i - 1)));
            }
        }
        return sb.toString();
    }

    // ~~~ 重写方法

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int i = errorStack.size(); i > 0; i--) {

            if (i == errorStack.size()) {

                sb.append(errorStack.get(i - 1));
            } else {

                sb.append(SPLIT).append(errorStack.get(i - 1));
            }
        }
        return sb.toString();
    }

    // ~~~ 内部方法

    /**
     * 获取错误对象简单表示
     * 
     * @param error
     * @return
     */
    private String digest(Error error) {

        if (null == error) {

            return null;
        }

        return error.toDigest();
    }

    // ~~~ bean方法

    /**
     * Getter method for property <tt>errorStack</tt>.
     * 
     * @return property value of errorStack
     */
    public List<Error> getErrorStack() {
        return errorStack;
    }

    /**
     * Setter method for property <tt>errorStack</tt>.
     * 
     * @param errorStack value to be assigned to property errorStack
     */
    public void setErrorStack(List<Error> errorStack) {
        this.errorStack = errorStack;
    }

    /**
     * Getter method for property <tt>thirdPartyError</tt>.
     * 
     * @return property value of thirdPartyError
     */
    public String getThirdPartyError() {
        return thirdPartyError;
    }

    /**
     * Setter method for property <tt>thirdPartyError</tt>.
     * 
     * @param thirdPartyError value to be assigned to property thirdPartyError
     */
    public void setThirdPartyError(String thirdPartyError) {
        this.thirdPartyError = thirdPartyError;
    }
}
