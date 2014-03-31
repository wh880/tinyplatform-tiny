package org.tinygroup.vfs.impl;

import junit.framework.TestCase;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

import java.net.MalformedURLException;

/**
 * Created by luoguo on 14-3-31.
 */
public class FtpFileObjectTest extends TestCase {
    public static void main(String[] args) throws MalformedURLException {

        FileObject object = VFS.resolveFile("ftp://anonymous:anonymous1@localhost/aaa");
        for (FileObject fileObject : object.getChildren()) {
            System.out.println(fileObject.getFileName());
            System.out.println(fileObject.getPath());
            System.out.println(fileObject.getAbsolutePath());
            System.out.println(fileObject.isFolder());
        }
    }

}
