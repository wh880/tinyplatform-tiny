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
package org.tinygroup.exception.constant;

/**
 * 错误级别常量池。
 * 
 * <p>对应于标准错误码的第5位：
 *      <table border="1">
 *          <tr>
 *           <td><b>位置</b></td><td>1</td><td>2</td><td>3</td><td>4</td><td bgcolor="green">5</td><td>6</td><td>7</td><td>8</td><td>9</td><td>10</td><td>11</td><td>12</td>
 *          </tr>
 *          <tr>
 *           <td><b>示例</b></td><td>A</td><td>E</td><td>0</td><td>1</td><td>0</td><td>1</td><td>0</td><td>1</td><td>1</td><td>0</td><td>2</td><td>7</td>
 *          </tr>
 *          <tr>
 *           <td><b>说明</b></td><td colspan=2>固定<br>标识</td><td>规<br>范<br>版<br>本</td><td>错<br>误<br>类<br>型</td><td>错<br>误<br>级<br>别</td><td colspan=4>错误场景</td><td colspan=3>错误编<br>码</td>
 *          </tr>
 *          </table>
 * 
 */
public enum ErrorLevel {
	   /** FATAL级别 */
	FATAL(1),

	 /** ERROR级别 */
	ERROR(2);
	
	
	private int level;
	
    private ErrorLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
    
	public static ErrorLevel find(String code) {
		for (ErrorLevel level : ErrorLevel.values()) {
			if (code.equals(level.getLevel()+"")) {
				return level;
			}
		}
		throw new RuntimeException(String.format("未找到code:%s，对应的错误级别", code));
	}
}
	
