/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.springutil;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.parser.filter.PathFilter;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ApplicationPropertyResourceConfigurer extends
		PropertyPlaceholderConfigurer {

	private Resource application;

	public void setApplication(Resource application) {
		this.application = application;
	}

	@Override
	protected Properties mergeProperties() throws IOException {
		if (application.exists()) {
			Properties properties = new Properties();
			// 应该有个应用配置加载对象,优先于spring容器启动
			String config = StreamUtil.readText(application.getInputStream(),
					"UTF-8", true);
			XmlNode applicationConfig = new XmlStringParser().parse(config)
					.getRoot();
			PathFilter<XmlNode> filter = new PathFilter<XmlNode>(
					applicationConfig);
			List<XmlNode> propertyList = filter
					.findNodeList("/application/application-properties/property");
			if(!CollectionUtil.isEmpty(propertyList)){
				for (XmlNode property : propertyList) {
					String name = property.getAttribute("name");
					String value = property.getAttribute("value");
					properties.put(name, value);
				}
			}
			return properties;
		}
		return super.mergeProperties();

	}

}
