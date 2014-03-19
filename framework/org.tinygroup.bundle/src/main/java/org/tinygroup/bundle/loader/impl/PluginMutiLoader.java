package org.tinygroup.bundle.loader.impl;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.PluginInfo;
import org.tinygroup.bundle.config.PluginJarDependency;
import org.tinygroup.bundle.loader.PluginLoader;
import org.tinygroup.commons.classloader.ResourceURLClassLoader;
import org.tinygroup.fileresolver.FileProcessor;
import org.tinygroup.i18n.I18nMessageFactory;
import org.tinygroup.i18n.I18nMessages;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;
import org.tinygroup.xstream.XStreamFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginMutiLoader implements PluginLoader {
    private static Logger logger = LoggerFactory.getLogger(PluginMutiLoader.class);
    private I18nMessages i18nMessages = I18nMessageFactory.getI18nMessages();
    private PluginResolver resoler = new PluginResolver();
    /**
     * List为插件包的外部包
     */
    private Map<PluginInfo, List<URL>> pluginExtendJars = new HashMap<PluginInfo, List<URL>>();

    /**
     * 插件包所依赖的Jar包(一般是该插件service的实现jar、接口jar),插件包及其所依赖的Jar包在同一个Loader中
     */
    private Map<PluginInfo, List<URL>> pluginDependJars = new HashMap<PluginInfo, List<URL>>();
    /**
     * URL对应的jar包所包含的插件
     */
    private Map<PluginInfo, URL> pluginURLMap = new HashMap<PluginInfo, URL>();

    /**
     * 插件及对应的loader
     */
    private Map<PluginInfo, ClassLoader> pluginLoaders = new HashMap<PluginInfo, ClassLoader>();

    /**
     * 公共包对应的loader
     */
    private ResourceURLClassLoader publicLoader = new ResourceURLClassLoader(new URL[0], this.getClass().getClassLoader());

    /**
     * 根据PluginInfo信息获取对应的ClassLoader
     *
     * @param plugin
     * @return
     */
    public ClassLoader getLoader(PluginInfo plugin) {
        return pluginLoaders.get(plugin);
    }

    public ClassLoader getPublicLoader() {
        return publicLoader;
    }

    public List<PluginInfo> load(String path) {
        List<PluginInfo> list = load(path, true);
        if (list != null && list.size() > 0) {
            generateLoader(list);
        }
        return list;
    }

    /**
     * 从路径读取插件信息
     *
     * @param path   插件读取路径
     * @param isLoad 是否需要对插件信息进行详细解析
     * @return
     */
    private List<PluginInfo> load(String path, boolean isLoad) {
        List<PluginInfo> list = new ArrayList<PluginInfo>();
        if (path == null || "".equals(path)) {
            logger.logMessage(LogLevel.DEBUG, "传入的文件名为空");
            return list;
        }

        FileObject fileD = null;
        fileD = VFS.resolveFile(path);
        if (fileD.isFolder()) { // 如果是目录
            loadDir(fileD, list, isLoad);
        } else { // 如果是文件
            loadJar(fileD, list, isLoad);
        }

        return list;
    }

    /**
     * 从指定目录读取插件信息
     *
     * @param fileD  指定目录
     * @param list   插件列表
     * @param isLoad 是否需要对插件进行详细解析
     */
    private void loadDir(FileObject fileD, List<PluginInfo> list, boolean isLoad) {
        logger.logMessage(LogLevel.DEBUG, "正在从目录[{0}]查找插件...", fileD.getAbsolutePath());
        List<FileObject> files = fileD.getChildren();

        for (FileObject f : files) {
            if (f.isFolder()) { // 如果是目录
                loadDir(f, list, isLoad);
            } else { // 如果是文件
                load(f, list, isLoad);
            }

        }

        logger.logMessage(LogLevel.DEBUG, "从目录[{0}]查找插件结束。", fileD.getAbsolutePath());

    }

    /**
     * 从指定jar读取插件信息
     *
     * @param fileObject 指定jar
     * @param list       插件列表
     * @param isLoad     是否需要对插件进行详细解析
     */
    private void loadJar(FileObject fileObject, List<PluginInfo> list, boolean isLoad) {
        // PluginManagerFactory.getBundleManager().removeAll();
        load(fileObject, list, isLoad);
    }

    private void load(FileObject f, List<PluginInfo> list, boolean isLoad) {

        String pluginsPath = f.getAbsolutePath();

        if (!pluginsPath.endsWith(".jar"))// 不是jar包
            return;
        logger.logMessage(LogLevel.DEBUG, "jar包路径{0}", pluginsPath);
        try {
            FileObject jarFile = VFS.resolveFile(pluginsPath);
            URL jarFileUrl = jarFile.getURL();
            // 判断jar包是否已被加载过，若被加载过，则不再重复加载。
            if (pluginURLMap.containsValue(jarFileUrl)) {
                logger.logMessage(LogLevel.INFO, "jar包{0}已被加载，请勿再次重复加载", jarFile.getAbsolutePath());
                return;
            }
            if (jarFile.isFolder()) {

                FileObject file = jarFile.getChild(PLUGIN_FILENAME);
                if (file == null) {
                    logger.logMessage(LogLevel.DEBUG, "未找到插件配置文件{0},Jar包{1}不是插件Jar包", PLUGIN_FILENAME, jarFile.getAbsolutePath());
                    return;
                }
                logger.logMessage(LogLevel.DEBUG, "发现插件配置文件{0}并开始读取,Jar包{1}", PLUGIN_FILENAME, jarFile.getAbsolutePath());
                XStream stream = XStreamFactory.getXStream(BundleManager.PLUGIN_XSTREAM);
                PluginInfo info = (PluginInfo) stream.fromXML(file.getInputStream());
                list.add(info);
                if (isLoad) {
                    dealPluginsJars(f, jarFile, info);
                }

                logger.logMessage(LogLevel.DEBUG, "插件配置文件[{0}]读取完毕,Jar包{1}", PLUGIN_FILENAME, jarFile.getAbsolutePath());
                return;

            } else {
                logger.logMessage(LogLevel.ERROR, "Jar包{1}类型不是Floader,无法读取子文件 ", jarFile.getAbsolutePath());
            }

        } catch (Exception e) {
            logger.errorMessage("读取文件[{0}]信息时出错:{1}", e, f.getAbsolutePath());
        }

    }

    /**
     * 处理插件所关联的jar
     *
     * @param f       插件文件
     * @param jarFile 插件jar文件
     * @param plugin  插件
     * @throws MalformedURLException
     */
    private void dealPluginsJars(FileObject f, FileObject jarFile, PluginInfo plugin) throws MalformedURLException {
        URL url = jarFile.getURL();
        if (!pluginURLMap.containsKey(url)) {
            pluginURLMap.put(plugin, url);
        }

        // 处理插件依赖的jar包(这里的依赖是插件jar所依赖的jar(一般是该插件service的实现jar、接口jar)，而非业务逻辑上的依赖)
        for (PluginJarDependency jarDep : plugin.getPluginJarDependencies()) {
            String name = jarDep.getName();
            if (name == null || "".equals(name.trim())) {
                continue;
            }
            logger.logMessage(LogLevel.INFO, "插件[id:{0},version:{1}]依赖jar包{3}", plugin.getId(), plugin.getVersion(), name);

            if (!pluginDependJars.containsKey(plugin)) {
                pluginDependJars.put(plugin, new ArrayList<URL>());
            }
            String urlString = jarFile.getURL().toString().replace(f.getFileName(), name);
            URL jarUrl = new URL(urlString);
            pluginDependJars.get(plugin).add(jarUrl);
            logger.logMessage(LogLevel.INFO, "添加jar包{0}全路径{1}", name, jarUrl);
        }
        // 处理插件的外部包
        for (PluginJarExtend jarExtend : plugin.getPluginJarExtends()) {
            String name = jarExtend.getName();
            if (name == null || "".equals(name.trim())) {
                continue;
            }
            logger.logMessage(LogLevel.INFO, "插件[id:{0},version:{1}]有外部jar包{3}", plugin.getId(), plugin.getVersion(), name);
            String urlString = jarFile.getURL().toString().replace(f.getFileName(), name);
            URL jarUrl = new URL(urlString);
            if (!pluginExtendJars.containsKey(plugin)) {
                pluginExtendJars.put(plugin, new ArrayList<URL>());
            }
            List<URL> extendJars = pluginExtendJars.get(plugin);
            if (!extendJars.contains(jarUrl)) {
                extendJars.add(jarUrl);
                logger.logMessage(LogLevel.INFO, "添加jar包{0}全路径{1}", name, jarUrl);
            }
        }
    }

    /**
     * 生成公共Loader和各个插件的loader
     */
    private void generateLoader(List<PluginInfo> list) {
        generatePublicLoader(list);
        generateOtherLoaders(list);
    }

    /**
     * 生成公共loader，并扫描公共loader中所包含jar中的资源
     */
    private void generatePublicLoader(List<PluginInfo> list) {
        logger.logMessage(LogLevel.INFO, "开始处理外部包的Loader");

        List<URL> extendJars = new ArrayList<URL>();
        // 将当前插件列表的外部包加入插件
        // TODO：可能会存在外部包已被加载过了，而此处重新加载
        for (PluginInfo plugin : list) {
            List<URL> listUrl = pluginExtendJars.get(plugin);
            if (listUrl == null)
                continue;
            for (URL url : listUrl) {
                if (!extendJars.contains(url)) {
                    extendJars.add(url);
                }
            }
        }
        URL urls1[] = listToArray(extendJars, extendJars.size());
        publicLoader.addURL(urls1);
        logger.logMessage(LogLevel.INFO, "外部包的Loader处理完毕");
        resolveResource(publicLoader, urls1);
    }

    /**
     * 分别为每个插件生成loader
     */
    private void generateOtherLoaders(List<PluginInfo> list) {

        pluginLoaders.clear();
        // 注册loader
        // addLoader方法中自己会过滤重复添加
//        LoaderManagerFactory.getManager().addLoader(publicLoader);

        for (PluginInfo plugin : list) {
            URL pluginUrl = pluginURLMap.get(plugin);
            generatePluginLoader(pluginUrl, plugin);
        }
    }

    private void generatePluginLoader(URL pluginUrl, PluginInfo plugin) {
        logger.logMessage(LogLevel.INFO, "开始生jar包{0}的Loader", pluginUrl.toString());
        List<URL> urls = pluginDependJars.get(plugin);
        URL urlArray[] = null;
        if (urls == null) {
            urlArray = new URL[1];
            urlArray[0] = pluginUrl;
        } else {
            urlArray = listToArray(urls, urls.size() + 1);
            urlArray[urls.size()] = pluginUrl;
        }
        URLClassLoader loader = new URLClassLoader(urlArray, publicLoader);
        // 注册loader
//        LoaderManagerFactory.getManager().addLoader(loader);
        pluginLoaders.put(plugin, loader);
        logger.logMessage(LogLevel.INFO, "jar包{0}的Loader生成完毕", pluginUrl.toString());
        resolveResource(loader, urlArray);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.tinygroup.bundle.loader.BundleLoader#remove(org.hundsun.
     * opensource.plugin.config.BundleDefine)
     */
    public void remove(PluginInfo plugin) {
        logger.logMessage(LogLevel.INFO, "开始移除插件[id:{0},version:{1}]对应的loader", plugin.getId(), plugin.getVersion());
        // 删除插件url信息
        URL pluginUrl = pluginURLMap.remove(plugin);
        if (pluginUrl == null) {
            pluginLoaders.remove(plugin);
            logger.logMessage(LogLevel.ERROR, "插件[id:{0},version:{1}]对应的文件Url未找到", plugin.getId(), plugin.getVersion());
            return;
        }
        // 删除依赖包
        removeDependJarResource(plugin, pluginUrl);
        // 删除外部包
        removeExtendJarResource(plugin);

        logger.logMessage(LogLevel.INFO, "移除插件[id:{0},version:{1}]对应的loader及资源信息完成", plugin.getId(), plugin.getVersion());
    }

    /**
     * 移除插件包及依赖包中的资源
     *
     * @param plugin
     * @param pluginUrl
     */
    private void removeDependJarResource(PluginInfo plugin, URL pluginUrl) {
        // 插件的依赖包，依赖包必须是私有的
        List<URL> dependJars = pluginDependJars.remove(plugin);
        URL urlArray[] = null;
        if (dependJars == null) {
            urlArray = new URL[1];
            urlArray[0] = pluginUrl;
        } else {
            urlArray = listToArray(dependJars, dependJars.size() + 1);
            urlArray[dependJars.size()] = pluginUrl;
        }

        // 插件对应的loader
        ClassLoader loader = pluginLoaders.remove(plugin);
        deResolveResource(loader, urlArray);
    }

    /**
     * 移除插件外部包中的资源
     *
     * @param plugin
     */
    private void removeExtendJarResource(PluginInfo plugin) {
        // 插件的公共包
        List<URL> removeExtendJars = getRemoveExtendJars(plugin);
        if (removeExtendJars == null) {
            return;
        }
        URL urlArray[] = listToArray(removeExtendJars, removeExtendJars.size());
        deResolveResource(publicLoader, urlArray);
    }

    /**
     * 移除插件时，获取需要移除的依赖包 若依赖包仅被本插件依赖，则移除，否则不移除
     *
     * @param plugin
     * @return
     */
    private List<URL> getRemoveExtendJars(PluginInfo plugin) {
        List<URL> extendJars = pluginExtendJars.remove(plugin);
        if (extendJars == null)
            return null;
        // 剩余所有插件所依赖的包
        List<URL> totalExtendJars = new ArrayList<URL>();
        for (List<URL> list : pluginExtendJars.values()) {
            totalExtendJars.addAll(list);
        }
        for (URL url : extendJars) {
            if (totalExtendJars.contains(url)) {
                extendJars.remove(url);
            }
        }
        return extendJars;
    }

    /**
     * 加载url中的资源，调用fileprocessor.process方法
     *
     * @param loader
     * @param urls1
     */
    public void resolveResource(ClassLoader loader, URL urls1[]) {
        resoler.resolve(urls1, loader);
    }

    /**
     * 移除url中的资源，调用fileprocessor.deprocess方法
     *
     * @param loader
     * @param urls
     */
    public void deResolveResource(ClassLoader loader, URL urls[]) {
        resoler.deResolve(urls, loader);
    }

    /**
     * 转换URLList为数组
     *
     * @param urlList
     * @param size
     * @return
     */
    private URL[] listToArray(List<URL> urlList, int size) {
        URL url[] = new URL[size];
        int i = 0;
        for (URL u1 : urlList) {
            url[i] = u1;
            i++;
        }
        return url;
    }

    public List<FileProcessor> getRemoveResourceProcessors(PluginInfo plugin) {
        List<PluginResourceProcessor> pluginRemoveResourceProcessors = plugin.getResourceRemoveProcessorList();
        return getProcessors(pluginRemoveResourceProcessors);
    }

    public List<FileProcessor> getResourceProcessors(PluginInfo plugin) {
        List<PluginResourceProcessor> pluginResourceProcessors = plugin.getResourceProcessorList();
        return getProcessors(pluginResourceProcessors);
    }

    private List<FileProcessor> getProcessors(List<PluginResourceProcessor> resourceProcessors) {
        List<FileProcessor> processors = new ArrayList<FileProcessor>();
        for (PluginResourceProcessor resProcessor : resourceProcessors) {
            String bean = resProcessor.getProcessorBeanName();
//            FileProcessor processor = (FileProcessor) SpringUtil.getBean(bean);
//            processors.add(processor);
        }
        return processors;
    }

    public List<PluginInfo> listJar(String path) {
        return load(path, false);
    }

    public void add(PluginInfo plugin) {
        throw new RuntimeException("not implements");
    }

}
