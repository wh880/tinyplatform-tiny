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
package org.tinygroup.vfs.impl.filter;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luoguo on 14-2-26.
 */
public class FilePathFileObjectFilter implements FileObjectFilter {
    private Pattern pattern;
    /**
     * 是否完全匹配，默认是局部区域即可
     */
    private boolean fullMatch = false;

    public FilePathFileObjectFilter(String path) {
        pattern = Pattern.compile(path);
    }

    public FilePathFileObjectFilter(String fileNamePattern, boolean fullMatch) {
        this(fileNamePattern);
        this.fullMatch = fullMatch;
    }

    public boolean accept(FileObject fileObject) {
        String filePath = fileObject.getPath();//取得文件路径
        if (fullMatch) {
        	//完全匹配，不仅对文件路径进行匹配，还要对匹配组(group)进行对比。
            Matcher matcher = pattern.matcher(filePath);
            if (matcher.find()) {
                return matcher.group().equals(filePath);
            } else {
                return false;
            }
        } else {
        	//局部匹配，直接用设置的正则表达式对文件路径进行匹配
            Matcher matcher = pattern.matcher(filePath);
            return matcher.find();
        }
    }
}
