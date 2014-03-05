package org.tinygroup.vfs.impl.filter;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectFilter;

/**
 * 根据文件扩展名进行文件过滤
 * Created by luoguo on 14-2-26.
 */
public class FileExtNameFileObjectFilter implements FileObjectFilter {
    String fileExtName;
    /**
     * 是否大小写敏感，默认不敏感
     */
    boolean caseSensitive = false;

    public FileExtNameFileObjectFilter(String fileExtName) {
        this.fileExtName = fileExtName;
    }

    public FileExtNameFileObjectFilter(String fileExtName, boolean caseSensitive) {
        this(fileExtName);
        this.caseSensitive = caseSensitive;
    }

    public boolean accept(FileObject fileObject) {
        String extName = fileObject.getExtName();
        if (extName != null) {
            if (caseSensitive) {
                return extName.equals(fileExtName);
            } else {
                return extName.equalsIgnoreCase(fileExtName);
            }
        }
        return false;
    }
}
