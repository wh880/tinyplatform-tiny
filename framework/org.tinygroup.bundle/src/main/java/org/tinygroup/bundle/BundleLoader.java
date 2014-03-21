package org.tinygroup.bundle;

import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.fileresolver.FileProcessor;

import java.net.URL;
import java.util.List;

public interface BundleLoader {
    static final String BUNDLE_FOLDER = "bundles";
    static final String BUNDLE_FILENAME = "bundles.xml";

    List<BundleDefine> load(String path);

    /**
     * 获取指定插件的ClassLoader
     *
     * @param bundleDefine
     * @return
     */
    ClassLoader getLoader(BundleDefine bundleDefine);

    /**
     * 获取插件外部包所在的ClassLoader，该Loader也是所有插件的ClassLoader的父Loader
     *
     * @return
     */
    ClassLoader getPublicLoader();

    /**
     * 获取指定插件的资源扫描FileProcessor
     *
     * @param bundleDefine
     * @return
     */
    List<FileProcessor> getResourceProcessors(BundleDefine bundleDefine);

    /**
     * 获取指定插件的资源移除FileProcessor
     *
     * @param bundleDefine
     * @return
     */
    List<FileProcessor> getRemoveResourceProcessors(BundleDefine bundleDefine);

    /**
     * 删除指定插件的ClassLoader及相关Jar信息
     *
     * @param bundleDefine
     */
    void remove(BundleDefine bundleDefine);

    void add(BundleDefine bundleDefine);

    List<BundleDefine> listJar(String path);

    void resolveResource(ClassLoader loader, URL urls1[]);

    void deResolveResource(ClassLoader loader, URL urls[]);
}
