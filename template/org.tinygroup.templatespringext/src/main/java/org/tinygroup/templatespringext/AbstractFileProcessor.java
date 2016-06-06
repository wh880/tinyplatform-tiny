package org.tinygroup.templatespringext;

import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.vfs.FileObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangll13383 on 2015/9/11.
 */
public abstract class AbstractFileProcessor implements FileProcessor {
    protected List<FileObject> fileList = new ArrayList<FileObject>();

    protected Map<String,FileObject> caches = new HashMap<String,FileObject>();

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractFileProcessor.class);

    public void addFile(FileObject fileObject) {
        if(caches.containsKey(fileObject.getAbsolutePath())) {
            if (caches.get(fileObject.getAbsolutePath()).isModified()) {
                caches.remove(fileObject.getAbsolutePath());
                caches.put(fileObject.getAbsolutePath(), fileObject);
                return;
            }
        }
        fileList.add(fileObject);
        caches.put(fileObject.getAbsolutePath(),fileObject);
    }

    public void removeFile(FileObject fileObject) {

    }

    public List<FileObject> getFileObjectList(){
        return fileList;
    }
}
