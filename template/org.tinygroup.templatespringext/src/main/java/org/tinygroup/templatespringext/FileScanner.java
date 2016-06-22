package org.tinygroup.templatespringext;

import java.util.List;

import org.tinygroup.template.TemplateEngine;
import org.tinygroup.vfs.FileObject;

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
