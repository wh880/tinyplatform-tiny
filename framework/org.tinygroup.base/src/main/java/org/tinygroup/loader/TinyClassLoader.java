package org.tinygroup.loader;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectFilter;
import org.tinygroup.vfs.FileObjectProcessor;
import org.tinygroup.vfs.VFS;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * TinyClassLoader主要用于动态加载类及资源处理
 * Created by luoguo on 14-2-25.
 */
public class TinyClassLoader extends URLClassLoader {

    FileObject[] fileObjects = null;
    List<TinyClassLoader> subTinyClassLoaderList = new ArrayList<TinyClassLoader>();

    public TinyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public TinyClassLoader(URL[] urls) {
        super(urls);
    }

    public TinyClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public FileObject[] getFileObjects() {
        List<FileObject> result = new ArrayList<FileObject>();
        getFileObjects(result);
        return result.toArray(new FileObject[0]);
    }

    private void getFileObjects(List<FileObject> result) {
        for (FileObject fileObject : getCurrentFileObjects()) {
            result.add(fileObject);
        }
        for (TinyClassLoader tinyClassLoader : subTinyClassLoaderList) {
            tinyClassLoader.getFileObjects(result);
        }
    }

    private FileObject[] getCurrentFileObjects() {
        URL[] urLs = getURLs();
        if (fileObjects == null) {
            fileObjects = new FileObject[urLs.length];
            for (int i = 0; i < urLs.length; i++) {
                fileObjects[i] = VFS.resolveURL(urLs[i]);
            }
        }
        return fileObjects;
    }

    public void addSubTinyClassLoader(TinyClassLoader tinyClassLoader) {
        subTinyClassLoaderList.add(tinyClassLoader);
    }

    public void removeSubTinyClassLoader(TinyClassLoader tinyClassLoader) {
        subTinyClassLoaderList.remove(tinyClassLoader);
    }

    public void foreach(FileObjectFilter fileObjectFilter, FileObjectProcessor fileObjectProcessor) {
        if (fileObjects != null) {
            for (FileObject fileObject : fileObjects) {
                fileObject.foreach(fileObjectFilter, fileObjectProcessor);
            }
        }
        for (TinyClassLoader tinyClassLoader : subTinyClassLoaderList) {
            tinyClassLoader.foreach(fileObjectFilter, fileObjectProcessor);
        }
    }

    /**
     * 覆盖父类的方法,在自己里找不到还找儿子
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        try {
            return super.findClass(name);
        } catch (ClassNotFoundException e) {
            for (TinyClassLoader tinyClassLoader : subTinyClassLoaderList) {
                try {
                    return tinyClassLoader.findClass(name);
                } catch (ClassNotFoundException e1) {
                    //如果子classloader找不到，不抛异常
                }
            }
        }
        //如果所有的都找不到，则抛异常
        throw new ClassNotFoundException(name);
    }

    /**
     * 覆盖父类的findResource方法
     *
     * @param name
     * @return
     */
    public URL findResource(final String name) {
        URL resource = super.findResource(name);
        if (resource == null) {
            for (TinyClassLoader tinyClassLoader : subTinyClassLoaderList) {
                resource = tinyClassLoader.findResource(name);
                if (resource != null) {
                    return resource;
                }
            }
        }
        //如果所有的都找不到，则返回null
        return null;
    }

    public Enumeration<URL> findResources(final String name) throws IOException {
        final Enumeration<URL>[] enumerations = new Enumeration[1 + subTinyClassLoaderList.size()];
        enumerations[0] = super.findResources(name);
        for (int i = 0; i < subTinyClassLoaderList.size(); i++) {
            enumerations[i + 1] = subTinyClassLoaderList.get(i).findResources(name);
        }
        return new Enumeration<URL>() {
            int index = 0;

            public boolean hasMoreElements() {
                boolean hasMoreElements = enumerations[index].hasMoreElements();
                while (!hasMoreElements && index < enumerations.length-1) {
                    index++;
                    hasMoreElements = enumerations[index].hasMoreElements();
                }
                return hasMoreElements;
            }

            public URL nextElement() {
                if (index < enumerations.length) {
                    return enumerations[index].nextElement();
                }
                return null;
            }
        };
    }
}
