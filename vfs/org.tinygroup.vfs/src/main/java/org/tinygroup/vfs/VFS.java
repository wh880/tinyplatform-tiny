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
package org.tinygroup.vfs;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tinygroup.vfs.impl.FileSchemaProvider;
import org.tinygroup.vfs.impl.FtpSchemaProvider;
import org.tinygroup.vfs.impl.HttpSchemaProvider;
import org.tinygroup.vfs.impl.HttpsSchemaProvider;
import org.tinygroup.vfs.impl.JBossVfsSchemaProvider;
import org.tinygroup.vfs.impl.JarSchemaProvider;
import org.tinygroup.vfs.impl.WsJarSchemaProvider;
import org.tinygroup.vfs.impl.ZipSchemaProvider;

/**
 * 虚拟文件系统
 *
 * @author luoguo
 */
public final class VFS {
    private static Map<String, FileObject> fileObjectCacheMap = new ConcurrentHashMap<String, FileObject>();
    private static Map<String, Long> fileModifyTimeMap = new ConcurrentHashMap<String, Long>();
    private static Map<String, SchemaProvider> schemaProviderMap = new HashMap<String, SchemaProvider>();
    private static String defaultSchema = "file:";

    static {
        addSchemaProvider(new JarSchemaProvider());//注册本地jar模式提供者
        addSchemaProvider(new WsJarSchemaProvider());//注册wsjar协议的模式提供者
        addSchemaProvider(new ZipSchemaProvider());//注册本地zip模式提供者
        addSchemaProvider(new FileSchemaProvider());//注册file协议的模式提供者
        addSchemaProvider(new HttpSchemaProvider());//注册http协议的模式提供者
        addSchemaProvider(new HttpsSchemaProvider());//注册https协议的模式提供者
        addSchemaProvider(new JBossVfsSchemaProvider());//注册vfs虚拟协议的模式提供者
        addSchemaProvider(new FtpSchemaProvider());//注册ftp协议的模式提供者
    }

    /**
     * 构建函数私有化
     */
    private VFS() {

    }

    /**
     * 清空Cache
     */
    public static void clearCache() {
        fileObjectCacheMap.clear();
    }

    /**
     * 添加新的模式提供者
     *
     * @param schemaProvider
     */
    public static  void addSchemaProvider(SchemaProvider schemaProvider) {
        schemaProviderMap.put(schemaProvider.getSchema(), schemaProvider);
    }

    /**
     * 设置默认模式提供者
     *
     * @param schema
     */
    public static  void setDefaultSchemaProvider(String schema) {
        defaultSchema = schema;
    }

    /**
     * 返回指定的模式提供者
     *
     * @param schema
     * @return
     */
    public static  SchemaProvider getSchemaProvider(String schema) {
        return schemaProviderMap.get(schema);
    }

    /**
     * 解析文件
     *
     * @param resourceResolve
     * @return
     */
    public static FileObject resolveFile(String resourceResolve) {
        String resource=resourceResolve;
        //根据协议地址从缓存中查询FileObject
        FileObject fileObject = fileObjectCacheMap.get(resource);
        if (fileObject != null && fileObject.isInPackage()) {
        	//检查FileObject的最近修改时间戳和缓存中的是否一致，如果一致的话就直接返回结果
            long oldTime = fileModifyTimeMap.get(resource);
            long newTime = fileObject.getLastModifiedTime();
            if (oldTime == newTime) {
                return fileObject;
            }
        }
        try {
        	//采用utf-8编码对协议地址进行解码
            resource = URLDecoder.decode(resource, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // 如果出错也不用管，忽略之
        }
        //取得默认的模式提供者FileSchemaProvider
        SchemaProvider schemaProvider = schemaProviderMap.get(defaultSchema);
        for (SchemaProvider provider : schemaProviderMap.values()) {
        	//遍历模式提供者，判断协议地址是否匹配当前模式提供者
            if (provider.isMatch(resource)) {
                schemaProvider = provider;
                break;
            }
        }
        //返回解析结果
        fileObject = schemaProvider.resolver(resource);
        //如果fileObject是包资源，则更新fileObject缓存和时间戳信息
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
