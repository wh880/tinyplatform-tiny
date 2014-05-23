package org.tinygroup.compiler.impl;

import org.tinygroup.compiler.CompileException;

import java.util.Collection;

/**
 * Created by luoguo on 2014/5/21.
 */
public class FileSourceJdtCompiler extends JdtCompiler<FileSource> {

    public boolean compile(FileSource source) throws CompileException {
        StringBuffer commandLine = getCommandLine().append(" \"").append(source.getSource().getAbsolutePath()).append("\"");
        return executeCommand(commandLine.toString());
    } 

    public boolean compile(FileSource[] sources) throws CompileException {
        StringBuffer commandLine = getCommandLine();
        for(FileSource source:sources) {
            commandLine.append(" \"").append(source.getSource().getAbsolutePath()).append("\"");
        }
        return executeCommand(commandLine.toString());
    }

    public boolean compile(Collection<FileSource> sources) throws CompileException {
        StringBuffer commandLine = getCommandLine();
        for(FileSource source:sources) {
            commandLine.append(" \"").append(source.getSource().getAbsolutePath()).append("\"");
        }
        return executeCommand(commandLine.toString());
    }
   
}
