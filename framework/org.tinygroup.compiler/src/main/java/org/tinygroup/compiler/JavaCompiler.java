package org.tinygroup.compiler;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * 定义Java编译器的接口
 * Created by luoguo on 2014/5/21.
 */
public interface JavaCompiler {
    String getEncode();

    /**
     * 编译一个Java源文件
     *
     * @param source
     * @return
     */
    Class<?> compile(JavaSource source) throws CompileException;

    void initialize();

    /**
     * 编译一组Java源文件
     *
     * @param sources
     * @return
     */
    Class<?>[] compile(JavaSource[] sources) throws CompileException;

    /**
     * 编译一组Java源文件
     *
     * @param sources
     * @return
     */
    Collection<Class<?>> compile(Collection<JavaSource> sources) throws CompileException;

    void setClassLoaderFacade(ClassLoaderFacade classLoaderFacade);

    ClassLoaderFacade getClassLoaderFacade();

    /**
     * 返回是否允许调试
     *
     * @return
     */
    boolean isDebugEnabled();

    /**
     * 设置是否允许调试
     *
     * @param debugEnabled
     */
    void setDebugEnabled(boolean debugEnabled);

    File getOutputDirectory();

    void setOutputDirectory(File outputDirector);

    void writeJavaSource(JavaSource javaSource, String encode) throws IOException;

    File getJavaSourceFile(String className);

    String getJavaSourcePath(String className);

    File getJavaClassFile(String className);

    String getJavaClassPath(String className);

}
