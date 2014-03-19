package org.tinygroup.bundle.fileresolve;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.bundle.BundleLoader;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

/**
 * 放在工程目录中的插件加载器
 *
 * @author luoguo
 */
public class PluginFileProcessor extends AbstractFileProcessor {
    private BundleManager bundleManager;

    public BundleManager getBundleManager() {
        return bundleManager;
    }

    public void setBundleManager(BundleManager bundleManager) {
        this.bundleManager = bundleManager;
    }

    public boolean isMatch(FileObject fileObject) {
        if (fileObject.getFileName().endsWith(BundleLoader.BUNDLE_FILENAME)) {
            return true;
        }
        return false;
    }


    public void process() {
        process(this.getClass().getClassLoader());

    }

    public void process(ClassLoader loader) {
        XStream stream = XStreamFactory.getXStream(BundleManager.PLUGIN_XSTREAM);
        for (FileObject fileObject : fileObjects) {
            BundleDefine bundleDefine = (BundleDefine) stream.fromXML(fileObject.getInputStream());
            bundleManager.add(bundleDefine);
        }
    }
}
