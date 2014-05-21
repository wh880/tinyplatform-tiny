package org.tinygroup.compiler.impl;

import org.tinygroup.compiler.Source;

import java.io.File;

/**
 * Created by luoguo on 2014/5/21.
 */
public class FileSource implements Source {
    File source;
    public FileSource(){

    }
    public FileSource(String path){
        this.source=new File(path);
    }
    public FileSource(File file){
        this.source=file;
    }
    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }
}
