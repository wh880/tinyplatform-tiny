package org.tinygroup.templatespringext;

import org.tinygroup.vfs.FileObject;


/**
 * Created by wangll13383 on 2015/9/11.
 */
public interface FileProcessor {
    void addFile(FileObject fileObject);

    void removeFile(FileObject fileObject);

    void process();

    boolean isMatch(String fileName);
}
