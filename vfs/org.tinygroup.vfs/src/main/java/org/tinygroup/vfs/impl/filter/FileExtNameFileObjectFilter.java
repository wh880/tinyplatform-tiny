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

/**
 * 根据文件扩展名进行文件过滤
 * Created by luoguo on 14-2-26.
 */
public class FileExtNameFileObjectFilter implements FileObjectFilter {
    private String fileExtName;
    /**
     * 是否大小写敏感，默认不敏感
     */
    private boolean caseSensitive = false;

    public FileExtNameFileObjectFilter(String fileExtName) {
        this.fileExtName = fileExtName;
    }

    public FileExtNameFileObjectFilter(String fileExtName, boolean caseSensitive) {
        this(fileExtName);
        this.caseSensitive = caseSensitive;
    }

    public boolean accept(FileObject fileObject) {
        String extName = fileObject.getExtName();//获取文件扩展名
        if (extName != null) {
            if (caseSensitive) {
            	//大小写敏感，进行精确匹配
                return extName.equals(fileExtName);
            } else {
            	//大小写不敏感，进行忽略大小写匹配
                return extName.equalsIgnoreCase(fileExtName);
            }
        }
        //如果扩展名为空值，默认返回不匹配
        return false;
    }
}
