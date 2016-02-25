/**
 * 
 */
package org.tinygroup.fileresolver;

import org.tinygroup.fileresolver.impl.RemoteConfigFileProcessor;
import org.tinygroup.parser.filter.PathFilter;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

/**
 * @author Administrator
 *
 */
public class RemoteConfigUtil {

	public static XmlNode loadRemoteConfig(String applicationConfig) {
		XmlStringParser parser = new XmlStringParser();
		XmlNode root = parser.parse(applicationConfig).getRoot();
		PathFilter<XmlNode> filter = new PathFilter<XmlNode>(root);
		XmlNode appConfig = filter
				.findNode(RemoteConfigFileProcessor.REMOTE_CONFIG_PATH);
		return appConfig; 
	}
	
}
