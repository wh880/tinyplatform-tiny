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
package org.tinygroup.compiler.impl;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.*;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tinygroup.compiler.AbstractJavaCompiler;
import org.tinygroup.compiler.CompileException;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by luoguo on 2014/5/21.
 */
public class JdtMemorySourceCompiler extends AbstractJavaCompiler<MemorySource> {
    // String sourceFolder;
    //
    // public String getSourceFolder() {
    // return sourceFolder;
    // }
    //
    // public void setSourceFolder(String sourceFolder) {
    // this.sourceFolder = sourceFolder;
    // }
    public JdtMemorySourceCompiler(String outputDirectory) {
        setOutputDirectory(outputDirectory);
    }

    public void writeJavaFile(MemorySource source) throws IOException {
        File file = new File(getPathName(getJavaFileName(source)));
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            file.createNewFile();

        }
        FileOutputStream stream = new FileOutputStream(file);
        stream.write(source.getContent().getBytes(getEncode()));
        stream.close();
    }

    private String getPathName(String fileName) {
        return getOutputDirectory() +(getOutputDirectory().endsWith(File.separator)?"":File.separatorChar)
                +fileName;
    }

    private String getJavaFileName(MemorySource source) {
        return source.getQualifiedClassName().replace(".", File.separator)
                + ".java";
    }


    public boolean compile(MemorySource source) throws CompileException {
        generateJavaClass(source);
        return true;
    }

    public boolean compile(MemorySource[] sources) throws CompileException {
        for (MemorySource memorySource : sources) {
            generateJavaClass(memorySource);
        }
        return true;
    }

    public boolean compile(Collection<MemorySource> sources) throws CompileException {
        for (MemorySource memorySource : sources) {
            generateJavaClass(memorySource);
        }
        return false;
    }

    protected void generateJavaClass(MemorySource source) throws CompileException {
        INameEnvironment env = new NameEnvironment(source);

        IErrorHandlingPolicy policy = DefaultErrorHandlingPolicies.exitOnFirstError();
        CompilerOptions options = getCompilerOptions();
        CompilerRequestor requestor = new CompilerRequestor();
        IProblemFactory problemFactory = new DefaultProblemFactory(Locale.getDefault());

        Compiler compiler = new Compiler(env, policy, options, requestor, problemFactory);
        compiler.compile(new ICompilationUnit[]{new CompilationUnit(source)});
    }

    public static CharSequence getPrettyError(String[] sourceLines, int line, int column, int start, int stop, int show_lines) {
        StringBuilder sb = new StringBuilder(128);
        for (int i = line - show_lines; i < line; i++) {
            if (i >= 0) {
                String sourceLine = sourceLines[i];

                // 1 个 Tab 变成 4 个空格
                if (i == line - 1) {
                    int origin_column = Math.min(column, sourceLine.length() - 1);
                    for (int j = 0; j < origin_column; j++) {
                        char c = sourceLine.charAt(j);
                        if (c == '\t') {
                            column += 3;
                        } else if (c >= '\u2E80' && c <= '\uFE4F') {
                            column++; // 中日韩统一表意文字（CJK Unified Ideographs）
                        }
                    }
                }
                sourceLine = sourceLine.replaceAll("\\t", "    ");
                sb.append(String.format("%4d: %s%n", i + 1, sourceLine));
            }
        }
        if (start > stop) {
            // <EOF>
            sb.append("      <EOF>\n");
            sb.append("      ^^^^^");
        } else {
            sb.append("      "); // padding
            for (int i = 0; i < column - 1; i++) {
                sb.append(' ');
            }
            for (int i = start; i <= stop; i++) {
                sb.append('^');
            }
        }
        sb.append('\n');
        return sb;
    }

    class CompilationUnit implements ICompilationUnit {
        final MemorySource source;

        public CompilationUnit(MemorySource source) {
            this.source = source;
        }


        public char[] getFileName() {
            return getJavaSourcePath(source.getQualifiedClassName()).toCharArray();
        }


        public char[] getContents() {
            return source.getContent().toCharArray();
        }

        private String getJavaSourcePath(String className) {
            String fileName = getPathName( className.replace('.', File.separatorChar) + ".java");
            return fileName;
        }

        public char[] getMainTypeName() {
            String qualifiedClassName = source.getQualifiedClassName();
            int dot = qualifiedClassName.lastIndexOf('.');
            if (dot > 0) {
                return qualifiedClassName.substring(dot + 1).toCharArray();
            }
            return qualifiedClassName.toCharArray();
        }


        public char[][] getPackageName() {
            String []names=source.getQualifiedClassName().split("[.]");
            char[][]result=new char[names.length-1][];
            for(int i=0;i<names.length-1;i++){
                result[i]=names[i].toCharArray();
            }
            return result;
        }


        public boolean ignoreOptionalProblems() {
            return false;
        }
    }

    class NameEnvironment implements INameEnvironment {
        final Logger log = LoggerFactory.getLogger(NameEnvironment.class);
        final Map<String, Boolean> cache = new HashMap<String, Boolean>();
        final MemorySource source;

        public NameEnvironment(MemorySource source) {
            this.source = source;
        }


        public NameEnvironmentAnswer findType(char[][] compoundTypeName) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < compoundTypeName.length; i++) {
                if (i > 0) {
                    sb.append('.');
                }
                sb.append(compoundTypeName[i]);
            }
            return findType(sb.toString());
        }


        public NameEnvironmentAnswer findType(char[] typeName, char[][] packageName) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < packageName.length; i++) {
                sb.append(packageName[i]).append('.');
            }
            sb.append(typeName);
            return findType(sb.toString());
        }
        private NameEnvironmentAnswer findType(final String className) {

            if (className.equals(source.getQualifiedClassName())) {
                return new NameEnvironmentAnswer(new CompilationUnit(source), null);
            }

            // find by system
            try {
                InputStream input = this.getClass().getClassLoader().getResourceAsStream(className.replace(".", "/") + ".class");
                if (input != null) {
                    byte[] bytes = new byte[(int) input.available()];
                    input.read(bytes);
                    input.close();
                    if (bytes != null) {
                        ClassFileReader classFileReader = new ClassFileReader(bytes, className.toCharArray(), true);
                        return new NameEnvironmentAnswer(classFileReader, null);
                    }
                }
            } catch (ClassFormatException e) {
                // Something very very bad
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return null;
        }


        public boolean isPackage(char[][] parentPackageName, char[] packageName) {
            StringBuilder sb = new StringBuilder();
            if (parentPackageName != null) {
                for (char[] p : parentPackageName) {
                    sb.append(p).append('.');
                }
            }
            sb.append(packageName);
            String name = sb.toString();

            Boolean found = cache.get(name);
            if (found != null) {
                return found.booleanValue();
            }

            boolean isPackage = !isJavaClass(name);
            cache.put(name, isPackage);
            return isPackage;
        }

        private boolean isJavaClass(String name) {
            if (name.equals(source.getQualifiedClassName())) {
                return true;
            }

            String resourceName = name.replace('.', '/') + ".class";
            URL url = this.getClass().getClassLoader().getResource(resourceName);
            return url != null;
        }


        public void cleanup() {
        }
    }

     class CompilerRequestor implements ICompilerRequestor {

        public void acceptResult(CompilationResult result) {
            if (result.hasErrors()) {
                StringBuffer sb=new StringBuffer();
                for (IProblem problem : result.getErrors()) {
                    String className = new String(problem.getOriginatingFileName()).replace("/", ".");
                    className = className.substring(0, className.length() - 5);
                    String message = problem.getMessage();
                    if (problem.getID() == IProblem.CannotImportPackage) {
                        // Non sense !
                        message = problem.getArguments()[0] + " cannot be resolved";
                    }
                    sb.append(className).append(":").append(message).append("\n");
                }
                throw new RuntimeException(sb.toString());
            } else {
                save(result.getClassFiles());
            }
        }


        public void save(ClassFile[]classFiles)  {
            if (classFiles == null) return;

            for (ClassFile classFile : classFiles) {
                String fileName = new String(classFile.fileName()) + ".class";
                File javaClassFile = new File(getOutputDirectory(), fileName);
                System.out.println(javaClassFile.getAbsolutePath());
                File pa=javaClassFile.getParentFile();
                if(!pa.exists()){
                    pa.mkdirs();
                }
                try {
                    FileOutputStream fout = new FileOutputStream(javaClassFile);
                    BufferedOutputStream bos = new BufferedOutputStream(fout);
                    bos.write(classFile.getBytes());
                    bos.close();
                    fout.close();
                }catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
