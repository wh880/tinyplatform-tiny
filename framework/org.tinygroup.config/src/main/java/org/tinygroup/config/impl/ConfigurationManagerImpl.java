package org.tinygroup.config.impl;

import org.tinygroup.config.Configuration;
import org.tinygroup.config.ConfigurationLoader;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.parser.filter.PathFilter;
import org.tinygroup.xmlparser.node.XmlNode;

import java.util.Collection;
import java.util.Map;

/**
 * Created by luoguo on 2014/5/14.
 */
public class ConfigurationManagerImpl implements org.tinygroup.config.ConfigurationManager {
    private static Logger logger = LoggerFactory.getLogger(ConfigurationManagerImpl.class);
    private ConfigurationLoader configurationLoader;
    private XmlNode applicationConfiguration;
    private Map<String, XmlNode> componentConfigurationMap;
    private Collection<Configuration> configurationList;

    public void setConfigurationLoader(ConfigurationLoader configurationLoader) {
        this.configurationLoader = configurationLoader;
    }

    public void setApplicationConfiguration(XmlNode applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    public void setComponentConfigurationMap(Map<String, XmlNode> componentConfigurationMap) {
        this.componentConfigurationMap = componentConfigurationMap;
    }

    public void setComponentConfiguration(String key, XmlNode componentConfiguration) {
        componentConfigurationMap.put(key, componentConfiguration);
    }

    public XmlNode getApplicationConfiguration() {
        return applicationConfiguration;
    }

    public Map<String, XmlNode> getComponentConfigurationMap() {
        return componentConfigurationMap;
    }

    public XmlNode getComponentConfiguration(String key) {
        return componentConfigurationMap.get(key);
    }

    public void distributeConfiguration() {
        if (configurationList != null) {
            logger.logMessage(LogLevel.INFO, "正在分发应用配置信息...");
            PathFilter<XmlNode> pathFilter = new PathFilter<XmlNode>(applicationConfiguration);
            for (Configuration configuration : configurationList) {
                XmlNode componentConfig = componentConfigurationMap.get(configuration.getComponentConfigPath());
                XmlNode appConfig = null;
                if (configuration.getApplicationNodePath() != null) {
                    appConfig = pathFilter.findNode(configuration.getApplicationNodePath());
                }
                configuration.config(appConfig, componentConfig);
            }
            logger.logMessage(LogLevel.INFO, "应用配置信息分发完毕");
        }

    }

    public void setConfigurationList(Collection<Configuration> configurationList) {
        this.configurationList = configurationList;
    }

    public void loadConfiguration() {
        if (configurationLoader != null) {
            setApplicationConfiguration(configurationLoader.loadApplicationConfiguration());
            setComponentConfigurationMap(configurationLoader.loadComponentConfiguration());
        }
    }
}
