package org.tinygroup.templatespringext.impl;

import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.templatespringext.CallBackFunction;
import org.tinygroup.templatespringext.processor.TinyJarFileProcessor;
import org.tinygroup.templatespringext.processor.TinyLocalPathProcessor;
import org.tinygroup.templatespringext.processor.TinyMacroProcessor;
import org.tinygroup.vfs.FileObject;
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
        classPathProcess();
        jarFileProcessor = jarFileProcessor == null?new TinyJarFileProcessor():jarFileProcessor;
        macroFileProcessor = new TinyMacroProcessor();
        localPathProcessor = new TinyLocalPathProcessor();
        jarFileProcessor.setEngine(engine);
        macroFileProcessor.setEngine(engine);
        localPathProcessor.setEngine(engine);
    }

    public void resolverFile(FileObject file, CallBackFunction callBackFunction) {
        if(file.isFolder()){
            for(FileObject f:file.getChildren()){
                resolverFile(f,callBackFunction);
            }
        }else {
            callBackFunction.process(file);
        }
    }

    public void resolverFloder(FileObject file, CallBackFunction callBackFunction) {
        if(file.isFolder()&&isMatch(file.getFileName())){
            for(FileObject f:file.getChildren()){
                resolverFloder(f,callBackFunction);
            }
        }else {
            callBackFunction.process(file);
        }
    }

    public void scanFile() {
        resolverFloder(VFS.resolveFile("./"), new CallBackFunction() {
            public void process(FileObject fileObject) {
                if(jarFileProcessor.isMatch(fileObject.getFileName())){
                    jarFileProcessor.addFile(fileObject);
                }
                if(localPathProcessor.isMatch(fileObject.getFileName())){
                    localPathProcessor.addFile(fileObject);
                }
            }
        });
    }

    public void fileProcess() {
        localPathProcessor.process();
        jarFileProcessor.process();
        for (FileObject file:jarFileProcessor.getFileObjectList()){
            resolverFile(file, new CallBackFunction() {
                public void process(FileObject fileObject) {
                    if(macroFileProcessor.isMatch(fileObject.getFileName())){
                        macroFileProcessor.addFile(fileObject);
                    }
                }
            });
        }
        for(String classPath:getClassPathList()){
            resolverFile(VFS.resolveFile(classPath), new CallBackFunction() {
                public void process(FileObject fileObject) {
                    if(macroFileProcessor.isMatch(fileObject.getFileName())){
                        macroFileProcessor.addFile(fileObject);
                    }
                }
            });
        }
        for (FileObject file:localPathProcessor.getFileObjectList()){
            resolverFile(file, new CallBackFunction() {
                public void process(FileObject fileObject) {
                    if(macroFileProcessor.isMatch(fileObject.getFileName())){
                        macroFileProcessor.addFile(fileObject);
                    }
                }
            });
        }
        macroFileProcessor.process();
    }

    public void classPathProcess() {
        for(String classPath : getClassPathList()){
            engine.addResourceLoader(new FileObjectResourceLoader("page","layout","component",classPath));

        }
    }
}
