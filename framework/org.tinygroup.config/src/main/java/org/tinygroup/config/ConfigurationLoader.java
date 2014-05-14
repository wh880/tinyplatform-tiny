package org.tinygroup.config;

import org.tinygroup.xmlparser.node.XmlNode;

import java.util.Map;

/**
 * 用于载入配置
 * Created by luoguo on 2014/5/14.
 */
public interface ConfigurationLoader {
    /**
     * 用于载入应用配置
     *
     * @return
     */
    XmlNode loadApplicationConfiguration();

    /**
     * 用于载入组件配置
     *
     * @return
     */
    Map<String, XmlNode> loadComponentConfiguration();

}
