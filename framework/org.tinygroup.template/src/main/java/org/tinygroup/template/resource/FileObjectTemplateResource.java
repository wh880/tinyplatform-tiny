package org.tinygroup.template.resource;

import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.TemplateResource;
import org.tinygroup.vfs.FileObject;

/**
 * Created by luoguo on 2014/6/7.
 */
public class FileObjectTemplateResource implements TemplateResource {
    FileObject fileObject;
    public FileObjectTemplateResource(FileObject fileObject){
        this.fileObject=fileObject;
    }


    @Override
    public long getTimestamp() {
        return fileObject.getLastModifiedTime();
    }

    @Override
    public String getPath() {
        return fileObject.getPath();
    }

    @Override
    public String getContent() throws TemplateException {
        try {
            return IOUtils.readFromInputStream(fileObject.getInputStream(),"UTF-8");
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }
}
