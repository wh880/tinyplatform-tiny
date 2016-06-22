package org.tinygroup.templatespringext.processor;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.templatespringext.AbstractFileProcessor;
import org.tinygroup.vfs.FileObject;

import java.util.List;

/**
 * Created by wangll13383 on 2015/9/9.
 * jar包处理器
 */
public class TinyJarFileProcessor extends AbstractFileProcessor{

    private final static String JAR_FILE_EXT = ".jar";

    private TemplateEngine engine;

    private List<String> nameRule;

    public List<String> getNameRule() {
        return nameRule;
    }

    public void setNameRule(List<String> nameRule) {
        this.nameRule = nameRule;
    }

    public TemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

    public boolean isMatch(String fileName) {
        return fileName.endsWith(JAR_FILE_EXT)&&jarNameMatches(fileName);
    }

    public boolean jarNameMatches(String fileName){
        for(String rule : nameRule){
            if(fileName.matches(rule)){
                return true;
            }
        }
        return false;
    }

    public void process() {
        if(fileList.size()>0){
            LOGGER.logMessage(LogLevel.INFO, "开始加载Jar文件...");
            for(FileObject file:fileList){
                engine.addResourceLoader(new FileObjectResourceLoader("page","layout","component",file.getAbsolutePath()));
            }
            LOGGER.logMessage(LogLevel.INFO, "加载Jar文件结束...");
        }
    }
}
