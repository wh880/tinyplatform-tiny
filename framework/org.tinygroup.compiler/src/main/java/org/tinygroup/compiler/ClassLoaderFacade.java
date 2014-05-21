/**
 * jetbrick-template
 * http://subchen.github.io/jetbrick-template/
 *
 * Copyright 2010-2014 Guoqiang Chen. All rights reserved.
 * Email: subchen@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.compiler;

import org.tinygroup.compiler.utils.PathUtils;
import org.tinygroup.compiler.utils.URLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderFacade {
    private final Logger logger = LoggerFactory.getLogger(ClassLoaderFacade.class);
    private final URL[] urls;
    private final ClassLoader classloader;
    private final boolean reloadable;

    public ClassLoaderFacade(String compilePath, boolean templateReloadable, CompileStrategy compileStrategy) {
        File outputDir = getCanonicalClasspath(compilePath);
        this.urls = new URL[]{URLUtils.fromFile(outputDir)};
        this.classloader = createClassLoader();
        this.reloadable = templateReloadable && compileStrategy != CompileStrategy.none;
    }
    private static File getCanonicalClasspath(String outputdir) {
        File dir = new File(outputdir);
        // 必须先建立目录，否则 URLClassLoader 会失败
        if (!dir.mkdirs() && !dir.exists()) {
            throw new IllegalStateException("Can't create a directory in " + dir.getAbsolutePath());
        }
        return PathUtils.getCanonicalFile(dir);
    }
    /**
     * 载入类
     *
     * @param qualifiedClassName
     * @return
     * @throws ClassNotFoundException
     */
    public Class<?> loadClass(String qualifiedClassName) throws ClassNotFoundException {
        if (reloadable) {
            // 为了可以动态卸载 Class，需要每次重新 new 一个 URLClassLoader
            ClassLoader classloader = createClassLoader();
            return classloader.loadClass(qualifiedClassName);
        } else {
            return classloader.loadClass(qualifiedClassName);
        }
    }

    public  ClassLoader getContextClassLoader() {
        ClassLoader loader = null;
        try {
            loader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable e) {
        }
        if (loader == null) {
            loader = this.getClass().getClassLoader();
        }
        return loader;
    }
    private ClassLoader createClassLoader() {
        return new URLClassLoader(urls, getContextClassLoader());
    }
}
