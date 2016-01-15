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

    void addFile(FileObject file);

    void resolverFile(FileObject file, CallBackFunction callBackFunction);

    void resolverFloder(FileObject file, CallBackFunction callBackFunction);

    void scanFile();

    boolean isMatch(String fileName);

    void fileProcess();

    void setClassPathList(List<String> classPathList);

    void setEngine(TemplateEngine engine);

    void init();

    void classPathProcess();

}
