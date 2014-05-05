/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.annotation.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 封装classname和annotationType正则表达式的类
 * 
 * @author renhui
 * 
 */
public class AnnotationTypeMatcher {
    // 匹配的类名正则表达式
	private List<Pattern> classPatterns = new ArrayList<Pattern>();
    // 注解正则表达式
	private Pattern annotationPattern;

	public AnnotationTypeMatcher(List<String> classNames, String annotationType) {
		if (classNames != null) {
			for (String name : classNames) {
				Pattern pattern = Pattern.compile(name);
				classPatterns.add(pattern);
			}
		}
		annotationPattern = Pattern.compile(annotationType);
	}

	/**
	 * 传入的路径是否匹配已有的类路径正则
	 * 
	 * @param classFullPath
	 * @return
	 */
	public boolean isMatch(String classFullPath) {
		for (Pattern pattern : classPatterns) {
			Matcher matcher = pattern.matcher(classFullPath);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 传入的注解是否匹配注解正则
	 * 
	 * @param annotation
	 * @return
	 */
	public boolean isAnnotationTypeMatch(Annotation annotation) {
		Matcher matcher = annotationPattern.matcher(annotation.annotationType()
				.getName());
		return matcher.matches();
	}
}
