package org.tinygroup.templatespringext.processor;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.templatespringext.impl.AbstractFileScanner;
import org.tinygroup.vfs.FileObject;

import java.util.List;

/**
 * Created by wangll13383 on 2015/9/9.
 * jar包处理器
 */
public class TinyJarFileProcessor extends AbstractFileScanner {

    private final static String JAR_FILE_EXT = ".jar";

    private TemplateEngine engine;

    private TinyMacroProcessor macroProcessor;

    private List<String> nameRole;

    public List<String> getNameRole() {
        return nameRole;
    }

    public void setNameRole(List<String> nameRole) {
        this.nameRole = nameRole;
    }

    public TinyMacroProcessor getMacroProcessor() {
        return macroProcessor;
    }

    public void setMacroProcessor(TinyMacroProcessor macroProcessor) {
        this.macroProcessor = macroProcessor;
    }

    public TemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

    public boolean isMatch(String fileName) {
        return fileName.endsWith(JAR_FILE_EXT);
    }

    public void fileProcess() {
        if(fileList.size()>0){
            LOGGER.logMessage(LogLevel.INFO, "开始加载Jar文件...");
            for(FileObject file:fileList){
                for(String role:nameRole){
                    if(file.getFileName().matches(role)) {
                        LOGGER.logMessage(LogLevel.INFO, "正在加载Jar文件[{0}]", file.getAbsolutePath());
                        engine.addResourceLoader(new FileObjectResourceLoader("page", "layout", "component", file.getAbsolutePath()));
                        macroProcessor.resolverFile(file);
                    }
                }
            }
            LOGGER.logMessage(LogLevel.INFO, "加载Jar文件结束...");
        }
    }
}
