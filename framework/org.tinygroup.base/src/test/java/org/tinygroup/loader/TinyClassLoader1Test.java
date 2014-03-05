package org.tinygroup.loader;


import junit.framework.TestCase;
import org.tinygroup.commons.tools.Enumerator;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectProcessor;
import org.tinygroup.vfs.impl.filter.FileExtNameFileObjectFilter;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;

/**
 * TinyClassLoader Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/25/2014</pre>
 */
public class TinyClassLoader1Test extends TestCase {
    TinyClassLoader tinyClassLoader = null;

    public TinyClassLoader1Test() {
    }

    public void setUp() throws Exception {
        super.setUp();
        URL[] urls0 = {new File("src/test/resources/org.tinygroup.loader-0.0.13-SNAPSHOT.jar-tests.jar").toURL()};
        URL[] urls1 = {new File("src/test/resources/org.tinygroup.loader-0.0.13-SNAPSHOT.jar1-tests.jar").toURL()};
        URL[] urls2 = {new File("src/test/resources/org.tinygroup.loader-0.0.13-SNAPSHOT.jar2-tests.jar").toURL()};
        tinyClassLoader = new TinyClassLoader(urls0);
        tinyClassLoader.addSubTinyClassLoader(new TinyClassLoader(urls1,tinyClassLoader));
        tinyClassLoader.addSubTinyClassLoader(new TinyClassLoader(urls2,tinyClassLoader));
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: getFileObjects()
     */
    public void testGetFileObjects() throws Exception {
        FileObject[] fileObjects = tinyClassLoader.getFileObjects();
        assertEquals(3, fileObjects.length);
        for (FileObject fileObject : fileObjects) {
            assertEquals(true, fileObject.isExist());
        }
    }

    public void testForEachByExtFileName() throws Exception {
        for (FileObject fileObject : tinyClassLoader.getFileObjects()) {
            fileObject.foreach(new FileExtNameFileObjectFilter("class"), new FileObjectProcessor() {
                public void process(FileObject fileObject) {
                    System.out.println(fileObject.getPath());
                }
            });
        }
    }
    public void testPrintMF() throws Exception {
        for (FileObject fileObject : tinyClassLoader.getFileObjects()) {
            fileObject.foreach(new FileExtNameFileObjectFilter("mf"), new FileObjectProcessor() {
                public void process(FileObject fileObject) {
                    System.out.println(fileObject.getPath());
                }
            });
        }
    }
    public void testFindResources() throws Exception {
        Enumeration<URL> enumerator = tinyClassLoader.findResources("META-INF/MANIFEST.MF");
        while(enumerator.hasMoreElements()){
            URL url=enumerator.nextElement();
            System.out.println(url.toString());
        }

    }
}
