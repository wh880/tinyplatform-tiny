package org.tinygroup.bundle.plugin;

import org.tinygroup.bundle.Bundle;
import org.tinygroup.bundle.BundleContext;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.exception.BundleException;

public class HelloAllBundle implements Bundle {
    HelloAll helloAll = new HelloAll("1.0");

    public void init(BundleContext bundleContext) throws BundleException {

    }

    public void start(BundleContext bundleContext) throws BundleException {

    }

    public void pause(BundleContext bundleContext) throws BundleException {

    }

    public void stop(BundleContext bundleContext) throws BundleException {

    }

    public void destroy(BundleContext bundleContext) throws BundleException {

    }

    public <T> T getService(String id, String version) {
        if (id.equals("helloAll")) {
            return (T) helloAll;
        }
        throw new BundleException("请求的服务ID不存在");
    }

    public <T> T getService(String id) {
        return (T) getService(id, "1.0");
    }

    public <T> T getService(Class<T> clazz, String version) {
        if (HelloAll.class.isAssignableFrom(clazz)) {
            return (T) helloAll;
        }
        throw new BundleException("请求的服务ID不存在");
    }

    public <T> T getService(Class<T> clazz) {
        return (T) getService(clazz, "1.0");
    }

    public <T> void setService(T service, Class<T> clazz) {
        if (service instanceof Hello) {
            helloAll.setHello((Hello) service);
        }
    }


    public void setBundleManager(BundleManager bundleManager) {


    }

    public void setBundleDefine(BundleDefine bundleDefine) {

    }
}
