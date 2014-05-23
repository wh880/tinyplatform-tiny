package org.tinygroup.compiler.impl;

import org.eclipse.jdt.core.compiler.batch.BatchCompiler;
import org.tinygroup.compiler.AbstractJavaCompiler;
import org.tinygroup.compiler.Source;

import java.io.PrintWriter;

/**
 * Created by luoguo on 2014/5/21.
 */
public abstract class JdtCompiler<S extends Source> extends AbstractJavaCompiler<S> {
    protected StringBuffer getCommandLine() {
        StringBuffer stringBuffer=new StringBuffer();
        return stringBuffer;
    }

    protected boolean executeCommand(String commandLine) {
        return BatchCompiler.compile(
                commandLine,
                getOutPrintWriter() != null ? getOutPrintWriter() : new PrintWriter(System.out),
                getErrPrintWriter() != null ? getErrPrintWriter() : new PrintWriter(System.out),
                getCompilationProgress());
    }
}
