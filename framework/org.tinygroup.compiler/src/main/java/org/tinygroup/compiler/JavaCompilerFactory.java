package org.tinygroup.compiler;

/**
 * 工厂接口，用于创建Java编译器
 * Created by luoguo on 2014/5/21.
 */
public interface JavaCompilerFactory {
    JavaCompiler createJavaCompiler();
}
