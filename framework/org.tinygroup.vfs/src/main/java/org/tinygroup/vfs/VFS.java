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
package org.tinygroup.vfs;

import org.tinygroup.vfs.impl.*;
import org.tinygroup.vfs.impl.ftp.FtpSchemaProvider;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 虚拟文件系统
 *
 * @author luoguo
 */
public class VFS {
    static Map<String, FileObject> fileObjectCacheMap = new ConcurrentHashMap<String, FileObject>();
    static Map<String, Long> fileModifyTimeMap = new ConcurrentHashMap<String, Long>();
    static final Map<String, SchemaProvider> schemaProviderMap = new HashMap<String, SchemaProvider>();
    private static String defaultSchema = "file:";

    static {
        addSchemaProvider(new JarSchemaProvider());
        addSchemaProvider(new ZipSchemaProvider());
        addSchemaProvider(new FileSchemaProvider());
        addSchemaProvider(new HttpSchemaProvider());
        addSchemaProvider(new HttpsSchemaProvider());
        addSchemaProvider(new FtpSchemaProvider());
        addSchemaProvider(new JBossVfsSchemaProvider());
    }

    /**
     * 构建函数私有化
     */
    private VFS() {

    }

    /**
     * 清空Cache
     */
    public static final void clearCache() {
        fileObjectCacheMap.clear();
    }

    /**
     * 添加新的模式提供者
     *
     * @param schemaProvider
     */
    public static final void addSchemaProvider(SchemaProvider schemaProvider) {
        schemaProviderMap.put(schemaProvider.getSchema(), schemaProvider);
    }

    /**
     * 设置默认模式提供者
     *
     * @param schema
     */
    public static final void setDefaultSchemaProvider(String schema) {
        defaultSchema = schema;
    }

    /**
     * 返回指定的模式提供者
     *
     * @param schema
     * @return
     */
    public static final SchemaProvider getSchemaProvider(String schema) {
        return schemaProviderMap.get(schema);
    }

    /**
     * 解析文件
     *
     * @param resource
     * @return
     */
    public static FileObject resolveFile(String resource) {
        FileObject fileObject = fileObjectCacheMap.get(resource);
        if (fileObject != null && fileObject.isInPackage()) {
            long oldTime = fileModifyTimeMap.get(resource);
            long newTime = fileObject.getLastModifiedTime();
            if (oldTime == newTime) {
                return fileObject;
            }
        }
        try {
            resource = URLDecoder.decode(resource, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // 如果出错也不用管，忽略之
        }
        SchemaProvider schemaProvider = schemaProviderMap.get(defaultSchema);
        for (SchemaProvider provider : schemaProviderMap.values()) {
            if (provider.isMatch(resource)) {
                schemaProvider = provider;
                break;
            }
        }
        fileObject = schemaProvider.resolver(resource);
        if (fileObject != null && fileObject.isInPackage()) {
            fileObjectCacheMap.put(resource, fileObject);
            fileModifyTimeMap.put(resource, fileObject.getLastModifiedTime());
        }
        return fileObject;
    }

    /**
     * 解析URL
     *
     * @param url
     * @return
     */
    public static FileObject resolveURL(URL url) {
        return resolveFile(url.getPath());
    }


}
