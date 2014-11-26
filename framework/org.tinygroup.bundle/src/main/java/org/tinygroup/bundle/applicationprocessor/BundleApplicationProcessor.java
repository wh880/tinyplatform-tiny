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
package org.tinygroup.bundle.applicationprocessor;

import org.tinygroup.application.Application;
import org.tinygroup.application.ApplicationProcessor;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.xmlparser.node.XmlNode;

public class BundleApplicationProcessor implements ApplicationProcessor{
	private BundleManager bundleManager;
	public BundleManager getBundleManager() {
		return bundleManager;
	}

	public void setBundleManager(BundleManager bundleManager) {
		this.bundleManager = bundleManager;
	}

	public String getApplicationNodePath() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getComponentConfigPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		// TODO Auto-generated method stub
		
	}

	public XmlNode getComponentConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public XmlNode getApplicationConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void start() {
		bundleManager.setCommonRoot(System.getProperty("user.dir"));
		bundleManager.setBundleRoot(System.getProperty("user.dir"));
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void setApplication(Application application) {
		// TODO Auto-generated method stub
		
	}

}
