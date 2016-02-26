package org.tinygroup.fileresolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.config.ConfigurationManager;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.fileresolver.impl.LocalPropertiesFileProcessor;
import org.tinygroup.fileresolver.impl.MergePropertiesFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

public class TestApplicationProperties {

	private static String DEFAULT_CONFIG = "application.xml";
	private final static Logger LOGGER = LoggerFactory.getLogger(TestApplicationProperties.class);
	public static void main(String[] args) {
		InputStream inputStream = TestApplicationProperties.class
				.getClassLoader().getResourceAsStream(DEFAULT_CONFIG);
		if (inputStream == null) {
			inputStream = TestApplicationProperties.class
					.getResourceAsStream(DEFAULT_CONFIG);
		}
		String applicationConfig = "";
		try {
			applicationConfig = StreamUtil
					.readText(inputStream, "UTF-8", false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (applicationConfig != null) {
			ConfigurationManager c = ConfigurationUtil
					.getConfigurationManager();
			c.setApplicationConfiguration(new XmlStringParser().parse(
					applicationConfig).getRoot());

		}

		FileResolver fileResolver = FileResolverFactory.getFileResolver();
		FileResolverUtil.addClassPathPattern(fileResolver);
		fileResolver
				.addResolvePath(FileResolverUtil.getClassPath(fileResolver));
		fileResolver.addResolvePath(FileResolverUtil.getWebClasses());
		fileResolver.addFileProcessor(new LocalPropertiesFileProcessor(
				applicationConfig));
		fileResolver.addFileProcessor(new MergePropertiesFileProcessor());
		fileResolver.resolve();
		Map<String, String> map = ConfigurationUtil.getConfigurationManager()
				.getConfiguration();
		for (String key : map.keySet()) {
			LOGGER.logMessage(LogLevel.INFO, "key:{},value:{}",key,map.get(key));
		}
		
		Assert.assertEquals("127.0.0.1",map.get("ip"));
		Assert.assertEquals("127.0.0.1",map.get("ipVar"));
		XmlNode node = ConfigurationUtil.getConfigurationManager().getApplicationConfiguration();
		LOGGER.logMessage(LogLevel.INFO, "{}",node);
		List<XmlNode> nodes1 = node.getSubNodes("value1");
		Assert.assertEquals("127.0.0.1",nodes1.get(0).getAttribute("value"));
		List<XmlNode> nodes2 = node.getSubNodes("value2");
		Assert.assertEquals("127.0.0.1",nodes2.get(0).getAttribute("value"));
		
	}
}
