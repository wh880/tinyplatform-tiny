package org.tinygroup.template.compiler;

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
import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.template.TemplateException;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MemorySourceCompiler {
    public <T> Class<T> loadClass(MemorySource source) throws TemplateException {
        return loadClass(null, source);
    }

    public <T> Class<T> loadClass(ClassLoader classLoader, MemorySource source) throws TemplateException {
        try {
            compile(source);
            URL[] urls = new URL[1];
            File file = new File(getOutputDir() );
            urls[0] = file.toURI().toURL();
            Class<T> type = (Class<T>) new URLClassLoader(urls).loadClass(source.qualifiedClassName);
            return type;
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    public <T> T loadInstance(MemorySource source) throws TemplateException {
        return loadInstance(null, source);
    }

    public <T> T loadInstance(ClassLoader classLoader, MemorySource source) throws TemplateException {
        try {
            Class<T> clazz = loadClass(classLoader, source);
            T object = (T) clazz.newInstance();
            return object;
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    String outputDir = TEMP_DIR;

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public static void main(String[] args) {
        MemorySource source = new MemorySource("org.tinygroup.template.parser.Context1", "package org.tinygroup.template.parser;import org.tinygroup.context.impl.ContextImpl;public class Context1 extends ContextImpl{}\n");
        MemorySourceCompiler compiler = new MemorySourceCompiler();
        compiler.compile(source);
    }

    class NameEnvironment implements INameEnvironment {

        private final MemorySource[] sources;

        public NameEnvironment(MemorySource[] sources) {
            this.sources = sources;
        }

        /**
         * @param compoundTypeName {{'j','a','v','a'}, {'l','a','n','g'}}
         */
        public NameEnvironmentAnswer findType(final char[][] compoundTypeName) {
            return findType(join(compoundTypeName));
        }

        public NameEnvironmentAnswer findType(final char[] typeName, final char[][] packageName) {
            return findType(join(packageName) + "." + new String(typeName));
        }

        /**
         * @param name like `aaa`,`aaa.BBB`,`java.lang`,`java.lang.String`
         */
        private NameEnvironmentAnswer findType(final String name) {

            // check data dir first
            for (MemorySource source : sources) {
                if (name.equals(source.getQualifiedClassName())) {
                    return new NameEnvironmentAnswer(new CompilationUnit(source), null);
                }
            }
            // find by system
            try {
                InputStream input = this.getClass().getClassLoader().getResourceAsStream(name.replace(".", "/") + ".class");
                if (input != null) {

                    byte[] bytes = readByteArray(input);
                    if (bytes != null) {
                        ClassFileReader classFileReader = new ClassFileReader(bytes, name.toCharArray(), true);
                        return new NameEnvironmentAnswer(classFileReader, null);
                    }
                }
            } catch (ClassFormatException e) {
                // Something very very bad
                throw new RuntimeException(e);
            }

            return null;
        }

        public boolean isPackage(char[][] parentPackageName, char[] packageName) {
            String name = new String(packageName);
            if (parentPackageName != null) {
                name = join(parentPackageName) + "." + name;
            }

            File target = new File(outputDir, name.replace('.', '/'));

            // only return false if it's a file
            // return true even if it doesn't exist
            return !target.isFile();
        }

        public void cleanup() {
        }
    }

    public void compile(Collection<MemorySource> source) {
        compile(source.toArray(new MemorySource[0]));
    }

    public void compile(MemorySource source) {
        MemorySource[] sources = new MemorySource[1];
        sources[0] = source;
        compile(sources);
    }

    public void compile(final MemorySource[] sources) {
        for(MemorySource source:sources){
            String javaFileName = source.getQualifiedClassName().replaceAll(".","/")+ ".java";
            try {
                IOUtils.writeToOutputStream(new FileOutputStream(new File(outputDir,javaFileName)),source.getContent(),"UTF-8");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * To find types ...
         */
        INameEnvironment nameEnvironment = new NameEnvironment(sources);
        /**
         * Compilation result
         */
        ICompilerRequestor compilerRequestor = new ICompilerRequestor() {

            public void acceptResult(CompilationResult result) {
                if (result.hasErrors()) {
                    StringBuffer sb = new StringBuffer();
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
                    save(result.getClassFiles(),sources);
                }

            }
        };

        IProblemFactory problemFactory = new DefaultProblemFactory(Locale.CHINA);
        IErrorHandlingPolicy policy = DefaultErrorHandlingPolicies.exitOnFirstError();

        /**
         * The JDT compiler
         */
        Compiler jdtCompiler = new Compiler(
                nameEnvironment, policy, getCompilerOptions(), compilerRequestor, problemFactory);

        // Go !

        ICompilationUnit[] units = new ICompilationUnit[sources.length];
        for (int i = 0; i < sources.length; i++) {
            units[i] = new CompilationUnit(sources[i]);
        }
        jdtCompiler.compile(units);
    }

    private static byte[] readByteArray(InputStream input) {
        BufferedInputStream stream = new BufferedInputStream(input);
        try {
            byte[] buf = new byte[stream.available()];
            stream.read(buf);
            stream.close();
            input.close();
            return buf;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(ClassFile[] classFiles,MemorySource[] sources) {
        if (classFiles == null) return;

        for (int i=0;i<classFiles.length;i++) {
            try {
                String fileName = new String(classFiles[i].fileName()) + ".class";
                File javaClassFile = new File(outputDir, fileName);
                File pa = javaClassFile.getParentFile();
                if (!pa.exists()) {
                    pa.mkdirs();
                }
                FileOutputStream fout = new FileOutputStream(javaClassFile);
                byte[] bytes = classFiles[i].getBytes();
                fout.write(bytes);
                fout.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static CompilerOptions getCompilerOptions() {
        Map

                settings = new HashMap();
        settings.put(CompilerOptions.OPTION_ReportMissingSerialVersion, CompilerOptions.IGNORE);
        settings.put(CompilerOptions.OPTION_LineNumberAttribute, CompilerOptions.GENERATE);
        settings.put(CompilerOptions.OPTION_SourceFileAttribute, CompilerOptions.GENERATE);
        settings.put(CompilerOptions.OPTION_ReportDeprecation, CompilerOptions.IGNORE);
        settings.put(CompilerOptions.OPTION_ReportUnusedImport, CompilerOptions.IGNORE);
        settings.put(CompilerOptions.OPTION_Encoding, "UTF-8");
        settings.put(CompilerOptions.OPTION_LocalVariableAttribute, CompilerOptions.GENERATE);
        String javaVersion = CompilerOptions.VERSION_1_5;
        if (System.getProperty("java.version").startsWith("1.6")) {
            javaVersion = CompilerOptions.VERSION_1_6;
        } else if (System.getProperty("java.version").startsWith("1.7")) {
            javaVersion = CompilerOptions.VERSION_1_7;
        }
        settings.put(CompilerOptions.OPTION_Source, javaVersion);
        settings.put(CompilerOptions.OPTION_TargetPlatform, javaVersion);
        settings.put(CompilerOptions.OPTION_PreserveUnusedLocal, CompilerOptions.PRESERVE);
        settings.put(CompilerOptions.OPTION_Compliance, javaVersion);
        return new CompilerOptions(settings);
    }

    private static class CompilationUnit implements ICompilationUnit {


        private final MemorySource source;

        public CompilationUnit(MemorySource source) {
            this.source = source;
        }

        @Override
        public char[] getContents() {
            return source.getContent().toCharArray();
        }

        @Override
        public char[] getMainTypeName() {
            return source.getSimpleName().toCharArray();
        }

        @Override
        public char[][] getPackageName() {
            String[] names = source.getQualifiedClassName().split("[.]");
            char[][] result = new char[names.length - 1][];
            for (int i = 0; i < names.length - 1; i++) {
                result[i] = names[i].toCharArray();
            }
            return result;
        }

        @Override
        public boolean ignoreOptionalProblems() {
            return false;
        }

        @Override
        public char[] getFileName() {
            return (source.getSimpleName() + ".java").toCharArray();
        }
    }

    private static String join(char[][] chars) {
        StringBuilder sb = new StringBuilder();
        for (char[] item : chars) {
            if (sb.length() > 0) {
                sb.append(".");
            }
            sb.append(item);
        }
        return sb.toString();
    }

}