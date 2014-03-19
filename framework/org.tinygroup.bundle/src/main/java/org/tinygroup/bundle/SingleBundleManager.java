package org.tinygroup.bundle;

import org.tinygroup.bundle.config.BundleDefine;

/**
 * 针对单个插件的操作接口
 *
 * @author luoguo
 */
public interface SingleBundleManager {

    void init(BundleDefine bundleDefine);

    void start(BundleDefine bundleDefine);

    void stop(BundleDefine bundleDefine);

    void pause(BundleDefine bundleDefine);

    void destroy(BundleDefine bundleDefine);

    void assemble(BundleDefine bundleDefine);

    void disassemble(BundleDefine bundleDefine);

}
