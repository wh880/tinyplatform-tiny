package org.tinygroup.bundle.impl;

import org.tinygroup.bundle.*;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.loader.TinyClassLoader;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springutil.SpringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luoguo on 2014/5/4.
 */
public class BundleManagerDefault implements BundleManager {
	private static Logger logger = LoggerFactory
			.getLogger(BundleManagerDefault.class);
	private Map<String, BundleDefine> bundleDefineMap = new HashMap<String, BundleDefine>();
	private Map<BundleDefine, TinyClassLoader> tinyClassLoaderMap = new HashMap<BundleDefine, TinyClassLoader>();
	private String bundleRoot;
	private String commonRoot;
	private BundleContext bundleContext = new BundleContextImpl();
	private TinyClassLoader tinyClassLoader = new TinyClassLoader();
	private List<BundleEvent> beforeStartBundleEvent = new ArrayList<BundleEvent>();
	private List<BundleEvent> afterStartBundleEvent = new ArrayList<BundleEvent>();
	private List<BundleEvent> beforeStopBundleEvent = new ArrayList<BundleEvent>();
	private List<BundleEvent> afterStopBundleEvent = new ArrayList<BundleEvent>();

	public void addBundleDefine(BundleDefine bundleDefine) {
		bundleDefineMap.put(bundleDefine.getName(), bundleDefine);
	}

	public BundleDefine getBundleDefine(String bundleName)
			throws BundleException {
		BundleDefine bundleDefine = bundleDefineMap.get(bundleName);
		if (bundleDefine != null) {
			return bundleDefine;
		}
		throw new BundleException("找不到杂物箱定义：" + bundleName);
	}

	/*
	 * TODO:不能直接remove，必须先停止，再remove (non-Javadoc)
	 * 
	 * @see
	 * org.tinygroup.bundle.BundleManager#removeBundle(org.tinygroup.bundle.
	 * config.BundleDefine)
	 */
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
		for (BundleDefine bundleDefine : bundleDefineMap.values()) {
			if (!tinyClassLoaderMap.containsKey(bundleDefine)) {// 如果说没有loader，就代表还没启动
				start(bundleContext, bundleDefine);
			}
		}
	}

	public void stop(BundleContext bundleContext) {
		for (BundleDefine bundleDefine : bundleDefineMap.values()) {
			if (tinyClassLoaderMap.containsKey(bundleDefine)) {// 如果说有loader，就代表还没停止
				stop(bundleContext, bundleDefine);
			}
		}
	}

	public void start(BundleContext bundleContext, String bundle) {
		logger.logMessage(LogLevel.INFO, "开始启动Bundle:{0}", bundle);
		if (tinyClassLoaderMap.containsKey(bundle)) {// 如果说有loader，就代表还已经启动
			logger.logMessage(LogLevel.INFO, "Bundle:{0}已启动，无需再次启动", bundle);
			return;
		}
		BundleDefine bundleDefine = bundleDefineMap.get(bundle);
		startBundle(bundleContext, bundleDefine, bundle);
		logger.logMessage(LogLevel.INFO, "启动Bundle:{0}完毕", bundle);
	}

	public void start(BundleContext bundleContext, BundleDefine bundleDefine) {
		String bundle = bundleDefine.getName();
		logger.logMessage(LogLevel.INFO, "开始启动Bundle:{0}", bundle);
		if (tinyClassLoaderMap.containsKey(bundle)) {// 如果说有loader，就代表还已经启动
			logger.logMessage(LogLevel.INFO, "Bundle:{0}已启动，无需再次启动", bundle);
			return;
		}
		startBundle(bundleContext, bundleDefine, bundle);
		logger.logMessage(LogLevel.INFO, "启动Bundle:{0}完毕", bundle);
	}

	private void startBundle(BundleContext bundleContext,
			BundleDefine bundleDefine, String bundle) {
		
		String[] dependens = bundleDefine.getDependencyArray(); // 获取所依赖的bundle项
		for (String dependen : dependens) { // 启动所有的依赖项
			logger.logMessage(LogLevel.DEBUG, "开始启动Bundle:{0}所依赖Bundle:{1}",
					bundle, dependen);
			start(bundleContext, dependen);
			logger.logMessage(LogLevel.DEBUG, "启动Bundle:{0}所依赖Bundle:{1}完毕",
					bundle, dependen);
		}

		processEvents(beforeStartBundleEvent, bundleContext, bundleDefine);

		// 执行activator
		String activatorBean = bundleDefine.getBundleActivator();
		if (activatorBean != null && !"".equals(activatorBean)) {
			BundleActivator activator = SpringUtil.getBean(activatorBean);
			try {
				activator.start(bundleContext);
			} catch (BundleException e) {
				logger.errorMessage("启动Bundle:{0}的Activator:{1}时出错", e, bundle,
						activatorBean);
			}
		}

		processEvents(afterStartBundleEvent, bundleContext, bundleDefine);
	}

	public void stop(BundleContext bundleContext, String bundle) {
		logger.logMessage(LogLevel.INFO, "开始停止Bundle:{0}", bundle);
		if (!tinyClassLoaderMap.containsKey(bundle)) {// 如果没有loader，就代表已停止
			logger.logMessage(LogLevel.INFO, "Bundle:{0}已停止，无需再次停止", bundle);
			return;
		}
		BundleDefine bundleDefine = bundleDefineMap.get(bundle);
		stopBundle(bundleContext, bundleDefine, bundle);
		logger.logMessage(LogLevel.INFO, "停止Bundle:{0}完毕", bundle);
	}

	public void stop(BundleContext bundleContext, BundleDefine bundleDefine) {
		String bundle = bundleDefine.getName();
		logger.logMessage(LogLevel.INFO, "开始停止Bundle:{0}", bundle);
		if (!tinyClassLoaderMap.containsKey(bundle)) {// 如果没有loader，就代表已停止
			logger.logMessage(LogLevel.INFO, "Bundle:{0}已停止，无需再次停止", bundle);
			return;
		}
		stopBundle(bundleContext, bundleDefine, bundle);
		logger.logMessage(LogLevel.INFO, "停止Bundle:{0}完毕", bundle);
	}

	private void stopBundle(BundleContext bundleContext,
			BundleDefine bundleDefine, String bundle) {
		//List<String> dependencyByList = bundleDefine.getDependencyByList();// 获取所依赖当前bundle的项
		
		List<String> dependencyByList = new ArrayList<String>();
		for(BundleDefine b:bundleDefineMap.values()){
			String[] dependencyArray = b.getDependencyArray();
			for(String dependency:dependencyArray){
				if(bundle.equals(dependency)){
					dependencyByList.add(b.getName());
				}
			}
		}
		
		for (String dependenBy : dependencyByList) { // 启动所有的依赖项
			logger.logMessage(LogLevel.DEBUG, "开始停止依赖Bundle:{0}的Bundle:{1}",
					bundle, dependenBy);
			stop(bundleContext, dependenBy);
			logger.logMessage(LogLevel.DEBUG, "停止依赖Bundle:{0}的Bundle:{1}完毕",
					bundle, dependenBy);
		}

		processEvents(beforeStopBundleEvent, bundleContext, bundleDefine);

		// 执行activator
		String activatorBean = bundleDefine.getBundleActivator();
		if (activatorBean != null && !"".equals(activatorBean)) {
			BundleActivator activator = SpringUtil.getBean(activatorBean);
			try {
				activator.stop(bundleContext);
			} catch (BundleException e) {
				logger.errorMessage("停止Bundle:{0}的Activator:{1}时出错", e, bundle,
						activatorBean);
			}
		}

		processEvents(afterStopBundleEvent, bundleContext, bundleDefine);
	}

	/**
	 * 针对某个bundleDefine处理事件
	 * 
	 * @param events
	 *            需要处理的事件列表
	 * @param bundleContext
	 * @param bundleDefine
	 */
	private void processEvents(List<BundleEvent> events,
			BundleContext bundleContext, BundleDefine bundleDefine) {
		for (BundleEvent event : events) {
			event.process(bundleContext, bundleDefine);
		}
	}

}
