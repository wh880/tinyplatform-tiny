package org.tinygroup.templatespringext.impl;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.templatespringext.FileScanner;
import org.tinygroup.vfs.FileObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangll13383 on 2015/9/9.
 * 文件扫描抽象类
 */
public abstract class AbstractFileScanner implements FileScanner {

    protected List<FileObject> fileList = new ArrayList<FileObject>();

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractFileScanner.class);

    public void addFile(FileObject file) {

    }

    public void resolverFile(FileObject file) {
        if(file.isFolder()&&!isMatch(file.getFileName())){
            for(FileObject f:file.getChildren()){
                resolverFile(f);
            }
        }else if(isMatch(file.getFileName())){
            fileList.add(file);
        }
    }
}
