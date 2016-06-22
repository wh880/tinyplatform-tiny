package org.tinygroup.templatespringext.impl;

import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.templatespringext.AbstractFileScanner;
import org.tinygroup.templatespringext.processor.TinyJarFileProcessor;
import org.tinygroup.templatespringext.processor.TinyLocalPathProcessor;
import org.tinygroup.templatespringext.processor.TinyMacroProcessor;
import org.tinygroup.vfs.VFS;

/**
 * Created by wangll13383 on 2015/9/11.
 */
public class FileScannerImpl extends AbstractFileScanner{

    private TinyJarFileProcessor jarFileProcessor;

    private TinyMacroProcessor macroFileProcessor;

    private TinyLocalPathProcessor localPathProcessor;

    private TemplateEngine engine;

    public void setEngine(TemplateEngine engine){
        this.engine  = engine;
    }

    public TinyJarFileProcessor getJarFileProcessor() {
        return jarFileProcessor;
    }

    public void setJarFileProcessor(TinyJarFileProcessor jarFileProcessor) {
        this.jarFileProcessor = jarFileProcessor;
    }

    public void init(){
        jarFileProcessor = jarFileProcessor == null?new TinyJarFileProcessor():jarFileProcessor;
        macroFileProcessor = new TinyMacroProcessor();
        localPathProcessor = new TinyLocalPathProcessor();

        this.getFileProcessors().add(jarFileProcessor);
        this.getFileProcessors().add(macroFileProcessor);
        this.getFileProcessors().add(localPathProcessor);

        jarFileProcessor.setEngine(engine);
        macroFileProcessor.setEngine(engine);
        localPathProcessor.setEngine(engine);
    }

    public void scanFile() {
        resolverFolder(VFS.resolveFile("./"));
    }

    public void fileProcess() {
        localPathProcessor.process();
        classPathProcess();
        jarFileProcessor.process();
        macroFileProcessor.process();
    }

    public void classPathProcess() {
        for(String classPath : getClassPathList()){
            engine.addResourceLoader(new FileObjectResourceLoader("page","layout","component",classPath));

        }
    }
}
