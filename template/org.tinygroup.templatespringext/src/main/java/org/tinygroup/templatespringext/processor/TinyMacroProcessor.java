package org.tinygroup.templatespringext.processor;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.templatespringext.impl.AbstractFileScanner;
import org.tinygroup.vfs.FileObject;

/**
 * Created by wangll13383 on 2015/9/9.
 * macro文件处理器
 */
public class TinyMacroProcessor extends AbstractFileScanner {

    private final static String MACRO_FILE_EXT = ".component";

    private TemplateEngine engine;

    public TemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

    public boolean isMatch(String fileName) {
        return fileName.endsWith(MACRO_FILE_EXT);
    }

    public void fileProcess() {
        if(fileList.size()>0){
            LOGGER.logMessage(LogLevel.INFO, "开始加载Macro文件...");
            for(FileObject file:fileList){
                LOGGER.logMessage(LogLevel.INFO, "正在加载Macro文件[{0}]",file.getAbsolutePath());
                try {
                    engine.registerMacroLibrary(file.getPath());
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.logMessage(LogLevel.INFO, "加载Macro文件结束...");
        }
    }
}
