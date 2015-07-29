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
package org.tinygroup.springmvc.util;


public final class WebUtil {
	
	public static String getExtension(String urlPath) {
		int end = urlPath.indexOf(';');
		if (end == -1) {
			end = urlPath.indexOf('?');
			if (end == -1) {
				end = urlPath.length();
			}
		}
		int dotIndex = urlPath.lastIndexOf('.');
		if (dotIndex != -1 && dotIndex < end) {
			return urlPath.substring(dotIndex + 1, end);
		}
		return null;
	}

	public static String getUriWithoutExtension(String urlPath) {
		int end = urlPath.indexOf(';');
		if (end == -1) {
			end = urlPath.indexOf('?');
			if (end == -1) {
				end = urlPath.length();
			}
		}
		int dotIndex = urlPath.lastIndexOf('.');
		if (dotIndex != -1 && dotIndex < end) {
			return urlPath.substring(0, dotIndex);
		}
		return urlPath;
	}

}
