package org.tinygroup.templatespringext;

import org.tinygroup.template.TemplateEngine;
import org.tinygroup.vfs.FileObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangll13383 on 2015/9/9.
 *
 */
public interface FileScanner {

    void resolverFolder(FileObject file);

    void scanFile();

    void fileProcess();

    void setClassPathList(List<String> classPathList);

    void setEngine(TemplateEngine engine);

    void init();

    void classPathProcess();

}
