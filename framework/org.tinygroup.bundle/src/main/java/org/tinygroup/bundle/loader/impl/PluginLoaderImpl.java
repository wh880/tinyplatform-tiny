package org.tinygroup.bundle.loader.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.fileresolver.FileProcessor;
import org.tinygroup.i18n.I18nMessageFactory;
import org.tinygroup.i18n.I18nMessages;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.bundle.config.PluginInfo;
import org.tinygroup.bundle.loader.PluginLoader;

/**
 * 扫描工程目录的插件加载器
 * 
 * @author luoguo
 * 
 */
public class PluginLoaderImpl implements PluginLoader {
	private static Logger logger = LoggerFactory
			.getLogger(PluginLoaderImpl.class);
	private I18nMessages i18nMessages = I18nMessageFactory.getI18nMessages();
	private List<PluginInfo> pluginInfoList = new ArrayList<PluginInfo>();

	public List<PluginInfo> load(String path) {
		return pluginInfoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tinygroup.bundle.loader.BundleLoader#remove(org.hundsun.
	 * opensource.plugin.config.BundleDefine)
	 */
	public void remove(PluginInfo plugin) {
		pluginInfoList.remove(plugin);

	}

	/**
	 * 加载url中的资源，调用fileprocessor.process方法
	 * 
	 * @param loader
	 * @param urls1
	 */
	public void resolveResource(ClassLoader loader, URL urls1[]) {

	}

	/**
	 * 移除url中的资源，调用fileprocessor.deprocess方法
	 * 
	 * @param loader
	 * @param urls
	 */
	public void deResolveResource(ClassLoader loader, URL urls[]) {
	}

	public List<FileProcessor> getRemoveResourceProcessors(PluginInfo plugin) {
		return null;
	}

	public List<FileProcessor> getResourceProcessors(PluginInfo plugin) {
		return null;
	}

	public List<PluginInfo> listJar(String path) {
		return pluginInfoList;
	}

	public ClassLoader getLoader(PluginInfo plugin) {
		return this.getClass().getClassLoader();
	}

	public ClassLoader getPublicLoader() {
		return this.getClass().getClassLoader();
	}

	public void add(PluginInfo plugin) {
		pluginInfoList.add(plugin);

	}

}
