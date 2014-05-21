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
package org.tinygroup.compiler.impl;

import org.tinygroup.compiler.ClassLoaderFacade;
import org.tinygroup.compiler.CompileException;
import org.tinygroup.compiler.JavaCompiler;
import org.tinygroup.compiler.JavaSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractJavaCompiler implements JavaCompiler {
    private ClassLoaderFacade classLoaderFacade;
    private boolean debugEnabled;
    private String encode = "UTF-8";
    private File outputDirectory;

    public ClassLoaderFacade getClassLoaderFacade() {
        return classLoaderFacade;
    }

    public void setClassLoaderFacade(ClassLoaderFacade classLoaderFacade) {
        this.classLoaderFacade = classLoaderFacade;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirector) {
        this.outputDirectory = outputDirector;
    }

    public static JavaCompiler create(ClassLoaderFacade classLoaderFacade, Class<JavaCompiler> clazz, boolean compileDebug) {
        try {
            JavaCompiler javaCompiler = (AbstractJavaCompiler) clazz.newInstance();
            javaCompiler.setClassLoaderFacade(classLoaderFacade);
            javaCompiler.setDebugEnabled(compileDebug);
            javaCompiler.initialize();
            return javaCompiler;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }


    public Class<?> compile(JavaSource source) throws CompileException {
        try {
            getJavaSourceFile(source.getQualifiedClassName()).delete();
            writeJavaSource(source, outputDirectory, getEncode());
            generateJavaClass(source);

            return classLoaderFacade.loadClass(source.getQualifiedClassName());
        } catch (IOException e) {
            throw new CompileException(e);
        } catch (ClassNotFoundException e) {
            throw new CompileException(e);
        }
    }

    public Class<?>[] compile(JavaSource[] sources) throws CompileException {
        Class<?>[] results = new Class[sources.length];
        for (int i = 0; i < sources.length; i++) {
            results[i] = compile(sources[i]);
        }
        return results;
    }

    public Collection<Class<?>> compile(Collection<JavaSource> sources) throws CompileException {
        ArrayList<Class<?>> results = new ArrayList<Class<?>>();
        for (JavaSource javaSource : sources) {
            results.add(compile(javaSource));
        }
        return results;
    }

    public void writeJavaSource(JavaSource javaSource, String encode) throws IOException {
        writeJavaSource(javaSource, outputDirectory, encode);
    }

    public File getJavaSourceFile(String className) {
        return new File(getPath(className, ".java"));

    }

    public File getJavaClassFile(String className) {
        return new File(getPath(className, ".class"));

    }

    public String getJavaSourcePath(String className) {
        return getPath(className, ".java");

    }

    public String getJavaClassPath(String className) {
        return getPath(className, ".class");

    }

    private String getPath(String className, String extFileName) {
        String fileName = outputDirectory.getAbsolutePath() + File.separatorChar + className.replace('.', File.separatorChar) + extFileName;
        return fileName;
    }

    public void writeJavaSource(JavaSource javaSource, File sourceDir, String encode) throws IOException {
        if (!sourceDir.exists()) {
            sourceDir.mkdirs();
        }
        OutputStream out = new FileOutputStream(getJavaSourceFile(javaSource.getQualifiedClassName()));
        try {
            out.write(javaSource.getQualifiedClassName().getBytes(encode));
        } finally {
            out.close();
        }
    }

    protected abstract void generateJavaClass(JavaSource source) throws IOException, CompileException;
}
