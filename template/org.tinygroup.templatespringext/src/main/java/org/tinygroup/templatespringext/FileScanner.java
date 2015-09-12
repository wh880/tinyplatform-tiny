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

    public void addFile(FileObject file);

    public void resolverFile(FileObject file,CallBackFunction callBackFunction);

    public void resolverFloder(FileObject file,CallBackFunction callBackFunction);

    public void scanFile();

    public boolean isMatch(String fileName);

    public void fileProcess();

    public void setClassPathList(List<String> classPathList);

    public void setEngine(TemplateEngine engine);

    public void init();

}
