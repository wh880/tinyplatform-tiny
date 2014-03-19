package org.tinygroup.bundle.impl;

import org.tinygroup.bundle.Bundle;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.InstanceCreator;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.config.BundleService;
import org.tinygroup.bundle.exception.BundleException;
import org.tinygroup.bundle.util.BundleUtil;
import org.tinygroup.commons.version.VersionCompareUtil;
import org.tinygroup.i18n.I18nMessageFactory;
import org.tinygroup.i18n.I18nMessages;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springutil.SpringUtil;

import java.util.*;
import java.util.Map.Entry;

/**
 * 插件基类，大多数的情况下，可以不必自己写插件实现类
 *
 * @author luoguo
 */
public abstract class BaseBundle implements Bundle {
    private BundleManager bundleManager;
    private BundleDefine bundleDefine;
    private I18nMessages i18nMessages = I18nMessageFactory.getI18nMessages();
    // 存放服务和服务对应的最高版本信息
    private Map<String, BundleService> idInfoMap = new HashMap<String, BundleService>();
    // 存放服务和所有服务的信息
    private Map<String, BundleService> idVersionInfoMap = new HashMap<String, BundleService>();
    // 存放服务实例
    private Map<BundleService, Object> serviceInstanceMap = new HashMap<BundleService, Object>();
    /**
     * application.xml中的配置信息，每条记录对应一个bundleService
     */
    private Logger logger = LoggerFactory.getLogger(BaseBundle.class);

    public void init() {

        logger.logMessage(LogLevel.DEBUG, "初始化插件[id:{0},version:{1}]的服务", bundleDefine.getId(), bundleDefine.getVersion());

        List<BundleService> list = this.bundleDefine.getBundleServices();
        for (BundleService service : list) {
            logger.logMessage(LogLevel.DEBUG, "初始化插件服务[id:{0},version:{1}]", service.getId(), service.getVersion());
            String id = service.getId();
            if (idInfoMap.containsKey(id)) {
                int compare = VersionCompareUtil.compareVersion(idInfoMap.get(id).getVersion(), service.getVersion());
                if (compare == -1) {
                    idInfoMap.put(id, service);
                    logger.logMessage(LogLevel.DEBUG, "插件服务[id:{0},version:{1}]是当前最高版本", service.getId(), service.getVersion());
                }
            } else {
                idInfoMap.put(id, service);
                logger.logMessage(LogLevel.DEBUG, "插件服务[id:{0},version:{1}]是当前最高版本", service.getId(), service.getVersion());
            }
            initServiceInstance(service);
            idVersionInfoMap.put(getKey(service), service);
            logger.logMessage(LogLevel.DEBUG, "初始化插件服务[id:{0},version:{1}]完成", service.getId(), service.getVersion());
        }

        if (bundleManager == null) {
            bundleManager = SpringUtil.getBean("bundleManager");
        }

        logger.logMessage(LogLevel.DEBUG, "初始化插件[id:{0},version:{1}]的服务完成", bundleDefine.getId(), bundleDefine.getVersion());

    }

    protected Map<BundleService, Object> getAllServiceMap() {
        return serviceInstanceMap;
    }


    protected abstract ClassLoader getThisClassLoader();

    /**
     * 子类可以通过更新写方法来变化实例的创建方式
     *
     * @param service
     */
    protected void initServiceInstance(BundleService service) {
        try {

            String className = service.getClassName();
            Class<?> clazz = getThisClassLoader().loadClass(className);
            Object bean = null;
            //TODO 这里要处理从Spring获取InstanceCreator的问题
            InstanceCreator creator = null;

            bean = creator.getInstance(className, null);

            if (bean != null) {
                serviceInstanceMap.put(service, bean);
            } else {
                logger.logMessage(LogLevel.ERROR, "服务[id:{0}，version:{1}]的实例获取失败，请检查配置！", service.getId(), service.getVersion());
            }
        } catch (Exception e) {
            logger.errorMessage("初始化插件服务[id:{0},version:{1}]出错,", e, service.getId(), service.getVersion());
            throw new BundleException(i18nMessages.getMessage("bundle.serviceClassNotFound", service.getId(), service.getVersion()), e);
        }
    }

    protected String getKey(BundleService service) {
        return getKey(service.getId(), service.getVersion());
    }

    protected String getKey(String id, String version) {
        return BundleUtil.getKey(id, version);
    }

    public void destroy() {
        idInfoMap.clear();
        idVersionInfoMap.clear();
        serviceInstanceMap.clear();
    }

    public <T> T getService(String id, String version) {
        if (id == null || "".equals(id)) {
            throw new BundleException(i18nMessages.getMessage("bundle.serviceIdIsNull"));
        }
        if (version == null) {
            return (T) getService(id);
        }
        BundleService service = idVersionInfoMap.get(getKey(id, version));
        return (T) getService(service);

    }

    public <T> T getService(String id) {
        BundleService service = idInfoMap.get(id);
        return (T) getService(service);
    }

    protected <T> T getService(BundleService service) {
        if (service == null) {
            return null;
        }
        Object object = serviceInstanceMap.get(service);
        if (object == null) {
            return null;
        }
        return (T) object;
    }

    public void setBundleManager(BundleManager bundleManager) {
        this.bundleManager = bundleManager;
    }

    public BundleManager getBundleManager() {
        return bundleManager;
    }

    public void setBundleDefine(BundleDefine bundleDefine) {
        this.bundleDefine = bundleDefine;
    }

    public void start() {

    }

    public void pause() {


    }

    public void stop() {

    }

    public <T> T getService(Class<T> clazz, String version) {
        logger.logMessage(LogLevel.DEBUG, "通过Class与version获取插件服务");
        if (version == null || "".equals(version)) {
            logger.logMessage(LogLevel.DEBUG, "通过Class与version获取插件服务,传入的version为空");
            return null;
        }
        Set<Entry<BundleService, Object>> set = this.serviceInstanceMap.entrySet();
        Iterator<Entry<BundleService, Object>> iterator = set.iterator();

        while (iterator.hasNext()) {
            Entry<BundleService, Object> entry = iterator.next();
            Object o = entry.getValue();
            if (o.getClass().isAssignableFrom(clazz)) {
                BundleService service = entry.getKey();
                if (version.equals(service.getVersion())) {
                    logger.logMessage(LogLevel.DEBUG, "通过Class与version获取插件服务,返回服务[id{0},version:{1}]", service.getId(), service.getVersion());
                    return (T) o;
                }
            }
        }
        logger.logMessage(LogLevel.DEBUG, "通过Class与version获取插件服务,未查找到合适的服务");
        return null;
    }

    public <T> T getService(Class<T> clazz) {
        logger.logMessage(LogLevel.DEBUG, "通过Class获取插件服务");
        Set<Entry<BundleService, Object>> set = this.serviceInstanceMap.entrySet();
        Iterator<Entry<BundleService, Object>> iterator = set.iterator();
        BundleService service = null;
        while (iterator.hasNext()) {
            Entry<BundleService, Object> entry = iterator.next();
            Object o = entry.getValue();
            if (o.getClass().isAssignableFrom(clazz)) {
                service = entry.getKey();
                break;
            }
        }
        if (service == null) {
            logger.logMessage(LogLevel.DEBUG, "通过Class获取插件服务,未查找到合适的服务");
            return null;
        }
        // 获取当前id对应service的最高版本
        service = idInfoMap.get(service.getId());
        logger.logMessage(LogLevel.DEBUG, "通过Class获取插件服务,返回服务[id{0},version:{1}]", service.getId(), service.getVersion());
        return (T) serviceInstanceMap.get(service);
    }

    public <T> void setService(T service, Class<T> clazz) {

    }


    public Map<String, BundleService> getIdInfoMap() {
        return idInfoMap;
    }

    public Map<String, BundleService> getIdVersionInfoMap() {
        return idVersionInfoMap;
    }

    public Map<BundleService, Object> getServiceInstanceMap() {
        return serviceInstanceMap;
    }

    public BundleDefine getBundleDefine() {
        return bundleDefine;
    }

}
