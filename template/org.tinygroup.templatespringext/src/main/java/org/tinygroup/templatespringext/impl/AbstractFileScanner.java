package org.tinygroup.templatespringext.impl;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.templatespringext.FileScanner;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangll13383 on 2015/9/9.
 * 文件扫描抽象类
 */
public abstract class AbstractFileScanner implements FileScanner {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractFileScanner.class);

    private List<String> classPathList;

    public void setClassPathList(List<String> classPathList) {
        this.classPathList = classPathList;
    }

    protected List<String> getClassPathList(){
        return classPathList;
    }

    public void addFile(FileObject file) {

    }

    public boolean isMatch(String fileName) {
        return!(fileName.endsWith(".jar")||classPathList.contains(fileName)||fileName.equals("classes")||fileName.equals("resources"));
    }


}
