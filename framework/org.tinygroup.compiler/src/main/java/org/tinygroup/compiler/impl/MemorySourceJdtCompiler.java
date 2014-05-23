package org.tinygroup.compiler.impl;

import org.tinygroup.compiler.CompileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by luoguo on 2014/5/21.
 */
public class MemorySourceJdtCompiler extends JdtCompiler<MemorySource> {
    String sourceFolder;

    public String getSourceFolder() {
        return sourceFolder;
    }

    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    void writeJavaFile(MemorySource source) throws IOException {
        File file = new File(sourceFolder + File.separatorChar + getJavaFileName(source));
        FileOutputStream stream = new FileOutputStream(file);
        stream.write(source.getContent().getBytes(getEncode()));
        stream.close();
    }

    private String getJavaFileName(MemorySource source) {
        return source.getQualifiedClassName().replaceAll(".", File.separator) + ".java";
    }

    public boolean compile(MemorySource source) throws CompileException {
        try {
            writeJavaFile(source);
            StringBuffer commandLine = getCommandLine().append(" \"").append(sourceFolder).append("\"");
            return executeCommand(commandLine.toString());
        } catch (IOException e) {
            throw new CompileException(e);
        }
    }

    public boolean compile(MemorySource[] sources) throws CompileException {
        try {
            for(MemorySource source:sources) {
                writeJavaFile(source);
            }
            StringBuffer commandLine = getCommandLine().append(" \"").append(sourceFolder).append("\"");
            return executeCommand(commandLine.toString());
        } catch (IOException e) {
            throw new CompileException(e);
        }
    }

    public boolean compile(Collection<MemorySource> sources) throws CompileException {
        try {
            for(MemorySource source:sources) {
                writeJavaFile(source);
            }
            StringBuffer commandLine = getCommandLine().append(" \"").append(sourceFolder).append("\"");
            return executeCommand(commandLine.toString());
        } catch (IOException e) {
            throw new CompileException(e);
        }
    }
}
