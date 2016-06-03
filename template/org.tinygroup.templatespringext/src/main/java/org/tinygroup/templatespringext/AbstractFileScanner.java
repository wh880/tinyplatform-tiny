package org.tinygroup.templatespringext;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.templatespringext.FileProcessor;
import org.tinygroup.templatespringext.FileScanner;
import org.tinygroup.vfs.FileObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangll13383 on 2015/9/9.
 * 文件扫描抽象类
 */
public abstract class AbstractFileScanner implements FileScanner {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractFileScanner.class);

    private List<String> classPathList = new ArrayList<String>();

    private List<FileProcessor> fileProcessors = new ArrayList<FileProcessor>();

    public List<FileProcessor> getFileProcessors() {
        return fileProcessors;
    }

    public void setFileProcessors(List<FileProcessor> fileProcessors) {
        this.fileProcessors = fileProcessors;
    }

    public void setClassPathList(List<String> classPathList) {
        this.classPathList = classPathList;
    }

    protected List<String> getClassPathList(){
        return classPathList;
    }

    public void resolverFolder(FileObject file) {
        if(file.isFolder()){
            for(FileProcessor fileProcessor:fileProcessors){
                if(fileProcessor.isMatch(file.getFileName())) {
                    fileProcessor.addFile(file);
                }
            }
            for(FileObject f:file.getChildren()){
                resolverFolder(f);
            }
        }else{
            for(FileProcessor fileProcessor:fileProcessors){
                if(fileProcessor.isMatch(file.getFileName())) {
                    fileProcessor.addFile(file);
                }
            }
        }
    }



}
