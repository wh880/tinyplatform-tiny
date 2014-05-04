package org.tinygroup.bundle.impl;

import org.tinygroup.bundle.BundleContext;
import org.tinygroup.bundle.BundleEvent;
import org.tinygroup.bundle.BundleException;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.loader.TinyClassLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luoguo on 2014/5/4.
 */
public class BundleManagerDefault implements BundleManager {
    private Map<String, BundleDefine> bundleDefineMap = new HashMap<String, BundleDefine>();
    private Map<BundleDefine, TinyClassLoader> tinyClassLoaderMap = new HashMap<BundleDefine, TinyClassLoader>();
    private String bundleRoot;
    private String commonRoot;
    private BundleContext bundleContext = new BundleContextImpl() {
    };
    private TinyClassLoader tinyClassLoader = new TinyClassLoader();
    private List<BundleEvent> beforeStartBundleEvent;
    private List<BundleEvent> afterStartBundleEvent;
    private List<BundleEvent> beforeStopBundleEvent;
    private List<BundleEvent> afterStopBundleEvent;

    public void addBundleDefine(BundleDefine bundleDefine) {
        bundleDefineMap.put(bundleDefine.getName(), bundleDefine);
    }

    public BundleDefine getBundleDefine(String bundleName) throws BundleException {
        BundleDefine bundleDefine = bundleDefineMap.get(bundleName);
        if (bundleDefine != null) {
            return bundleDefine;
        }
        throw new BundleException("找不到杂物箱定义：" + bundleName);
    }

    public void removeBundle(BundleDefine bundleDefine) throws BundleException {
        if (bundleDefineMap.get(bundleDefine.getName()) != null) {
            bundleDefineMap.remove(bundleDefine.getName());
        }
        throw new BundleException("找不到杂物箱定义：" + bundleDefine.getName());
    }

    public void setBundleRoot(String path) {
        this.bundleRoot = path;
    }

    public void setCommonRoot(String path) {
        this.commonRoot = path;
    }

    public String getBundleRoot() {
        return bundleRoot;
    }

    public String getCommonRoot() {
        return commonRoot;
    }

    public BundleContext getBundleContext() {
        return bundleContext;
    }

    public TinyClassLoader getTinyClassLoader() {
        return tinyClassLoader;
    }

    public void setBeforeStartBundleEvent(List<BundleEvent> bundleEvents) {
        this.beforeStartBundleEvent = bundleEvents;
    }

    public void setAfterStartBundleEvent(List<BundleEvent> bundleEvents) {
        this.afterStartBundleEvent = bundleEvents;
    }

    public void setBeforeStopBundleEvent(List<BundleEvent> bundleEvents) {
        this.beforeStopBundleEvent = bundleEvents;
    }

    public void setAfterStopBundleEvent(List<BundleEvent> bundleEvents) {
        this.afterStopBundleEvent = bundleEvents;
    }

    public TinyClassLoader getTinyClassLoader(BundleDefine bundleDefine) {
        return tinyClassLoaderMap.get(bundleDefine);
    }

    public void start(BundleContext bundleContext) {
    }

    public void stop(BundleContext bundleContext) {

    }

    public void start(BundleContext bundleContext, BundleDefine bundleDefine) {

    }

    public void stop(BundleContext bundleContext, BundleDefine bundleDefine) {

    }
}
