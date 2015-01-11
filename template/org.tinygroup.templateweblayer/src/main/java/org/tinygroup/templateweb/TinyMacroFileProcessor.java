package org.tinygroup.templateweb;

import java.util.List;
import java.util.Map;

import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.loader.TinyClassLoader;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.loader.ClassLoaderResourceLoader;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * tiny宏文件扫描
 *
 * @author renhui
 */
public class TinyMacroFileProcessor extends AbstractFileProcessor {

    private static final String COMPONENT_FILE_NAME = ".component";

    private static final String TINY_TEMPLATE_CONFIG = "/application/template-config";
    private static final String TINY_TEMPLATE_CONFIG_PATH = "/templateconfig.config.xml";
    //配置的三个参数名
    private static final String TEMPLATE_EXT_FILE_NAME = "templateExtFileName";
    private static final String LAYOUT_EXT_FILE_NAME = "layoutExtFileName";
    private static final String COMPONENT_EXT_FILE_NAME = "componentExtFileName";
    //配置参数的默认值
    private static final String TEMPLATE_EXT_DEFALUT = "page";
    private static final String LAYOUT_EXT_DEFALUT = "layout";
    private static final String COMPONENT_EXT_DEFALUT = "component";

    private TemplateEngine engine;

    private BundleManager bundleManager;

    private static boolean hasResourceLoader = false;

    public TemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

    public BundleManager getBundleManager() {
        return bundleManager;
    }

    public void setBundleManager(BundleManager bundleManager) {
        this.bundleManager = bundleManager;
    }

	public boolean isMatch(FileObject fileObject) {
        if (!fileObject.isFolder() && fileObject.getFileName().endsWith(COMPONENT_FILE_NAME)) {
            return true;
        }
        return false;
    }

    public void process() {
        if (!hasResourceLoader) {
            addResourceLoaders();
            hasResourceLoader = true;
        }
        for (FileObject fileObject : changeList) {
            logger.logMessage(LogLevel.INFO, "正在加载宏模板配置文件[{0}]",
                    fileObject.getAbsolutePath());
            try {
                engine.registerMacroLibrary(fileObject.getPath());
            } catch (TemplateException e) {
                logger.errorMessage("加载宏模板配置文件[{0}]出错", e,
                        fileObject.getAbsolutePath());
            }
            logger.logMessage(LogLevel.INFO, "正在加载宏模板配置文件[{0}]",
                    fileObject.getAbsolutePath());
        }

    }

    private void addResourceLoaders() {
        String templateExtFileName = TEMPLATE_EXT_DEFALUT;
        String layoutExtFileName = LAYOUT_EXT_DEFALUT;
        String componentExtFileName = COMPONENT_EXT_DEFALUT;
        //合并节点
        XmlNode totalConfig = ConfigurationUtil.combineXmlNode(applicationConfig, componentConfig);
        if (totalConfig != null) {
            templateExtFileName = StringUtil.defaultIfBlank(totalConfig.getAttribute(TEMPLATE_EXT_FILE_NAME), TEMPLATE_EXT_DEFALUT);
            layoutExtFileName = StringUtil.defaultIfBlank(totalConfig.getAttribute(LAYOUT_EXT_FILE_NAME), LAYOUT_EXT_DEFALUT);
            componentExtFileName = StringUtil.defaultIfBlank(totalConfig.getAttribute(COMPONENT_EXT_FILE_NAME), COMPONENT_EXT_DEFALUT);
        }
        Map<BundleDefine, TinyClassLoader> bundles = bundleManager
                .getBundleMap();
        for (TinyClassLoader classLoader : bundles.values()) {
            ClassLoaderResourceLoader classResourceLoader = new ClassLoaderResourceLoader(
                    templateExtFileName, layoutExtFileName,
                    componentExtFileName, classLoader);
            engine.addResourceLoader(classResourceLoader);
        }
        List<String> scanningPaths = fileResolver.getResolveFileObjectSet();
        for (String path : scanningPaths) {
            FileObjectResourceLoader fileResourceLoader = new FileObjectResourceLoader(
                    templateExtFileName, layoutExtFileName,
                    componentExtFileName, path);
            engine.addResourceLoader(fileResourceLoader);
        }
    }

    public void config(XmlNode applicationConfig, XmlNode componentConfig) {
        super.config(applicationConfig, componentConfig);
    }

    public String getComponentConfigPath() {
		return TINY_TEMPLATE_CONFIG_PATH;
	}
    
    public String getApplicationNodePath() {
        return TINY_TEMPLATE_CONFIG;
    }

}
