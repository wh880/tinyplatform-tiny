package org.tinygroup.bundle;

/**
 * BundleActivator，杂物箱，它是在运行时一个杂物箱的内存实类
 * Created by luoguo on 2014/5/4.
 */
public interface BundleActivator {
    void start(BundleContext bundleContext) throws BundleException;

    void stop(BundleContext bundleContext) throws BundleException;
}
