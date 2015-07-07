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
 * <td>0</td>
 * <td>T</td>
 * <td>E</td>
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
 * <td>规<br>
 * 范<br>
 * 版<br>
 * 本</td>
 * <td colspan=2>固定<br>
 * 标识</td>
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
public class ErrorCodeDefault extends AbstractErrorCode {
    public static final String VERSION = "0";

    /**
     * 未知系统异常
     */
    public static final String UNKNOWN_ERROR = "0TE129999999";

    /**
     * 未知系统异常
     */
    public static final String UNKNOWN_SYSTEM_ERROR = "0TE129999999";

    /**
     * 未知扩展系统异常
     */
    public static final String UNKNOWN_EXT_ERROR = "0TE229999999";
    /**
     * 未知业务异常
     */
    public static final String UNKNOWN_BIZ_ERROR = "0TE329999999";

    /**
     * 未知第三方异常
     */
    public static final String UNKNOWN_THIRD_PARTY_ERROR = "0TE429999999";

    // 这里是定义长度
    private static final int[] FIELD_LENGTH = {1, 2, 1, 1, 4, 3};
    /**
     *
     */
    private static final long serialVersionUID = -8398330229603671639L;


    public ErrorCodeDefault() {
        super();
    }

    public ErrorCodeDefault(ErrorType errorType, ErrorLevel errorLevel,
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
        return "%1s%2s%1d%1d%04d%03d";
    }

    public boolean isMatch(String errorCodeStr) {
        return errorCodeStr.startsWith(VERSION);
    }

}
