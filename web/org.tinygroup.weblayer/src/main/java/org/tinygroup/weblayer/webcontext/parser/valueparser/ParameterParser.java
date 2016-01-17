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
package org.tinygroup.weblayer.webcontext.parser.valueparser;

import org.apache.commons.fileupload.FileItem;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.weblayer.webcontext.parser.exception.UploadException;
import org.tinygroup.weblayer.webcontext.parser.upload.UploadParameters;

/**
 * <code>ParameterParser</code>是用来解析HTTP请求中GET或POST的参数的接口。
 *
 * @author renhui
 */
public interface ParameterParser extends ValueParser {
    /**
     * 取得指定名称的<code>FileItem</code>对象，如果不存在，则返回<code>null</code>。
     *
     * @param key 参数名
     * @return <code>FileItem</code>对象
     */
    FileObject getFileObject(String key);

    /**
     * 取得指定名称的<code>FileItem</code>对象，如果不存在，则返回<code>null</code>。
     *
     * @param key 参数名
     * @return <code>FileItem</code>对象的数组
     */
    FileObject[] getFileObjects(String key);

    /**
     * 添加<code>FileItem</code>。
     *
     * @param name  参数名
     * @param value 参数值
     */
    void add(String name, FileItem value);
    
    /**
     * 获取所有文件上传内容
     * @return
     */
    FileItem[] getFileItems();

    /**
     * 解析符合<a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>标准的
     * <code>multipart/form-data</code>类型的HTTP请求。
     * <p>
     * 要执行此方法，须将<code>UploadService.automatic</code>配置参数设置成<code>false</code>。
     * 此方法覆盖了service的默认设置，适合于在action或servlet中手工执行。
     * </p>
     *
     * @throws UploadException 如果解析时出现异常
     */
    void parseUpload() throws UploadException;

    /**
     * 解析符合<a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>标准的
     * <code>multipart/form-data</code>类型的HTTP请求。
     * <p>
     * 要执行此方法，须将<code>UploadService.automatic</code>配置参数设置成<code>false</code>。
     * 此方法覆盖了service的默认设置，适合于在action或servlet中手工执行。
     * </p>
     *
     * @param sizeThreshold  文件放在内存中的阈值，小于此值的文件被保存在内存中。如果此值小于0，则使用预设的值
     * @param sizeMax        HTTP请求的最大尺寸，超过此尺寸的请求将被抛弃。
     * @param repositoryPath 暂存上载文件的绝对路径
     * @throws UploadException 如果解析时出现异常
     */
    void parseUpload(UploadParameters params) throws UploadException;

    /**
     * 将parameters重新组装成query string。
     *
     * @return query string，如果没有参数，则返回<code>null</code>
     */
    String toQueryString();
}
