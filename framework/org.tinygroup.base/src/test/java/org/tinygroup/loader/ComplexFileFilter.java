package org.tinygroup.loader;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectFilter;
import org.tinygroup.vfs.FileObjectProcessor;

import java.util.Map;

/**
 * Created by luoguo on 14-2-26.
 */
public class ComplexFileFilter implements FileObjectFilter {
    Map<FileObjectFilter,FileObjectProcessor> filterProcessorMap;
    public boolean accept(FileObject fileObject) {
        for(FileObjectFilter filter:filterProcessorMap.keySet()){
            if(filter.accept(fileObject)){
                filterProcessorMap.get(filter).process(fileObject);
            }
        }
        return false;
    }
}
