package org.tinygroup.bundle.impl;

import org.tinygroup.bundle.*;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.config.BundleDependency;
import org.tinygroup.bundle.exception.BundleException;
import org.tinygroup.bundle.util.BundleUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

import java.util.*;

import static org.tinygroup.logger.LogLevel.INFO;

/**
 * 插件管理器
 *
 * @author luoguo
 */
public class BundleManagerImpl implements BundleManager {


    private Logger logger = LoggerFactory.getLogger(BundleManagerImpl.class);
    /**
     * key=id+"-"+version
     */
    private final Map<String, BundleDefine> idVersionMap = new HashMap<String, BundleDefine>();
    /**
     * key=id
     */
    private final Map<String, BundleDefine> idMap = new HashMap<String, BundleDefine>();
    /**
     * 比较器
     */
    private BundleDefineCompare bundleDefineCompare = new BundleDefineCompareImpl();
    private final Map<BundleDefine, Bundle> pluginMap = new HashMap<BundleDefine, Bundle>();
    private final Map<BundleDefine, Integer> pluginStatus = new HashMap<BundleDefine, Integer>();
    private final List<BundleDefine> bundleDefineList = new ArrayList<BundleDefine>();
    private BundleLoader bundleLoader;

    private BundleContext bundleContext = null;//TODO创建

    public BundleDefineCompare getBundleDefineCompare() {
        return bundleDefineCompare;
    }

    public void setBundleDefineCompare(BundleDefineCompare bundleDefineCompare) {
        this.bundleDefineCompare = bundleDefineCompare;
    }

    public void add(BundleDefine bundleDefine) {

        logger.logMessage(LogLevel.INFO, "开始加载插件[id:{0}, version:{1}, type:{2}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

        if (idVersionMap.containsKey(getKey(bundleDefine))) {
            logger.logMessage(INFO, "插件[id:{0}, version:{1}, type:{2}]已存在，无需加载", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
            return;
        }

        bundleDefineList.add(bundleDefine);
        idVersionMap.put(getKey(bundleDefine), bundleDefine);

        BundleDefine info = idMap.get(bundleDefine.getId());
        try {
            logger.logMessage(INFO, "开始创建插件[id:{0}, version:{1}, type:{2}]实例", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

            logger.logMessage(INFO, "插件[id:{0}, version:{1}, type:{2}]实例已创建完成", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
            Bundle bundle = null;
            logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]对应的loader不存在", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
            logger.logMessage(LogLevel.INFO, "采用系统Loader实例化");
            bundle = (Bundle) Class.forName(bundleDefine.getType()).newInstance();

            bundle.setBundleManager(this);
            bundle.setBundleDefine(bundleDefine);
            pluginMap.put(bundleDefine, bundle);
            if (info == null) {
                idMap.put(bundleDefine.getId(), bundleDefine);
            } else if (bundleDefineCompare.compare(bundleDefine, info) > 0) {
                idMap.put(bundleDefine.getId(), bundleDefine);

            }
            logger.log(INFO, "plugin.add", bundleDefine.getId(), bundleDefine.getVersion());
        } catch (Exception e) {
            logger.errorMessage("加载插件[id:{0}, version:{1}, type:{2}]时出错", e, bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
        }
    }


    private String getKey(BundleDefine bundleDefine) {
        return getKey(bundleDefine.getId(), bundleDefine.getVersion());
    }

    private String getKey(String id, String version) {
        return BundleUtil.getKey(id, version);
    }

    public void add(BundleDefine[] bundleDefines) {
        for (BundleDefine bundleDefine : bundleDefines) {
            add(bundleDefine);
        }
    }

    public void add(Collection<BundleDefine> bundleDefineCollection) {
        for (BundleDefine bundleDefine : bundleDefineCollection) {
            add(bundleDefine);
        }
    }

    public int status(BundleDefine bundleDefine) {
        int status = BundleManager.STATUS_UNINITIALIZED;
        synchronized (pluginStatus) {
            Integer s = pluginStatus.get(bundleDefine);
            if (s != null) {
                status = s;
            }
        }
        return status;
    }

    public void init(BundleDefine bundleDefine) {

        logger.logMessage(LogLevel.INFO, "开始初始化插件[id:{0}, version:{1}, type:{2}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

        int status = status(bundleDefine);// 获取插件初始状态
        // 判断是否是未初始化
        if (status != BundleManager.STATUS_UNINITIALIZED) {
            logger.logMessage(INFO, "插件[id:{0}, version:{1}]状态是[{2}],退出初始化", bundleDefine.getId(), bundleDefine.getVersion(), getStatusDescription(status));
            return;
        }

        // 初始化插件实例
        try {
            setStatus(bundleDefine, BundleManager.STATUS_INITING);
            pluginMap.get(bundleDefine).init(bundleContext);
            setStatus(bundleDefine, BundleManager.STATUS_INITED);
        } catch (BundleException e) {
            setStatus(bundleDefine, status);
            logger.error("插件[id:{0}, version:{1}, type:{2}]初始化时出错,恢复状态为[{3}]", e, bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));
            throw e;
        }
        logger.log(INFO, "plugin.init", bundleDefine.getId(), bundleDefine.getVersion());
    }

    private void setStatus(BundleDefine bundleDefine, int status) {
        synchronized (pluginStatus) {
            pluginStatus.put(bundleDefine, status);
        }
    }

    public void start(BundleDefine bundleDefine) {

        logger.logMessage(LogLevel.INFO, "开始启动插件[id:{0}, version:{1}, type:{2}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

        int status = status(bundleDefine);
        // 如果是已start则返回
        if (status == BundleManager.STATUS_READY) {

            logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]状态是[{3}]，退出启动", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));

            return;
        }

        // 如果未初始化,则初始化
        if (status == BundleManager.STATUS_UNINITIALIZED) {
            init(bundleDefine);
            status = status(bundleDefine);
        }
        // 如果未组装,则组装
        if (status == BundleManager.STATUS_INITED) {
            assemble(bundleDefine);
            status = status(bundleDefine);
        }
        // 如果是组装OK或暂停状态
        if (status == BundleManager.STATUS_ASSEMBLEED || status == BundleManager.STATUS_PAUSED) {
            try {

                logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]状态为[{3}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));

                Bundle bundle = pluginMap.get(bundleDefine);
                setStatus(bundleDefine, BundleManager.STATUS_STARTING);
                // 先启动依赖的项
                startDependency(bundleDefine);
                bundle.start(bundleContext);
                setStatus(bundleDefine, BundleManager.STATUS_READY);
            } catch (BundleException e) {
                setStatus(bundleDefine, status);
                logger.logMessage(LogLevel.ERROR, "插件[id:{0}, version:{1}, type:{2}]启动时出错，恢复状态为[{3}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));
                throw e;
            }
        }
        logger.log(INFO, "plugin.start", bundleDefine.getId(), bundleDefine.getVersion());
    }

    private void startDependency(BundleDefine bundleDefine) {
        logger.logMessage(LogLevel.INFO, "启动插件[id:{0}, version:{1}, type:{2}]依赖的项", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
        if (bundleDefine.getBundleDependencies() != null) {
            for (BundleDependency pluginDependency : bundleDefine.getBundleDependencies()) {

                logger.logMessage(LogLevel.INFO, "启动依赖的项[plugin-id:{0},plugin-version:{1}]", pluginDependency.getBundleId(), pluginDependency.getBundleVersion());

                BundleDefine dependBundle = getDependencyBundleDefine(pluginDependency);
                start(dependBundle);

                logger.logMessage(LogLevel.INFO, "依赖的项[plugin-id:{0},plugin-version:{1}]启动完成", pluginDependency.getBundleId(), pluginDependency.getBundleVersion());
            }
        }
        logger.logMessage(LogLevel.INFO, "启动插件[id:{0}, version:{1}, type:{2}]依赖的项完成", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
    }

    public void stop(BundleDefine bundleDefine) {

        logger.logMessage(LogLevel.INFO, "开始停止插件[id:{0}, version:{1}, type:{2}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

        int status = status(bundleDefine);
        // 如果状态已是已装配，则直接返回
        if (status == BundleManager.STATUS_ASSEMBLEED) {
            logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]状态为[{3}]，退出停止", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));
            return;
        }

        // 如果是初始化OK或暂停状态
        if (status == BundleManager.STATUS_READY || status == BundleManager.STATUS_PAUSED) {
            try {
                logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]状态为[{3}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));

                Bundle bundle = pluginMap.get(bundleDefine);
                setStatus(bundleDefine, BundleManager.STATUS_STOPING);
                // 先要停止依赖当前插件的项
                stopDependencyBy(bundleDefine);
                bundle.stop(bundleContext);
                setStatus(bundleDefine, BundleManager.STATUS_ASSEMBLEED);
            } catch (BundleException e) {
                setStatus(bundleDefine, status);
                logger.logMessage(LogLevel.ERROR, "插件[id:{0}, version:{1}, type:{2}]停止时出错，恢复状态为[{3}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));
                throw e;
            }
        }
        logger.log(INFO, "plugin.stop", bundleDefine.getId(), bundleDefine.getVersion());
    }

    private void stopDependencyBy(BundleDefine bundleDefine) {

        logger.logMessage(LogLevel.INFO, "开始停止依赖插件[id:{0}, version:{1}, type:{2}]的项", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
        for (BundleDefine define : bundleDefineList) {
            if (define.getBundleDependencies() != null) {
                for (BundleDependency pluginDependency : define.getBundleDependencies()) {
                    BundleDefine dependency = getDependencyBundleDefine(pluginDependency);
                    if (dependency.equals(bundleDefine)) {
                        logger.logMessage(LogLevel.INFO, "开始停止依赖项[id:{0},version:{1}]", dependency.getId(), dependency.getVersion());
                        stop(define);
                        logger.logMessage(LogLevel.INFO, "依赖项[id:{0},version:{1}]停止完成", dependency.getId(), dependency.getVersion());
                    }
                }
            }
        }
        logger.logMessage(LogLevel.INFO, "停止依赖插件[id:{0}, version:{1}, type:{2}]的项完成", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
    }

    private BundleDefine getDependencyBundleDefine(BundleDependency pluginDependency) {
        BundleDefine dependency = null;
        if (pluginDependency.getBundleVersion() == null) {
            dependency = getBundleDefine(pluginDependency.getBundleId());
        } else {
            dependency = getBundleDefine(pluginDependency.getBundleId(), pluginDependency.getBundleVersion());
        }
        if (dependency == null) {
            throw new BundleException(String.format("插件[plugin-id:%s,plugin-version:%s]不存在", pluginDependency.getBundleId(), pluginDependency.getBundleVersion()));
        }
        return dependency;
    }

    public void pause(BundleDefine bundleDefine) {

        logger.logMessage(LogLevel.INFO, "开始暂停插件[id:{0}, version:{1}, type:{2}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

        int status = status(bundleDefine);
        // 如果是初始化OK或暂停状态
        if (status == BundleManager.STATUS_READY) {
            try {
                logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]状态为[{3}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));

                Bundle bundle = pluginMap.get(bundleDefine);
                setStatus(bundleDefine, BundleManager.STATUS_PAUSING);
                // 先要暂停依赖的项
                pauseDependencyBy(bundleDefine);
                bundle.pause(bundleContext);
                setStatus(bundleDefine, BundleManager.STATUS_PAUSED);
            } catch (BundleException e) {
                setStatus(bundleDefine, status);
                logger.logMessage(LogLevel.ERROR, "插件[id:{0}, version:{1}, type:{2}]暂停时出错，恢复状态为[{3}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));
                throw e;
            }
        } else {
            logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]状态为[{3}]，退出暂停", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));
            return;
        }
        logger.log(INFO, "plugin.pause", bundleDefine.getId(), bundleDefine.getVersion());
    }

    private void pauseDependencyBy(BundleDefine bundleDefine) {
        logger.logMessage(LogLevel.INFO, "暂停依赖插件[id:{0}, version:{1}, type:{2}]的项", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
        for (BundleDefine define : bundleDefineList) {
            if (define.getBundleDependencies() != null) {
                for (BundleDependency pluginDependency : define.getBundleDependencies()) {
                    BundleDefine dependency = getDependencyBundleDefine(pluginDependency);
                    if (dependency.equals(bundleDefine)) {
                        logger.logMessage(LogLevel.INFO, "暂停依赖项[id:{0},version{1}]", dependency.getId(), dependency.getVersion());
                        pause(define);
                        logger.logMessage(LogLevel.INFO, "依赖项[id:{0},version{1}]暂停完成", dependency.getId(), dependency.getVersion());
                    }
                }
            }
        }

        logger.logMessage(LogLevel.INFO, "暂停依赖插件[id:{0}, version:{1}, type:{2}]的项完成", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
    }

    public void destroy(BundleDefine bundleDefine) {

        logger.logMessage(LogLevel.INFO, "开始销毁插件[id:{0}, version:{1}, type:{2}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

        int status = status(bundleDefine);
        /**
         * 如果是未初始化，则结束
         */
        if (status == BundleManager.STATUS_UNINITIALIZED) {
            logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]状态为[{3}]，退出销毁", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));
            return;
        }
        // 只有是停止状态才可以被销毁
        if (status == BundleManager.STATUS_READY || status == BundleManager.STATUS_PAUSED) {

            stop(bundleDefine);
            status = status(bundleDefine);
        }
        // 如果已经停止,则分解它
        if (status == BundleManager.STATUS_ASSEMBLEED) {
            disassemble(bundleDefine);
            status = status(bundleDefine);
        }

        if (status == BundleManager.STATUS_INITED) {
            try {
                Bundle bundle = pluginMap.get(bundleDefine);
                setStatus(bundleDefine, BundleManager.STATUS_DESTORYING);
                disassembleDependencyBy(bundleContext, bundleDefine);
                bundle.destroy(bundleContext);
                // pluginMap.remove(bundleDefine); 20120831不应该remover，否则插件无法再次被初始化
                setStatus(bundleDefine, BundleManager.STATUS_UNINITIALIZED);
            } catch (BundleException e) {
                setStatus(bundleDefine, status);
                logger.logMessage(LogLevel.ERROR, "插件[id:{0}, version:{1}, type:{2}]销毁时出错，恢复状态为[{3}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));
                throw e;
            }
        }

        logger.log(INFO, "plugin.destroy", bundleDefine.getId(), bundleDefine.getVersion());
    }

    private void disassembleDependencyBy(BundleContext bundleContext, BundleDefine bundleDefine) {

        logger.logMessage(LogLevel.INFO, "卸载依赖插件[id:{0}, version:{1}, type:{2}]的项", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

        for (BundleDefine define : bundleDefineList) {
            if (!define.equals(bundleDefine) && define.getBundleDependencies() != null) {
                for (BundleDependency pluginDependency : define.getBundleDependencies()) {
                    BundleDefine dependency = getDependencyBundleDefine(pluginDependency);
                    if (dependency.equals(bundleDefine)) {
                        logger.logMessage(LogLevel.INFO, "卸载依赖项[id:{0}, version:{1}]", dependency.getId(), dependency.getVersion());
                        disassemble(define);
                        logger.logMessage(LogLevel.INFO, "卸载依赖项[id:{0}, version:{1}]完成", dependency.getId(), dependency.getVersion());
                        cleanDependencyService(define, bundleDefine, pluginDependency);
                    }
                }
            }
        }

        logger.logMessage(LogLevel.INFO, "卸载依赖插件[id:{0}, version:{1}, type:{2}]的项完成", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

    }

    /**
     * 清空依赖的服务
     *
     * @param bundleDefine       依赖者
     * @param dependBundleDefine 被依赖者
     * @param pluginDependency   依赖描述
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void cleanDependencyService(BundleDefine bundleDefine, BundleDefine dependBundleDefine,
                                        BundleDependency pluginDependency) {

        logger.logMessage(LogLevel.INFO, "清空插件[id:{0}, version:{1}, type:{2}]依赖的服务[plugin-id:{3},plugin-version:{4},service-id={5},service-version={6}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), pluginDependency.getBundleId(), pluginDependency.getBundleVersion(), pluginDependency.getServiceId(), pluginDependency.getServiceVersion());

        String serviceVersion = pluginDependency.getServiceVersion();
        String serviceId = pluginDependency.getServiceId();
        String serviceType = pluginDependency.getServiceType();
        try {
            if (serviceVersion == null) {
                if (serviceId == null) {
                    Class clazz = Class.forName(serviceType);
                    pluginMap.get(bundleDefine).setService(null, clazz);

                } else {
                    Object object = pluginMap.get(dependBundleDefine).getService(serviceId);
                    Class clazz = object.getClass();
                    pluginMap.get(bundleDefine).setService(null, clazz);
                }
            } else {
                if (serviceId == null) {
                    Class clazz = Class.forName(serviceType);
                    pluginMap.get(bundleDefine).setService(null, clazz);

                } else {
                    Object object = pluginMap.get(dependBundleDefine).getService(serviceId, serviceVersion);
                    Class clazz = object.getClass();
                    pluginMap.get(bundleDefine).setService(null, clazz);
                }
            }
            logger.logMessage(LogLevel.INFO, "清空插件[id:{0}, version:{1}, type:{2}]依赖的服务[plugin-id:{3},plugin-version:{4},service-id={5},service-version={6}]完成", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), pluginDependency.getBundleId(), pluginDependency.getBundleVersion(), pluginDependency.getServiceId(), pluginDependency.getServiceVersion());
        } catch (Exception e) {
            logger.logMessage(LogLevel.INFO, "清空插件[id:{0}, version:{1}, type:{2}]依赖的服务[plugin-id:{3},plugin-version:{4},service-id={5},service-version={6}]时出错", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), pluginDependency.getBundleId(), pluginDependency.getBundleVersion(), pluginDependency.getServiceId(), pluginDependency.getServiceVersion());
            throw new BundleException(e.getMessage(), e);
        }
    }

    public void init() {
        logger.logMessage(LogLevel.INFO, "开始初始化所有插件");
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                init(bundleDefine);
            } catch (BundleException e) {
                processException(e);
            }
        }
        logger.logMessage(LogLevel.INFO, "所有插件已初始化");
    }

    public void start() {
        logger.logMessage(LogLevel.INFO, "开始启动所有插件");
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                start(bundleDefine);
            } catch (BundleException e) {
                //TODO 添加日志
            }
        }
        logger.logMessage(LogLevel.INFO, "所有插件已启动");
    }

    public void stop() {
        logger.logMessage(LogLevel.INFO, "开始停止所有插件");
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                stop(bundleDefine);
            } catch (BundleException e) {
                processException(e);

            }
        }
        logger.logMessage(LogLevel.INFO, "所有插件已停止");
    }

    public void pause() {
        logger.logMessage(LogLevel.INFO, "开始暂停所有插件");
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                pause(bundleDefine);
            } catch (BundleException e) {
                processException(e);

            }
        }
        logger.logMessage(LogLevel.INFO, "所有插件已暂停");
    }

    public void destroy() {
        logger.logMessage(LogLevel.INFO, "开始销毁所有插件");
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                destroy(bundleDefine);
            } catch (BundleException e) {
                processException(e);

            }
        }
        logger.logMessage(LogLevel.INFO, "所有插件已销毁");
    }

    private void processException(BundleException e) {
        logger.errorMessage(e.getMessage(), e);
    }

    public <T> T getService(BundleDefine bundleDefine, Class<T> clazz) {
        int status = status(bundleDefine);
        if (status == BundleManager.STATUS_READY) {
            return pluginMap.get(bundleDefine).getService(clazz);
        }
        throw getNotReadyException();
    }

    private BundleException getNotReadyException() {
        logger.logMessage(LogLevel.ERROR, "插件状态不是STATUS_READY");
        return new BundleException("插件状态不是STATUS_READY");
    }

    public <T> T getService(BundleDefine bundleDefine, Class<T> clazz, String version) {
        int status = status(bundleDefine);
        if (status == BundleManager.STATUS_READY) {
            return pluginMap.get(bundleDefine).getService(clazz, version);
        }
        throw getNotReadyException();

    }

    public BundleDefine getBundleDefine(String id, String version) {
        return idVersionMap.get(getKey(id, version));
    }

    public BundleDefine getBundleDefine(String id) {
        return idMap.get(id);
    }

    public <T> T getService(BundleDefine bundleDefine, String id) {
        int status = status(bundleDefine);
        if (status == BundleManager.STATUS_READY) {
            return (T) pluginMap.get(bundleDefine).getService(id);
        }
        throw getNotReadyException();
    }

    public <T> T getService(BundleDefine bundleDefine, String id, String version) {
        int status = status(bundleDefine);
        if (status == BundleManager.STATUS_READY) {
            return (T) pluginMap.get(bundleDefine).getService(id, version);
        }
        throw getNotReadyException();
    }

    public void assemble(BundleDefine bundleDefine) {

        logger.logMessage(LogLevel.INFO, "开始装载插件[id:{0}, version:{1}, type:{2}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

        int status = status(bundleDefine);
        // 如果是未初始化状态，先初始化之
        if (status == BundleManager.STATUS_UNINITIALIZED) {
            init(bundleDefine);
            status = status(bundleDefine);
        }
        // 如果是已初始化状态
        if (status == BundleManager.STATUS_INITED) {
            try {
                setStatus(bundleDefine, BundleManager.STATUS_ASSEMBLEING);
                assembleDependency(bundleDefine);
                setStatus(bundleDefine, BundleManager.STATUS_ASSEMBLEED);
            } catch (BundleException e) {
                setStatus(bundleDefine, status);
                logger.logMessage(LogLevel.ERROR, "插件[id:{0}, version:{1}, type:{2}]装载时出错，恢复状态为[{3}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));
                throw e;
            }
        }

        logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]已装载完成", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
    }

    private void assembleDependency(BundleDefine bundleDefine) {
        logger.logMessage(LogLevel.INFO, "装载插件[id:{0}, version:{1}, type:{2}]依赖的项", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

        if (bundleDefine.getBundleDependencies() != null) {
            for (BundleDependency pluginDependency : bundleDefine.getBundleDependencies()) {

                logger.logMessage(LogLevel.INFO, "装载依赖的项[plugin-id:{0},plugin-version:{1}]", pluginDependency.getBundleId(), pluginDependency.getBundleVersion());

                BundleDefine dependBundleDefine = getDependencyBundleDefine(pluginDependency);
                assemble(dependBundleDefine);
                setDependencyService(bundleDefine, dependBundleDefine, pluginDependency);

                logger.logMessage(LogLevel.INFO, "装载依赖的项[plugin-id:{0},plugin-version:{1}]完成", pluginDependency.getBundleId(), pluginDependency.getBundleVersion());
            }
        }
        logger.logMessage(LogLevel.INFO, "装载插件[id:{0}, version:{1}, type:{2}]依赖的项完成", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void setDependencyService(BundleDefine bundleDefine, BundleDefine dependBundleDefine,
                                      BundleDependency pluginDependency) {
        String serviceVersion = pluginDependency.getServiceVersion();
        String serviceId = pluginDependency.getServiceId();
        String serviceType = pluginDependency.getServiceType();

        logger.logMessage(LogLevel.INFO, "设置插件[id:{0}, version:{1}, type:{2}]依赖的服务[plugin-id:{3},plugin-version:{4},service-id={5},service-version={6}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), pluginDependency.getBundleId(), pluginDependency.getBundleVersion(), pluginDependency.getServiceId(), pluginDependency.getServiceVersion());

        try {
            if (serviceVersion == null) {
                if (serviceId == null) {
                    Class clazz = Class.forName(serviceType);
                    pluginMap.get(bundleDefine).setService(pluginMap.get(dependBundleDefine).getService(clazz), clazz);

                } else {
                    Object object = pluginMap.get(dependBundleDefine).getService(serviceId);
                    Class clazz = object.getClass();
                    pluginMap.get(bundleDefine).setService(object, clazz);
                }
            } else {
                if (serviceId == null) {
                    Class clazz = Class.forName(serviceType);
                    pluginMap.get(bundleDefine).setService(pluginMap.get(dependBundleDefine).getService(clazz, serviceVersion), clazz);

                } else {
                    Object object = pluginMap.get(dependBundleDefine).getService(serviceId, serviceVersion);
                    Class clazz = object.getClass();
                    pluginMap.get(bundleDefine).setService(object, clazz);
                }
            }

            logger.logMessage(LogLevel.INFO, "设置插件[id:{0}, version:{1}, type:{2}]依赖的服务[plugin-id:{3},plugin-version:{4},service-id={5},service-version={6}]完成", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), pluginDependency.getBundleId(), pluginDependency.getBundleVersion(), pluginDependency.getServiceId(), pluginDependency.getServiceVersion());
        } catch (Exception e) {
            logger.logMessage(LogLevel.INFO, "设置插件[id:{0}, version:{1}, type:{2}]依赖的服务[plugin-id:{3},plugin-version:{4},service-id={5},service-version={6}]出错", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), pluginDependency.getBundleId(), pluginDependency.getBundleVersion(), pluginDependency.getServiceId(), pluginDependency.getServiceVersion());
            throw new BundleException(e.getMessage(), e);
        }
    }

    public void assemble() {
        logger.logMessage(LogLevel.INFO, "开始装载所有插件");
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                assemble(bundleDefine);
            } catch (BundleException e) {
                processException(e);
            }
        }
        logger.logMessage(LogLevel.INFO, "所有插件已装载");
    }

    public void disassemble(BundleDefine bundleDefine) {

        logger.logMessage(LogLevel.INFO, "开始卸载插件[id:{0}, version:{1}, type:{2}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());

        int status = status(bundleDefine);

        if (status == BundleManager.STATUS_READY || status == BundleManager.STATUS_PAUSED) {
            stop(bundleDefine);
            status = status(bundleDefine);
        }
        // 只有是停止状态才可以被销毁
        if (status == BundleManager.STATUS_ASSEMBLEED) {
            try {
                setStatus(bundleDefine, BundleManager.STATUS_DESTORYING);
                disassembleDependencyBy(bundleContext, bundleDefine);

                setStatus(bundleDefine, BundleManager.STATUS_INITED);
            } catch (BundleException e) {
                setStatus(bundleDefine, status);
                logger.logMessage(LogLevel.ERROR, "插件[id:{0}, version:{1}, type:{2}]卸载时出错，恢复状态为[{3}]", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType(), getStatusDescription(status));
                throw e;
            }
        }
        logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]已卸载完成", bundleDefine.getId(), bundleDefine.getVersion(), bundleDefine.getType());
    }

    public void disassemble() {
        logger.logMessage(LogLevel.INFO, "开始卸载所有插件");
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                disassemble(bundleDefine);
            } catch (BundleException e) {
                processException(e);
            }
        }
        logger.logMessage(LogLevel.INFO, "所有插件已卸载");
    }

    public int size() {
        return bundleDefineList.size();
    }

    public List<BundleDefine> getAllBundleDefine() {
        return bundleDefineList;
    }


    public void remove(BundleDefine plugin) {

        logger.logMessage(LogLevel.INFO, "开始移除插件[id:{0}, version:{1}, type:{2}]", plugin.getId(), plugin.getVersion(), plugin.getType());
        // 删除指定的plugin
        destroy(plugin);
        bundleDefineList.remove(plugin);
        idVersionMap.remove(getKey(plugin));
        pluginMap.remove(plugin);
        pluginStatus.remove(plugin);
        bundleLoader.remove(plugin);

        // 如果当前plugin是同类中最高版本(即被存放与idMap)，则将其中map中删除
        if (!idMap.get(plugin.getId()).getVersion().equals(plugin.getVersion())) {

            logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]已移除", plugin.getId(), plugin.getVersion(), plugin.getType());

            return;
        }
        logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]是当前插件最高版本", plugin.getId(), plugin.getVersion(), plugin.getType());

        idMap.remove(plugin.getId());

        logger.logMessage(LogLevel.INFO, "开始为插件[id:{0}]计算新的最高版本", plugin.getId());

        List<BundleDefine> list = new ArrayList<BundleDefine>();
        for (BundleDefine p : bundleDefineList) { // 查询是否有与plugin同id但不同版本的
            if (p.getId().equals(plugin.getId())) {
                list.add(p);
            }
        }
        if (list.size() <= 0) {
            logger.logMessage(LogLevel.INFO, "插件[id:{0}]已无其他版本", plugin.getId());
            logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]已移除", plugin.getId(), plugin.getVersion(), plugin.getType());
            return;
        }
        if (list.size() == 1) { // 有且只有一个，则直接将该版本设置为最高版本，放入idMap
            idMap.put(list.get(0).getId(), list.get(0));
            logger.logMessage(LogLevel.INFO, "插件[id:{0}]最新版本为[version:{1}]", plugin.getId(), list.get(0).getVersion());
            logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]已移除", plugin.getId(), plugin.getVersion(), plugin.getType());
            return;
        }
        BundleDefine newBundle = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            BundleDefine p1 = list.get(i);
            if (bundleDefineCompare.compare(p1, newBundle) > 0) {
                newBundle = p1;
            }
        }
        idMap.put(newBundle.getId(), newBundle);
        logger.logMessage(LogLevel.INFO, "插件[id:{0}]最新版本为[version:{1}]", plugin.getId(), newBundle.getVersion());
        logger.logMessage(LogLevel.INFO, "插件[id:{0}, version:{1}, type:{2}]已移除", plugin.getId(), plugin.getVersion(), plugin.getType());
    }

    public void remove(Collection<BundleDefine> bundleDefineCollection) {
        logger.logMessage(LogLevel.INFO, "开始移除指定插件列表");
        for (BundleDefine define : bundleDefineCollection) {
            remove(define);
        }
        logger.logMessage(LogLevel.INFO, "移除指定插件列表完成");
    }

    public void removeAll() {
        logger.logMessage(LogLevel.INFO, "开始移除所有插件");

        for (int i = bundleDefineList.size() - 1; i >= 0; i--) {
            try {
                remove(bundleDefineList.get(i));
            } catch (BundleException e) {
                processException(e);
            }

        }
        logger.logMessage(LogLevel.INFO, "移除所有插件完成");
    }

    public void init(int level) {
        logger.logMessage(LogLevel.INFO, "开始初始化插件,初始化等级{0}", level);
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                if (bundleDefine.getLevel() <= level) {
                    init(bundleDefine);
                }
            } catch (BundleException e) {
                processException(e);
            }
        }
        logger.logMessage(LogLevel.INFO, "初始化插件完成,初始化等级{0}", level);
    }

    public void assemble(int level) {
        logger.logMessage(LogLevel.INFO, "开始装载插件,装载等级{0}", level);
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                if (bundleDefine.getLevel() <= level) {
                    assemble(bundleDefine);
                }
            } catch (BundleException e) {
                processException(e);
            }
        }
        logger.logMessage(LogLevel.INFO, "装载插件完成,装载等级{0}", level);
    }

    public void start(int level) {
        logger.logMessage(LogLevel.INFO, "开始启动插件,启动等级{0}", level);
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                if (bundleDefine.getLevel() <= level) {
                    start(bundleDefine);
                }
            } catch (BundleException e) {
                processException(e);
            }
        }
        logger.logMessage(LogLevel.INFO, "启动插件完成,启动等级{0}", level);
    }

    public void pause(int level) {
        logger.logMessage(LogLevel.INFO, "开始暂停插件,暂停等级{0}", level);
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                if (bundleDefine.getLevel() >= level) {
                    pause(bundleDefine);
                }
            } catch (BundleException e) {
                processException(e);
            }
        }
        logger.logMessage(LogLevel.INFO, "暂停插件完成,暂停等级{0}", level);
    }

    public void stop(int level) {
        logger.logMessage(LogLevel.INFO, "开始停止插件,停止等级{0}", level);
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                if (bundleDefine.getLevel() >= level) {
                    stop(bundleDefine);
                }
            } catch (BundleException e) {
                processException(e);
            }
        }
        logger.logMessage(LogLevel.INFO, "停止插件完成,停止等级{0}", level);
    }

    public void disassemble(int level) {
        logger.logMessage(LogLevel.INFO, "开始卸载插件,卸载等级{0}", level);
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                if (bundleDefine.getLevel() >= level) {
                    disassemble(bundleDefine);
                }
            } catch (BundleException e) {
                processException(e);
            }
        }
        logger.logMessage(LogLevel.INFO, "卸载插件完成,卸载等级{0}", level);
    }

    public void destroy(int level) {
        logger.logMessage(LogLevel.INFO, "开始销毁插件,销毁等级{0}", level);
        for (BundleDefine bundleDefine : bundleDefineList) {
            try {
                if (bundleDefine.getLevel() >= level) {
                    destroy(bundleDefine);
                }
            } catch (BundleException e) {
                processException(e);
            }
        }
        logger.logMessage(LogLevel.INFO, "销毁插件完成,销毁等级{0}", level);
    }

    public String getStatusDescription(int status) {
        switch (status) {
            case STATUS_UNINITIALIZED:
                return "未初始化";
            case STATUS_INITING:
                return "正在初始化";
            case STATUS_INITED:
                return "已初始化";
            case STATUS_ASSEMBLEING:
                return "正在装配";
            case STATUS_ASSEMBLEED:
                return "已装配";
            case STATUS_STARTING:
                return "正在启动";
            case STATUS_READY:
                return "已就绪";
            case STATUS_PAUSING:
                return "正在暂停";
            case STATUS_PAUSED:
                return "已暂停";
            case STATUS_STOPING:
                return "正在停止";
            case STATUS_DISASSEMBLEING:
                return "正在卸载(逆装配)";
            case STATUS_DESTORYING:
                return "正在销毁";
        }
        return "状态不可识别";
    }


    public void setBundleLoader(BundleLoader bundleLoader) {
        this.bundleLoader = bundleLoader;

    }

    public void load(String path) {

    }

    public List<BundleDefine> getBundleDefineList() {
        return bundleDefineList;
    }

    public BundleContext getBundleContext() {
        return bundleContext;
    }

}
