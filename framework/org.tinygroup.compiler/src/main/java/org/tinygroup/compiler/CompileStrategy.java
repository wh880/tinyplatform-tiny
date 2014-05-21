package org.tinygroup.compiler;

/**
 * Created by luoguo on 2014/5/21.
 */
public enum CompileStrategy {
    precompile, // 启动时自动对所有模板进行进行编译
    always, // 第一次访问的时候，开始编译
    auto, // 第一次访问的时候，如果存在 .class 文件，并模板源文件没有修改过，则直接加载 .class，否则进行编译
    none, // 直接加载存在的 .class 文件，否则报错(无需模板源文件存在)，class 文件必须放在 classpath 下面
}
