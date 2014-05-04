package org.tinygroup.bundle;

import org.tinygroup.bundle.config.BundleDefine;

/**
 * 在Bundle加载，或卸载的时候执行一些处理
 * Created by luoguo on 2014/5/4.
 */
public interface BundleEvent {
    void process(BundleContext bundleContext, BundleDefine bundle);
}
