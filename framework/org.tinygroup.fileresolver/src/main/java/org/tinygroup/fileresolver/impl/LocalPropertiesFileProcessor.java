/**
 * 
 */
package org.tinygroup.fileresolver.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.ini.IniOperator;
import org.tinygroup.ini.Section;
import org.tinygroup.ini.ValuePair;
import org.tinygroup.ini.impl.IniOperatorDefault;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.parser.filter.PathFilter;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

/**
 * @author Administrator
 *
 */
public class LocalPropertiesFileProcessor extends AbstractFileProcessor {

	private static final String APPLICATION_PROPERTIES_PROPERTY = "/application/application-properties/property";
	
	private static final String APPLICATION_PROPERTIES_FILE = "/application/application-properties/file";
	
	String applicationConfig;
	
	public LocalPropertiesFileProcessor(String applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
	
	public void process() {
		LOGGER.logMessage(LogLevel.INFO, "开始读取本地Application变量配置信息");
		XmlNode xmlNode = new XmlStringParser().parse(applicationConfig).getRoot();
		//加载application property节点
		loadApplicationProperties(xmlNode);
		//加载外部配置文件信息
		loadApplicationPropertyFiles(xmlNode);
		LOGGER.logMessage(LogLevel.INFO, "读取本地Application变量配置信息完成");
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return false;
	}

	private static void loadApplicationProperties(XmlNode applicationConfig) {
		PathFilter<XmlNode> filter = new PathFilter<XmlNode>(applicationConfig);
		List<XmlNode> propertyList = filter
				.findNodeList(APPLICATION_PROPERTIES_PROPERTY);
		for (XmlNode property : propertyList) {
			String name = property.getAttribute("name");
			String value = property.getAttribute("value");
			ConfigurationUtil.getConfigurationManager().getConfiguration().put(name, value);
		}
	}
	
	private void loadApplicationPropertyFiles(XmlNode applicationConfig) {
		PathFilter<XmlNode> filter = new PathFilter<XmlNode>(applicationConfig);
		List<XmlNode> propertyList = filter
				.findNodeList(APPLICATION_PROPERTIES_FILE);
		for (XmlNode property : propertyList) {
			String path = property.getAttribute("path");
			if (path.endsWith(".ini")) {
				loadApplicationPropertyIniFile(path);
			} else if (path.endsWith(".properties")) {
				loadApplicationPropertyPropertiesFile(path);
			}

		}
	}

	private void loadApplicationPropertyPropertiesFile(String path) {
		Properties p = new Properties();
		InputStream in = ConfigurationUtil.class.getResourceAsStream(path);
		try {
			p.load(in);
			in.close();
		} catch (IOException e) {
			throw new RuntimeException("读取配置文件:" + path + "时出错", e);
		}
		if (p.size() <= 0) {
			return;
		}
		for (Object key : p.keySet()) {
			String value = p.getProperty(key.toString());
			ConfigurationUtil.getConfigurationManager().getConfiguration().put(key.toString(), value);
		}

	}

	private void loadApplicationPropertyIniFile(String path) {
		IniOperator operator = new IniOperatorDefault();
		try {
			File file = new File(ConfigurationUtil.class.getClassLoader()
					.getResource(path).toURI());
			operator.read(new FileInputStream(file), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("读取配置文件:" + path + "时出错", e);
		}
		List<Section> sectionList = operator.getSections().getSectionList();
		for (Section section : sectionList) {
			List<ValuePair> valuePairs = section.getValuePairList();
			for (ValuePair valuePair : valuePairs) {
				String key = valuePair.getKey();
				String value = valuePair.getValue();
				ConfigurationUtil.getConfigurationManager().getConfiguration().put(key, value);
			}
		}
	}
	
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}
	
}
