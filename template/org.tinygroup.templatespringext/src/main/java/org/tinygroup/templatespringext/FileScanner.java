package org.tinygroup.templatespringext;

import org.tinygroup.vfs.FileObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangll13383 on 2015/9/9.
 * 文件扫描接口
 */
public interface FileScanner {

    public void addFile(FileObject file);

    public void resolverFile(FileObject file);

    public boolean isMatch(String fileName);

    public void fileProcess();

}
