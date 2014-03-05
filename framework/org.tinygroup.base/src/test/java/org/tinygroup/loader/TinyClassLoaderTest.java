package org.tinygroup.loader;


import junit.framework.TestCase;
import org.tinygroup.vfs.FileObject;

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
public class TinyClassLoaderTest extends TestCase {
    TinyClassLoader tinyClassLoader = null;

    public TinyClassLoaderTest() {
    }

    public void setUp() throws Exception {
        super.setUp();
        URL[] urls = {new File("src/test/resources/org.tinygroup.loader-0.0.13-SNAPSHOT.jar-tests.jar").toURL(),
                new File("src/test/resources/org.tinygroup.loader-0.0.13-SNAPSHOT.jar1-tests.jar").toURL(),
                new File("src/test/resources/org.tinygroup.loader-0.0.13-SNAPSHOT.jar2-tests.jar").toURL()};
        tinyClassLoader = new TinyClassLoader(urls);
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

    public void testGetResources() throws Exception {
        Enumeration<URL> urls = tinyClassLoader.findResources("META-INF/MANIFEST.MF");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                //            System.out.println(url.toString());
            }
        }
        long end = System.currentTimeMillis();
        System.out.printf("time:%d\n", end - start);
    }
}
