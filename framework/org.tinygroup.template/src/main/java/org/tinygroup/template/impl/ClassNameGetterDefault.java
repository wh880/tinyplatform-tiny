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
package org.tinygroup.template.impl;

import org.tinygroup.template.loader.ClassNameGetter;

/**
 * Created by luoguo on 2014/6/9.
 */
public class ClassNameGetterDefault implements ClassNameGetter {

    private String convertGoodStylePath(String path) {
        StringBuffer sb = new StringBuffer(200);
        //除了下面的几种情况，全部替换成"_"
        for (char c : path.toCharArray()) {
            if (c == '/' ) {
                sb.append(c);
                sb.append("p_");
            } else if (c >= '0' && c <= '9') {
                sb.append(c);
            } else if (c >= 'a' && c <= 'z') {
                sb.append(c);
            } else if (c >= 'A' && c <= 'Z') {
                sb.append(c);
            } else {
                sb.append("_");
            }
        }
        return sb.toString();
    }


    public ClassName getClassName(String path) {
        String name = path;
        name = convertGoodStylePath(name);
        //去掉前置"/"
        if (name.startsWith("/")) {
            name = name.substring(1);
        }
        ClassName className = new ClassName();
        String fullClassName = name.replaceAll("/", ".");
        className.setClassName(fullClassName);
        className.setSimpleClassName(fullClassName.substring(fullClassName.lastIndexOf('.') + 1));
        if (fullClassName.indexOf('.') > 0) {
            className.setPackageName(fullClassName.substring(0, fullClassName.lastIndexOf('.')));
        }
        return className;
    }
}
