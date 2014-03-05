package org.tinygroup.vfs;

import org.tinygroup.vfs.FileObject;

/**
 * 用于对文件对象进行处理的接口
 * Created by luoguo on 14-2-26.
 */
public interface FileObjectProcessor {
    void process(FileObject fileObject);
}
