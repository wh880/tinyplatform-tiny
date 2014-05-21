package org.tinygroup.compiler.impl;

import org.tinygroup.compiler.CompileException;

import java.util.Collection;

/**
 * Created by luoguo on 2014/5/21.
 */
public class FileSourceJdtCompiler extends JdtCompiler<FileSource> {

    public void compile(FileSource source) throws CompileException {
        StringBuffer commandLine = getCommandLine().append(" \"").append(source.getSource().getAbsolutePath()).append("\"");
        executeCommand(commandLine.toString());
    }

    public void compile(FileSource[] sources) throws CompileException {
        StringBuffer commandLine = getCommandLine();
        for(FileSource source:sources) {
            commandLine.append(" \"").append(source.getSource().getAbsolutePath()).append("\"");
        }
        executeCommand(commandLine.toString());
    }

    public void compile(Collection<FileSource> sources) throws CompileException {
        StringBuffer commandLine = getCommandLine();
        for(FileSource source:sources) {
            commandLine.append(" \"").append(source.getSource().getAbsolutePath()).append("\"");
        }
        executeCommand(commandLine.toString());
    }
    public static void main(String[] args) throws CompileException {
        FileSourceJdtCompiler compiler=new FileSourceJdtCompiler();
        FileSource[] fileSources = {new FileSource("E:\\test"),new FileSource("E:\\test1")};
        compiler.compile(fileSources);
    }
}
