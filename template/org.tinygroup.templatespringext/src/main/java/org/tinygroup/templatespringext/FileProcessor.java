package org.tinygroup.templatespringext;

import org.tinygroup.vfs.FileObject;


/**
 * Created by wangll13383 on 2015/9/11.
 */
public interface FileProcessor {
    public void addFile(FileObject fileObject);

    public void removeFile(FileObject fileObject);

    public void process();

    public boolean isMatch(String fileName);
}
