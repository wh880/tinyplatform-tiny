package org.tinygroup.vfs.impl.filter;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luoguo on 14-2-26.
 */
public class FileNameFileObjectFilter implements FileObjectFilter {
    Pattern pattern;
    /**
     * 是否完全匹配，默认是局部区域即可
     */
    boolean fullMatch = false;

    public FileNameFileObjectFilter(String fileNamePattern) {
        pattern = Pattern.compile(fileNamePattern);
    }

    public FileNameFileObjectFilter(String fileNamePattern, boolean fullMatch) {
        this(fileNamePattern);
        fullMatch = true;
    }

    public boolean accept(FileObject fileObject) {
        String fileName = fileObject.getFileName();
        if (fullMatch) {
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                return matcher.group().equals(fileName);
            } else {
                return false;
            }
        } else {
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
