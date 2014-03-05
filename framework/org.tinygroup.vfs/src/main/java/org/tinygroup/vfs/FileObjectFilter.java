package org.tinygroup.vfs;

/**
 * 用于对文件进行过滤
 * Created by luoguo on 14-2-26.
 */
public interface FileObjectFilter {
    /**
     * 如果文件对象匹配则返回真
     *
     * @param fileObject
     * @return
     */
    public boolean accept(FileObject fileObject);
}
