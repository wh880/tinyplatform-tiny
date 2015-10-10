package org.tinygroup.templatespringext.processor;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.templatespringext.impl.AbstractFileProcessor;
import org.tinygroup.vfs.FileObject;

import java.util.List;

/**
 * Created by wangll13383 on 2015/9/12.
 */
public class TinyLocalPathProcessor extends AbstractFileProcessor {
    private final static String CLASSES_FILE_EXT = "classes";

    private  final  static  String RESOURCE_FILE_EXT = "resources";

    private  final  static  String WEBAPP_FILE_EXT = "webapp";

    private TemplateEngine engine;

    public TemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

    public boolean isMatch(String fileName) {
        return fileName.equals(CLASSES_FILE_EXT)||fileName.equals(RESOURCE_FILE_EXT)||fileName.equals(WEBAPP_FILE_EXT);    }

    public void process() {
        if(fileList.size()>0){
            LOGGER.logMessage(LogLevel.INFO, "开始加载Path路径...");
            for(FileObject file:fileList){
                engine.addResourceLoader(new FileObjectResourceLoader("page","layout","component",file.getAbsolutePath()));
            }
            LOGGER.logMessage(LogLevel.INFO, "加载Path路径结束...");
        }
    }
}
